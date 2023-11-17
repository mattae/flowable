package com.mattae.snl.plugins.flowable.content.engine.impl;

import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.MetadataInstanceEntityImpl;
import org.flowable.variable.api.types.ValueFields;
import org.flowable.variable.api.types.VariableType;
import org.flowable.variable.api.types.VariableTypes;
import org.flowable.variable.service.impl.VariableValueProvider;

public class MetadataValueProvider implements VariableValueProvider {
    protected VariableTypes variableTypes;

    public MetadataValueProvider(VariableTypes variableTypes) {
        this.variableTypes = variableTypes;
    }

    public VariableType findVariableType(Object value) {
        return this.variableTypes.findVariableType(value);
    }

    public ValueFields createValueFields(String name, VariableType type, Object value) {
        MetadataInstanceEntityImpl metaDataInstance = new MetadataInstanceEntityImpl();
        metaDataInstance.setName(name);
        metaDataInstance.setType(type);
        metaDataInstance.setValue(value);
        return metaDataInstance;
    }
}
