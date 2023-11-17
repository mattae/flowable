//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity;

import java.util.HashMap;
import java.util.Map;

public class ContentItemEntityImpl extends org.flowable.content.engine.impl.persistence.entity.ContentItemEntityImpl {
    private static final long serialVersionUID = 1L;
    protected String definitionId;
    protected String definitionName;
    protected String type;
    protected boolean renditionAvailable;

    public ContentItemEntityImpl() {
    }

    public Object getPersistentState() {
        Map<String, Object> persistentState = new HashMap<>();
        persistentState.put("name", this.name);
        persistentState.put("mimeType", this.mimeType);
        persistentState.put("taskId", this.taskId);
        persistentState.put("processInstanceId", this.processInstanceId);
        persistentState.put("contentStoreId", this.contentStoreId);
        persistentState.put("contentStoreName", this.contentStoreName);
        persistentState.put("contentAvailable", this.contentAvailable);
        persistentState.put("renditionAvailable", this.renditionAvailable);
        persistentState.put("field", this.field);
        persistentState.put("definitionId", this.definitionId);
        persistentState.put("definitionName", this.definitionName);
        persistentState.put("type", this.type);
        persistentState.put("contentSize", this.contentSize);
        persistentState.put("created", this.created);
        persistentState.put("createdBy", this.createdBy);
        persistentState.put("lastModified", this.lastModified);
        persistentState.put("lastModifiedBy", this.lastModifiedBy);
        persistentState.put("tenantId", this.tenantId);
        persistentState.put("scopeId", this.scopeId);
        persistentState.put("scopeType", this.scopeType);
        return persistentState;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefinitionId() {
        return definitionId;
    }

    public void setDefinitionId(String definitionId) {
        this.definitionId = definitionId;
    }

    public String getDefinitionName() {
        return definitionName;
    }

    public void setDefinitionName(String definitionName) {
        this.definitionName = definitionName;
    }

    public boolean isRenditionAvailable() {
        return renditionAvailable;
    }

    public void setRenditionAvailable(boolean renditionAvailable) {
        this.renditionAvailable = renditionAvailable;
    }

    public String toString() {
        return "ContentItemEntity[" + this.id + "]";
    }
}
