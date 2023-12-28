//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mattae.snl.plugins.spring.boot;

import com.mattae.snl.plugins.flowable.config.FlowableContentProperties;
import com.mattae.snl.plugins.flowable.config.SpringContentEngineConfigurator;
import com.mattae.snl.plugins.flowable.content.spring.SpringContentEngineConfiguration;
import org.flowable.app.spring.SpringAppEngineConfiguration;
import org.flowable.content.engine.configurator.ContentEngineConfigurator;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.*;
import org.flowable.spring.boot.app.AppEngineAutoConfiguration;
import org.flowable.spring.boot.app.AppEngineServicesAutoConfiguration;
import org.flowable.spring.boot.condition.ConditionalOnAppEngine;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration(
    proxyBeanMethods = false
)
@EnableConfigurationProperties({FlowableProperties.class, FlowableContentProperties.class})
@AutoConfigureAfter({AppEngineAutoConfiguration.class, ProcessEngineAutoConfiguration.class})
@AutoConfigureBefore({AppEngineServicesAutoConfiguration.class, ProcessEngineServicesAutoConfiguration.class})
public class ContentEngineAutoConfiguration extends AbstractEngineAutoConfiguration {
    protected final FlowableContentProperties contentProperties;

    public ContentEngineAutoConfiguration(FlowableProperties flowableProperties, FlowableContentProperties contentProperties) {
        super(flowableProperties);
        this.contentProperties = contentProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public SpringContentEngineConfiguration contentEngineConfiguration(DataSource dataSource, PlatformTransactionManager platformTransactionManager) {
        SpringContentEngineConfiguration configuration = new SpringContentEngineConfiguration();
        configuration.setTransactionManager(platformTransactionManager);
        this.configureEngine(configuration, dataSource);
        FlowableContentProperties.Storage storage = this.contentProperties.getStorage();
        configuration.setContentRootFolder(storage.getRootFolder());
        configuration.setCreateContentRootFolder(storage.getCreateRoot());
        return configuration;
    }

    @Configuration(
        proxyBeanMethods = false
    )
    @ConditionalOnAppEngine
    @ConditionalOnBean(
        type = {"org.flowable.app.spring.SpringAppEngineConfiguration"}
    )
    public static class ContentEngineAppConfiguration extends BaseEngineConfigurationWithConfigurers<SpringContentEngineConfiguration> {
        public ContentEngineAppConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean(
            name = {"contentAppEngineConfigurationConfigurer"}
        )
        public EngineConfigurationConfigurer<SpringAppEngineConfiguration> contentAppEngineConfigurationConfigurer(ContentEngineConfigurator contentEngineConfigurator) {
            return (appEngineConfiguration) -> {
                appEngineConfiguration.addConfigurator(contentEngineConfigurator);
            };
        }

        @Bean
        @ConditionalOnMissingBean
        public ContentEngineConfigurator contentEngineConfigurator(SpringContentEngineConfiguration configuration) {
            SpringContentEngineConfigurator contentEngineConfigurator = new SpringContentEngineConfigurator();
            contentEngineConfigurator.setContentEngineConfiguration(configuration);
            this.invokeConfigurers(configuration);
            return contentEngineConfigurator;
        }
    }

    @Configuration(
        proxyBeanMethods = false
    )
    @ConditionalOnBean(
        type = {"org.flowable.spring.SpringProcessEngineConfiguration"}
    )
    @ConditionalOnMissingBean(
        type = {"org.flowable.app.spring.SpringAppEngineConfiguration"}
    )
    public static class ContentEngineProcessConfiguration extends BaseEngineConfigurationWithConfigurers<SpringContentEngineConfiguration> {
        public ContentEngineProcessConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean(
            name = {"contentProcessEngineConfigurationConfigurer"}
        )
        public EngineConfigurationConfigurer<SpringProcessEngineConfiguration> contentProcessEngineConfigurationConfigurer(ContentEngineConfigurator contentEngineConfigurator) {
            return (processEngineConfiguration) -> {
                processEngineConfiguration.addConfigurator(contentEngineConfigurator);
            };
        }

        @Bean
        @ConditionalOnMissingBean
        public ContentEngineConfigurator contentEngineConfigurator(SpringContentEngineConfiguration configuration) {
            SpringContentEngineConfigurator contentEngineConfigurator = new SpringContentEngineConfigurator();
            contentEngineConfigurator.setContentEngineConfiguration(configuration);
            this.invokeConfigurers(configuration);
            return contentEngineConfigurator;
        }
    }
}
