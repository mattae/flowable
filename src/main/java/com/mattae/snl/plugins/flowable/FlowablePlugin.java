package com.mattae.snl.plugins.flowable;

import io.github.jbella.snl.core.api.bootstrap.EnhancedSpringBootstrap;
import org.laxture.sbp.SpringBootPlugin;
import org.laxture.sbp.spring.boot.SpringBootstrap;
import org.laxture.sbp.spring.boot.configurer.SbpDataSourceConfigurer;
import org.pf4j.PluginWrapper;

public class FlowablePlugin extends SpringBootPlugin {

    public FlowablePlugin(PluginWrapper wrapper) {
        super(wrapper, new SbpDataSourceConfigurer());
    }

    @Override
    protected SpringBootstrap createSpringBootstrap() {
        return new EnhancedSpringBootstrap(this, FlowablePluginApp.class);
    }

}
