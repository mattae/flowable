// 
// Decompiled by Procyon v0.5.36
// 

package com.mattae.snl.plugins.flowable.config;

import com.mattae.snl.plugins.flowable.config.deployer.FormDeployer;
import com.mattae.snl.plugins.flowable.form.FormEngineConfiguration;
import com.mattae.snl.plugins.flowable.form.spring.SpringFormEngineConfiguration;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.impl.AbstractEngineConfiguration;
import org.flowable.common.engine.impl.AbstractEngineConfigurator;
import org.flowable.common.engine.impl.EngineDeployer;
import org.flowable.common.engine.impl.persistence.entity.Entity;
import org.flowable.common.spring.SpringEngineConfiguration;
import org.flowable.form.engine.FormEngine;
import org.flowable.form.engine.impl.db.EntityDependencyOrder;

import java.util.ArrayList;
import java.util.List;

public class FormEngineConfigurator extends AbstractEngineConfigurator {
    protected FormEngineConfiguration formEngineConfiguration;

    public int getPriority() {
        return 300000;
    }

    protected List<EngineDeployer> getCustomDeployers() {
        final List<EngineDeployer> deployers = new ArrayList<>();
        deployers.add(new FormDeployer());
        return deployers;
    }

    protected String getMybatisCfgPath() {
        return "org/flowable/form/db/mapping/mappings.xml";
    }

    public void configure(final AbstractEngineConfiguration engineConfiguration) {
        if (this.formEngineConfiguration == null) {
            this.formEngineConfiguration = new SpringFormEngineConfiguration();
        }
        else if (!(this.formEngineConfiguration instanceof SpringFormEngineConfiguration)) {
            throw new IllegalArgumentException("Expected formEngine configuration to be of type " + SpringFormEngineConfiguration.class + " but was " + this.formEngineConfiguration.getClass());
        }
        this.initialiseCommonProperties(engineConfiguration, this.formEngineConfiguration);
        final SpringEngineConfiguration springEngineConfiguration = (SpringEngineConfiguration)engineConfiguration;
        ((SpringFormEngineConfiguration)this.formEngineConfiguration).setTransactionManager(springEngineConfiguration.getTransactionManager());
        /*if (this.formEngineConfiguration.getExpressionManager() == null) {
            this.formEngineConfiguration.setExpressionManager((ExpressionManager)new SpringFormExpressionManager(springEngineConfiguration.getApplicationContext(), springEngineConfiguration.getBeans()));
        }*/
        this.initFormEngine();
        this.initServiceConfigurations(engineConfiguration, this.formEngineConfiguration);
    }

    protected List<Class<? extends Entity>> getEntityInsertionOrder() {
        return EntityDependencyOrder.INSERT_ORDER;
    }

    protected List<Class<? extends Entity>> getEntityDeletionOrder() {
        return EntityDependencyOrder.DELETE_ORDER;
    }

    protected synchronized FormEngine initFormEngine() {
        if (this.formEngineConfiguration == null) {
            throw new FlowableException("FormEngineConfiguration is required");
        }
        return this.formEngineConfiguration.buildFormEngine();
    }

    public FormEngineConfiguration getFormEngineConfiguration() {
        return this.formEngineConfiguration;
    }

    public FormEngineConfigurator setFormEngineConfiguration(final FormEngineConfiguration formEngineConfiguration) {
        this.formEngineConfiguration = formEngineConfiguration;
        return this;
    }
}