package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity;

import org.flowable.content.engine.impl.persistence.entity.AbstractContentEngineNoRevisionEntity;

import java.io.Serializable;

public class DocumentResourceEntityImpl extends AbstractContentEngineNoRevisionEntity implements DocumentResourceEntity, Serializable {
    private static final long serialVersionUID = 1L;

    protected String name;

    protected byte[] bytes;

    protected String deploymentId;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getDeploymentId() {
        return this.deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public boolean isGenerated() {
        return false;
    }

    public Object getPersistentState() {
        return DocumentResourceEntityImpl.class;
    }

    public String toString() {
        return "DocumentResourceEntity[id=" + this.id + ", name=" + this.name + "]";
    }
}
