package com.mattae.snl.plugins.flowable.content.api;

import java.util.Date;

public interface DocumentDefinition {
    String getId();

    String getKey();

    int getVersion();

    String getDeploymentId();

    String getTenantId();

    String getName();

    String getResourceName();

    String getCategory();

    Date getCreated();

    Date getLastModified();
}
