package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity;

import org.flowable.identitylink.service.impl.persistence.entity.IdentityLinkEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentDefinitionEntityImpl extends AbstractContentEngineEntity implements DocumentDefinitionEntity {
    protected String key;

    protected int version;

    protected String deploymentId;

    protected String tenantId;

    protected String name;

    protected String resourceName;

    protected String category;

    protected Date created;

    protected Date lastModified;


    protected List<IdentityLinkEntity> identityLinks;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getDeploymentId() {
        return this.deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceName() {
        return this.resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(Date updateTime) {
        this.lastModified = updateTime;
    }

    public Object getPersistentState() {
        Map<String, Object> persistentState = new HashMap<>();
        persistentState.put("key", this.key);
        persistentState.put("version", Integer.valueOf(this.version));
        persistentState.put("name", this.name);
        persistentState.put("resourceName", this.resourceName);
        persistentState.put("category", this.category);
        persistentState.put("created", this.created);
        persistentState.put("lastModified", this.lastModified);
        return persistentState;
    }
}
