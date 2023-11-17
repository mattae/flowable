package com.mattae.snl.plugins.flowable;

import io.github.jbella.snl.core.api.bootstrap.EnhancedSpringBootstrap;
import io.github.jbella.snl.core.api.bootstrap.JpaSpringBootPlugin;
import org.laxture.sbp.spring.boot.SpringBootstrap;
import org.pf4j.PluginWrapper;

import java.util.Collections;

public class FlowablePlugin extends JpaSpringBootPlugin {

    public FlowablePlugin(PluginWrapper wrapper) {
        super(wrapper, Collections.emptyList());
    }

    @Override
    protected SpringBootstrap createSpringBootstrap() {
        return new EnhancedSpringBootstrap(this, FlowablePluginApp.class);
    }

}
