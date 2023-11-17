package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity;

import org.flowable.common.engine.api.repository.EngineResource;

import java.io.Serializable;
import java.util.*;

public class DocumentDeploymentEntityImpl extends AbstractContentEngineEntity implements DocumentDeploymentEntity, Serializable {
    private static final long serialVersionUID = 1L;

    protected String name;

    protected String key;

    protected String category;

    protected String tenantId = "";

    protected String parentDeploymentId;

    protected Map<String, EngineResource> resources;

    protected Date deploymentTime;

    protected boolean isNew;

    protected Map<Class<?>, List<Object>> deployedArtifacts;

    public void addResource(DocumentResourceEntity resource) {
        if (this.resources == null)
            this.resources = new HashMap<>();
        this.resources.put(resource.getName(), resource);
    }

    public Object getPersistentState() {
        Map<String, Object> persistentState = new HashMap<>();
        persistentState.put("category", this.category);
        persistentState.put("tenantId", this.tenantId);
        persistentState.put("parentDeploymentId", this.parentDeploymentId);
        return persistentState;
    }

    public void addDeployedArtifact(Object deployedArtifact) {
        if (this.deployedArtifacts == null)
            this.deployedArtifacts = new HashMap<>();
        Class<?> clazz = deployedArtifact.getClass();
        List<Object> artifacts = this.deployedArtifacts.computeIfAbsent(clazz, k -> new ArrayList());
        artifacts.add(deployedArtifact);
    }

    public <T> List<T> getDeployedArtifacts(Class<T> clazz) {
        for (Class<?> deployedArtifactsClass : this.deployedArtifacts.keySet()) {
            if (clazz.isAssignableFrom(deployedArtifactsClass))
                return (List<T>) this.deployedArtifacts.get(deployedArtifactsClass);
        }
        return null;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, EngineResource> getResources() {
        return this.resources;
    }

    public void setResources(Map<String, EngineResource> resources) {
        this.resources = resources;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getParentDeploymentId() {
        return this.parentDeploymentId;
    }

    public void setParentDeploymentId(String parentDeploymentId) {
        this.parentDeploymentId = parentDeploymentId;
    }

    public Date getDeploymentTime() {
        return this.deploymentTime;
    }

    public void setDeploymentTime(Date deploymentTime) {
        this.deploymentTime = deploymentTime;
    }

    public boolean isNew() {
        return this.isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public String getDerivedFrom() {
        return null;
    }

    public String getDerivedFromRoot() {
        return null;
    }

    public String getEngineVersion() {
        return null;
    }

    public String toString() {
        return "DocumentDeploymentEntity[id=" + this.id + ", name=" + this.name + "]";
    }
}
