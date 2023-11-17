//
// Decompiled by Procyon v0.5.36
//

package com.mattae.snl.plugins.flowable.config;

import org.flowable.common.engine.impl.AbstractEngineConfiguration;
import org.flowable.content.spring.SpringContentEngineConfiguration;

public class ContentEngineConfigurator extends org.flowable.content.engine.configurator.ContentEngineConfigurator {

    public int getPriority() {
        return 300000;
    }


    protected String getMybatisCfgPath() {
        return "installers/flowable/content/db/mapping/mappings.xml";
    }

    public void configure(final AbstractEngineConfiguration engineConfiguration) {
        if (this.contentEngineConfiguration == null) {
            this.contentEngineConfiguration = new SpringContentEngineConfiguration();
        } else if (!(this.contentEngineConfiguration instanceof SpringContentEngineConfiguration)) {
            throw new IllegalArgumentException("Expected formEngine configuration to be of type " + SpringContentEngineConfigurator.class + " but was " + this.contentEngineConfiguration.getClass());
        }
        super.configure(engineConfiguration);
    }
}
