package com.mattae.snl.plugins.spring.boot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattae.snl.plugins.flowable.config.FormEngineConfigurator;
import com.mattae.snl.plugins.flowable.config.SpringFormEngineConfigurator;
import com.mattae.snl.plugins.flowable.form.spring.SpringFormEngineConfiguration;
import com.mattae.snl.plugins.flowable.form.spring.autodeployment.DefaultAutoDeploymentStrategy;
import com.mattae.snl.plugins.flowable.form.spring.autodeployment.ResourceParentFolderAutoDeploymentStrategy;
import com.mattae.snl.plugins.flowable.form.spring.autodeployment.SingleResourceAutoDeploymentStrategy;
import org.flowable.app.engine.AppEngine;
import org.flowable.app.spring.SpringAppEngineConfiguration;
import org.flowable.common.engine.api.scope.ScopeTypes;
import org.flowable.common.spring.AutoDeploymentStrategy;
import org.flowable.common.spring.CommonAutoDeploymentProperties;
import org.flowable.form.api.FormManagementService;
import org.flowable.form.api.FormRepositoryService;
import org.flowable.form.api.FormService;
import org.flowable.form.engine.FormEngine;
import org.flowable.form.engine.FormEngines;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.*;
import org.flowable.spring.boot.app.AppEngineAutoConfiguration;
import org.flowable.spring.boot.app.AppEngineServicesAutoConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@AutoConfigureAfter({
        AppEngineAutoConfiguration.class,
        ProcessEngineAutoConfiguration.class,
})
@AutoConfigureBefore({
        AppEngineServicesAutoConfiguration.class,
        ProcessEngineServicesAutoConfiguration.class,
})
@AutoConfiguration
public class FormEngineAutoConfiguration extends AbstractSpringEngineAutoConfiguration {

    protected final FlowableAutoDeploymentProperties autoDeploymentProperties;

    public FormEngineAutoConfiguration(FlowableProperties flowableProperties,
                                       FlowableAutoDeploymentProperties autoDeploymentProperties) {
        super(flowableProperties);
        this.autoDeploymentProperties = autoDeploymentProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public SpringFormEngineConfiguration formEngineConfiguration(
            DataSource dataSource,
            PlatformTransactionManager platformTransactionManager,
            ObjectProvider<ObjectMapper> objectMapperProvider,
            ObjectProvider<List<AutoDeploymentStrategy<FormEngine>>> formAutoDeploymentStrategies
    ) throws IOException {
        SpringFormEngineConfiguration configuration = new SpringFormEngineConfiguration();

        List<Resource> resources = this.discoverDeploymentResources(
                "form",
                List.of("form"),
                true
        );

        if (resources != null && !resources.isEmpty()) {
            configuration.setDeploymentResources(resources.toArray(new Resource[0]));
            configuration.setDeploymentName("form");
        }

        configureSpringEngine(configuration, platformTransactionManager);
        configureEngine(configuration, dataSource);
        ObjectMapper objectMapper = objectMapperProvider.getIfAvailable();
        if (objectMapper != null) {
            configuration.setObjectMapper(objectMapper);
        }

        // We cannot use orderedStream since we want to support Boot 1.5 which is on pre 5.x Spring
        List<AutoDeploymentStrategy<FormEngine>> deploymentStrategies = formAutoDeploymentStrategies.getIfAvailable();
        if (deploymentStrategies == null) {
            deploymentStrategies = new ArrayList<>();
        }

        CommonAutoDeploymentProperties deploymentProperties = this.autoDeploymentProperties.deploymentPropertiesForEngine(ScopeTypes.FORM);
        // Always add the out of the box auto deployment strategies as last
        deploymentStrategies.add(new DefaultAutoDeploymentStrategy(deploymentProperties));
        deploymentStrategies.add(new SingleResourceAutoDeploymentStrategy(deploymentProperties));
        deploymentStrategies.add(new ResourceParentFolderAutoDeploymentStrategy(deploymentProperties));
        configuration.setDeploymentStrategies(deploymentStrategies);

        return configuration;
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnBean(type = {
            "org.flowable.spring.SpringProcessEngineConfiguration"
    })
    @ConditionalOnMissingBean(type = {
            "org.flowable.app.spring.SpringAppEngineConfiguration"
    })
    public static class FormEngineProcessConfiguration extends BaseEngineConfigurationWithConfigurers<SpringFormEngineConfiguration> {

        @Bean
        @ConditionalOnMissingBean(name = "formProcessEngineConfigurationConfigurer")
        public EngineConfigurationConfigurer<SpringProcessEngineConfiguration> formProcessEngineConfigurationConfigurer(
                FormEngineConfigurator formEngineConfigurator) {
            return processEngineConfiguration -> processEngineConfiguration.addConfigurator(formEngineConfigurator);
        }

        @Bean
        @ConditionalOnMissingBean
        public FormEngineConfigurator formEngineConfigurator(SpringFormEngineConfiguration configuration) {
            SpringFormEngineConfigurator formEngineConfigurator = new SpringFormEngineConfigurator();
            formEngineConfigurator.setFormEngineConfiguration(configuration);
            invokeConfigurers(configuration);

            return formEngineConfigurator;
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnBean(type = {
            "org.flowable.app.spring.SpringAppEngineConfiguration"
    })
    public static class FormEngineAppEngineConfiguration extends BaseEngineConfigurationWithConfigurers<SpringFormEngineConfiguration> {

        @Bean
        @ConditionalOnMissingBean(name = "formAppEngineConfigurationConfigurer")
        public EngineConfigurationConfigurer<SpringAppEngineConfiguration> formAppEngineConfigurationConfigurer(
                FormEngineConfigurator formEngineConfigurator) {
            return appEngineConfiguration -> appEngineConfiguration.addConfigurator(formEngineConfigurator);
        }

        @Bean
        @ConditionalOnMissingBean
        public FormEngineConfigurator formEngineConfigurator(SpringFormEngineConfiguration configuration) {
            SpringFormEngineConfigurator formEngineConfigurator = new SpringFormEngineConfigurator();
            formEngineConfigurator.setFormEngineConfiguration(configuration);

            invokeConfigurers(configuration);

            return formEngineConfigurator;
        }
    }

    @Bean
    public FormService formService(FormEngine formEngine) {
        return formEngine.getFormService();
    }

    @Bean
    public FormRepositoryService formRepositoryService(FormEngine formEngine) {
        return formEngine.getFormRepositoryService();
    }

    @Bean
    public FormManagementService formManagementService(FormEngine formEngine) {
        return formEngine.getFormManagementService();
    }

    @Bean
    public FormEngine formEngine(@SuppressWarnings("unused") AppEngine appEngine) {
        // The app engine needs to be injected, as otherwise it won't be initialized, which means that the FormEngine is not initialized yet
        if (!FormEngines.isInitialized()) {
            throw new IllegalStateException("Form engine has not been initialized");
        }
        return FormEngines.getDefaultFormEngine();
    }
}