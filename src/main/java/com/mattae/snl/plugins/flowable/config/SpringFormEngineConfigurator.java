package com.mattae.snl.plugins.flowable.config;

import com.mattae.snl.plugins.flowable.form.spring.SpringFormEngineConfiguration;
import org.flowable.common.engine.impl.AbstractEngineConfiguration;
import org.flowable.common.spring.SpringEngineConfiguration;

public class SpringFormEngineConfigurator extends FormEngineConfigurator {

    @Override
    public void configure(AbstractEngineConfiguration engineConfiguration) {
        if (formEngineConfiguration == null) {
            formEngineConfiguration = new SpringFormEngineConfiguration();
        } else if (!(formEngineConfiguration instanceof SpringEngineConfiguration)) {
            throw new IllegalArgumentException("Expected formEngine configuration to be of type "
                + SpringFormEngineConfiguration.class + " but was " + formEngineConfiguration.getClass());
        }
        super.configure(engineConfiguration);
    }
}
