//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mattae.snl.plugins.flowable.config;

import com.mattae.snl.plugins.flowable.content.api.DocumentRepositoryService;
import com.mattae.snl.plugins.flowable.content.api.MetadataService;
import com.mattae.snl.plugins.flowable.content.api.RenditionService;
import com.mattae.snl.plugins.flowable.content.config.ContentEngineConfiguration;
import com.mattae.snl.plugins.flowable.content.spring.SpringContentEngineConfiguration;
import com.mattae.snl.plugins.spring.boot.ContentEngineAutoConfiguration;
import org.flowable.app.engine.AppEngine;
import org.flowable.content.api.ContentManagementService;
import org.flowable.content.api.ContentService;
import org.flowable.content.engine.ContentEngine;
import org.flowable.content.engine.ContentEngines;
import org.flowable.content.spring.ContentEngineFactoryBean;
import org.flowable.engine.ProcessEngine;
import org.flowable.spring.boot.BaseEngineConfigurationWithConfigurers;
import org.flowable.spring.boot.FlowableProperties;
import org.flowable.spring.boot.ProcessEngineServicesAutoConfiguration;
import org.flowable.spring.boot.app.AppEngineServicesAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(
    proxyBeanMethods = false
)
@EnableConfigurationProperties({FlowableProperties.class, FlowableContentProperties.class})
@AutoConfigureAfter({ContentEngineAutoConfiguration.class, AppEngineServicesAutoConfiguration.class, ProcessEngineServicesAutoConfiguration.class})
public class ContentEngineServicesAutoConfiguration {
    public ContentEngineServicesAutoConfiguration() {
    }

    @Bean
    public ContentService contentService(ContentEngine contentEngine) {
        return contentEngine.getContentService();
    }

    @Bean
    public ContentManagementService contentManagementService(ContentEngine contentEngine) {
        return contentEngine.getContentManagementService();
    }

    @Configuration(
        proxyBeanMethods = false
    )
    @ConditionalOnMissingBean(
        type = {"org.flowable.content.engine.ContentEngine", "org.flowable.engine.ProcessEngine", "org.flowable.app.engine.AppEngine"}
    )
    static class StandaloneConfiguration extends BaseEngineConfigurationWithConfigurers<SpringContentEngineConfiguration> {
        StandaloneConfiguration() {
        }

        @Bean
        public ContentEngineFactoryBean contentEngine(SpringContentEngineConfiguration contentEngineConfiguration) {
            ContentEngineFactoryBean factory = new ContentEngineFactoryBean();
            factory.setContentEngineConfiguration(contentEngineConfiguration);
            this.invokeConfigurers(contentEngineConfiguration);
            return factory;
        }

        @Bean
        public MetadataService getMetadataService(ContentEngineConfiguration engineConfiguration) {
            return engineConfiguration.getMetadataService();
        }

        @Bean
        public ContentService getContentService(ContentEngineConfiguration engineConfiguration) {
            return engineConfiguration.getContentService();
        }

        @Bean
        public DocumentRepositoryService getDocumentRepositoryService(ContentEngineConfiguration engineConfiguration) {
            return engineConfiguration.getDocumentRepositoryService();
        }

        @Bean
        public RenditionService getRenditionService(ContentEngineConfiguration contentEngineConfiguration) {
            return contentEngineConfiguration.getRenditionService();
        }
    }

    @Configuration(
        proxyBeanMethods = false
    )
    @ConditionalOnMissingBean(
        type = {"org.flowable.content.engine.ContentEngine"}
    )
    @ConditionalOnBean(
        type = {"org.flowable.app.engine.AppEngine"}
    )
    static class AlreadyInitializedAppEngineConfiguration {
        AlreadyInitializedAppEngineConfiguration() {
        }

        @Bean
        public ContentEngine contentEngine(AppEngine appEngine) {
            if (!ContentEngines.isInitialized()) {
                throw new IllegalStateException("Content engine has not been initialized");
            } else {
                return ContentEngines.getDefaultContentEngine();
            }
        }
    }

    @Configuration(
        proxyBeanMethods = false
    )
    @ConditionalOnMissingBean(
        type = {"org.flowable.content.engine.ContentEngine", "org.flowable.app.engine.AppEngine"}
    )
    @ConditionalOnBean(
        type = {"org.flowable.engine.ProcessEngine"}
    )
    static class AlreadyInitializedEngineConfiguration {
        AlreadyInitializedEngineConfiguration() {
        }

        @Bean
        public ContentEngine contentEngine(ProcessEngine processEngine) {
            if (!ContentEngines.isInitialized()) {
                throw new IllegalStateException("Content engine has not been initialized");
            } else {
                return ContentEngines.getDefaultContentEngine();
            }
        }
    }
}
