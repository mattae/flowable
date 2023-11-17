package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity;

import org.apache.commons.lang3.StringUtils;
import org.flowable.common.engine.impl.persistence.entity.ByteArrayRef;
import org.flowable.variable.api.types.ValueFields;
import org.flowable.variable.api.types.VariableType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MetadataInstanceEntityImpl extends AbstractContentEngineEntity implements MetadataInstanceEntity, ValueFields, Serializable {
    private static final long serialVersionUID = 1L;

    protected String name;

    protected VariableType type;

    protected String typeName;

    protected String contentItemId;

    protected Long longValue;

    protected Double doubleValue;

    protected String textValue;

    protected String textValue2;

    protected ByteArrayRef byteArrayRef;

    protected Object cachedValue;

    protected boolean forcedUpdate;

    protected boolean deleted;

    public Object getPersistentState() {
        Map<String, Object> persistentState = new HashMap<>();
        persistentState.put("name", this.name);
        if (this.type != null)
            persistentState.put("typeName", this.type.getTypeName());
        persistentState.put("contentItemId", this.contentItemId);
        persistentState.put("longValue", this.longValue);
        persistentState.put("doubleValue", this.doubleValue);
        persistentState.put("textValue", this.textValue);
        persistentState.put("textValue2", this.textValue2);
        if (this.byteArrayRef != null && this.byteArrayRef.getId() != null)
            persistentState.put("byteArrayRef", this.byteArrayRef.getId());
        if (this.forcedUpdate)
            persistentState.put("forcedUpdate", Boolean.TRUE);
        return persistentState;
    }

    public Object getValue() {
        if (!this.type.isCachable() || this.cachedValue == null)
            this.cachedValue = this.type.getValue(this);
        return this.cachedValue;
    }

    public void setValue(Object value) {
        this.type.setValue(value, this);
        this.typeName = this.type.getTypeName();
        this.cachedValue = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentItemId() {
        return this.contentItemId;
    }

    public void setContentItemId(String contentItemId) {
        this.contentItemId = contentItemId;
    }

    public String getTypeName() {
        if (this.typeName != null)
            return this.typeName;
        if (this.type != null)
            return this.type.getTypeName();
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public VariableType getType() {
        return this.type;
    }

    public void setType(VariableType type) {
        this.type = type;
    }

    public Long getLongValue() {
        return this.longValue;
    }

    public void setLongValue(Long longValue) {
        this.longValue = longValue;
    }

    public Double getDoubleValue() {
        return this.doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    @Override
    public byte[] getBytes() {
        return new byte[0];
    }

    @Override
    public void setBytes(byte[] bytes) {

    }

    public String getTextValue() {
        return this.textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public String getTextValue2() {
        return this.textValue2;
    }

    public void setTextValue2(String textValue2) {
        this.textValue2 = textValue2;
    }

    public Object getCachedValue() {
        return this.cachedValue;
    }

    public void setCachedValue(Object cachedValue) {
        this.cachedValue = cachedValue;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MetadataInstanceEntity[");
        sb.append("id=").append(this.id);
        sb.append(", name=").append(this.name);
        sb.append(", type=").append((this.type != null) ? this.type.getTypeName() : "null");
        if (this.longValue != null)
            sb.append(", longValue=").append(this.longValue);
        if (this.doubleValue != null)
            sb.append(", doubleValue=").append(this.doubleValue);
        if (this.textValue != null)
            sb.append(", textValue=").append(StringUtils.abbreviate(this.textValue, 40));
        if (this.textValue2 != null)
            sb.append(", textValue2=").append(StringUtils.abbreviate(this.textValue2, 40));
        if (this.byteArrayRef != null && this.byteArrayRef.getId() != null)
            sb.append(", byteArrayRef=").append(this.byteArrayRef.getId());
        sb.append("]");
        return sb.toString();
    }

    public String getProcessInstanceId() {
        return null;
    }

    public String getExecutionId() {
        return null;
    }

    public String getScopeId() {
        return null;
    }

    public String getSubScopeId() {
        return null;
    }

    public String getScopeType() {
        return null;
    }

    public String getTaskId() {
        return null;
    }
}
