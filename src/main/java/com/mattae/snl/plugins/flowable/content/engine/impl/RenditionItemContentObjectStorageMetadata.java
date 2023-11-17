package com.mattae.snl.plugins.flowable.content.engine.impl;

import com.mattae.snl.plugins.flowable.content.api.ContentObjectStorageMetadata;
import com.mattae.snl.plugins.flowable.content.api.RenditionItem;

public class RenditionItemContentObjectStorageMetadata implements ContentObjectStorageMetadata {
    protected final RenditionItem renditionItem;

    public RenditionItemContentObjectStorageMetadata(RenditionItem renditionItem) {
        this.renditionItem = renditionItem;
    }

    public String getName() {
        return this.renditionItem.getName();
    }

    public String getScopeId() {
        if (this.renditionItem.getTaskId() != null)
            return this.renditionItem.getTaskId();
        if (this.renditionItem.getProcessInstanceId() != null)
            return this.renditionItem.getProcessInstanceId();
        return this.renditionItem.getScopeId();
    }

    public String getScopeType() {
        if (this.renditionItem.getTaskId() != null)
            return "task";
        if (this.renditionItem.getProcessInstanceId() != null)
            return "bpmn";
        return this.renditionItem.getScopeType();
    }

    public String getMimeType() {
        return this.renditionItem.getMimeType();
    }

    public String getTenantId() {
        return this.renditionItem.getTenantId();
    }

    public String getContentStoreName() {
        return this.renditionItem.getContentStoreName();
    }

    public Object getStoredObject() {
        return this.renditionItem;
    }
}
