package com.mattae.snl.plugins.flowable.config;

import com.mattae.snl.plugins.flowable.content.spring.SpringContentEngineConfiguration;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.impl.AbstractEngineConfiguration;
import org.flowable.common.spring.SpringEngineConfiguration;
import org.flowable.content.engine.ContentEngine;

public class SpringContentEngineConfigurator extends ContentEngineConfigurator {
    public SpringContentEngineConfigurator() {
    }

    public void configure(AbstractEngineConfiguration engineConfiguration) {
        if (this.contentEngineConfiguration == null) {
            this.contentEngineConfiguration = new SpringContentEngineConfiguration();
        } else if (!(this.contentEngineConfiguration instanceof SpringContentEngineConfiguration)) {
            throw new IllegalArgumentException("Expected contentEngine configuration to be of type " + SpringContentEngineConfiguration.class + " but was " + engineConfiguration.getClass());
        }

        this.initialiseCommonProperties(engineConfiguration, this.contentEngineConfiguration);
        SpringEngineConfiguration springEngineConfiguration = (SpringEngineConfiguration) engineConfiguration;
        ((SpringContentEngineConfiguration) this.contentEngineConfiguration).setTransactionManager(springEngineConfiguration.getTransactionManager());
        this.initContentEngine();
        this.initServiceConfigurations(engineConfiguration, this.contentEngineConfiguration);
    }

    protected synchronized ContentEngine initContentEngine() {
        if (this.contentEngineConfiguration == null) {
            throw new FlowableException("ContentEngineConfiguration is required");
        } else {
            return this.contentEngineConfiguration.buildContentEngine();
        }
    }
}
