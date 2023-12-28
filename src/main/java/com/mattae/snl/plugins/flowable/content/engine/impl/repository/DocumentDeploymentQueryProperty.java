package com.mattae.snl.plugins.flowable.content.engine.impl.repository;

import org.flowable.common.engine.api.query.QueryProperty;

import java.util.HashMap;
import java.util.Map;

public class DocumentDeploymentQueryProperty implements QueryProperty {
    private static final Map<String, DocumentDeploymentQueryProperty> properties = new HashMap<>();
    public static final DocumentDeploymentQueryProperty DEPLOYMENT_ID = new DocumentDeploymentQueryProperty("RES.ID_");
    public static final DocumentDeploymentQueryProperty DEPLOYMENT_NAME = new DocumentDeploymentQueryProperty("RES.NAME_");
    public static final DocumentDeploymentQueryProperty DEPLOYMENT_TENANT_ID = new DocumentDeploymentQueryProperty("RES.TENANT_ID_");
    public static final DocumentDeploymentQueryProperty DEPLOY_TIME = new DocumentDeploymentQueryProperty("RES.DEPLOY_TIME_");
    private static final long serialVersionUID = 1L;
    private String name;

    public DocumentDeploymentQueryProperty(String name) {
        this.name = name;
        properties.put(name, this);
    }

    public static DocumentDeploymentQueryProperty findByName(String propertyName) {
        return properties.get(propertyName);
    }

    public String getName() {
        return this.name;
    }
}
