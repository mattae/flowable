package com.mattae.snl.plugins.flowable.config;

import com.mattae.snl.plugins.flowable.form.spring.SpringFormEngineConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.impl.AbstractEngineConfiguration;
import org.flowable.common.spring.SpringEngineConfiguration;
import org.flowable.form.engine.FormEngine;

@Slf4j
public class SpringFormEngineConfigurator extends FormEngineConfigurator {

    @Override
    public void configure(AbstractEngineConfiguration engineConfiguration) {
        if (formEngineConfiguration == null) {
            formEngineConfiguration = new SpringFormEngineConfiguration();
        } else if (!(formEngineConfiguration instanceof SpringFormEngineConfiguration)) {
            throw new IllegalArgumentException("Expected formEngine configuration to be of type "
                + SpringFormEngineConfiguration.class + " but was " + formEngineConfiguration.getClass());
        }
        initialiseCommonProperties(engineConfiguration, formEngineConfiguration);
        SpringEngineConfiguration springEngineConfiguration = (SpringEngineConfiguration) engineConfiguration;
        ((SpringFormEngineConfiguration) formEngineConfiguration).setTransactionManager(springEngineConfiguration.getTransactionManager());
        if (formEngineConfiguration.getBeans() == null) {
            formEngineConfiguration.setBeans(engineConfiguration.getBeans());
        }

        initFormEngine();
        
        initServiceConfigurations(engineConfiguration, formEngineConfiguration);
    }

    @Override
    protected synchronized FormEngine initFormEngine() {
        if (formEngineConfiguration == null) {
            throw new FlowableException("FormEngineConfiguration is required");
        }

        return formEngineConfiguration.buildFormEngine();
    }
}