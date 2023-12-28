package com.mattae.snl.plugins.flowable.content.api;

import org.flowable.common.engine.api.repository.EngineDeployment;

import java.util.Date;

public interface DocumentDeployment extends EngineDeployment {
    String getId();

    String getName();

    Date getDeploymentTime();

    String getCategory();

    String getTenantId();

    String getParentDeploymentId();
}
