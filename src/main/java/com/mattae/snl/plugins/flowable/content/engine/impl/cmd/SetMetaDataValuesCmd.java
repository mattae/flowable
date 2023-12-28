package com.mattae.snl.plugins.flowable.content.engine.impl.cmd;

import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.MetadataInstanceEntity;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.MetadataInstanceEntityManager;
import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.content.engine.impl.persistence.entity.ContentItemEntity;
import org.flowable.variable.api.types.VariableType;
import org.flowable.variable.api.types.VariableTypes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetMetaDataValuesCmd implements Command<Void> {
    protected String contentItemId;

    protected Map<String, Object> metadataValues;

    public SetMetaDataValuesCmd(String contentItemId, Map<String, Object> metadataValues) {
        this.contentItemId = contentItemId;
        this.metadataValues = metadataValues;
    }

    public Void execute(CommandContext commandContext) {
        if (this.contentItemId == null)
            throw new FlowableIllegalArgumentException("contentItemId is null");
        if (this.metadataValues == null)
            throw new FlowableIllegalArgumentException("metadataValues is null");
        if (this.metadataValues.isEmpty())
            throw new FlowableIllegalArgumentException("metadataValues is empty");
        ContentItemEntity contentItemEntity = CommandContextUtil.getContentItemEntityManager(commandContext).findById(this.contentItemId);
        if (contentItemEntity == null)
            throw new FlowableObjectNotFoundException("No content item found for id " + this.contentItemId, ContentItemEntity.class);
        VariableTypes variableTypes = CommandContextUtil.getContentEngineConfiguration(commandContext).getVariableTypes();
        MetadataInstanceEntityManager metadataInstanceEntityManager = CommandContextUtil.getMetadataInstanceEntityManager(commandContext);
        List<MetadataInstanceEntity> existingMetadataInstances = metadataInstanceEntityManager.findMetadataInstancesByContentItemId(this.contentItemId);
        Map<String, MetadataInstanceEntity> metadataInstanceMap = new HashMap<>();
        for (MetadataInstanceEntity metadataInstanceEntity : existingMetadataInstances)
            metadataInstanceMap.put(metadataInstanceEntity.getName(), metadataInstanceEntity);
        for (String metadataName : this.metadataValues.keySet()) {
            Object value = this.metadataValues.get(metadataName);
            VariableType type = variableTypes.findVariableType(value);
            MetadataInstanceEntity metadataInstance = metadataInstanceMap.get(metadataName);
            if (metadataInstance != null) {
                metadataInstance.setType(type);
                metadataInstance.setValue(value);
                metadataInstanceEntityManager.update(metadataInstance);
                continue;
            }
            MetadataInstanceEntity newMetadataInstance = metadataInstanceEntityManager.create(this.contentItemId, metadataName, type, value);
            metadataInstanceEntityManager.insert(newMetadataInstance);
        }
        return null;
    }
}
