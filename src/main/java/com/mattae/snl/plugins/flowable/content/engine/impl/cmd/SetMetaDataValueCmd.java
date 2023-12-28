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

public class SetMetaDataValueCmd implements Command<Void> {
    protected String contentItemId;

    protected String metadataName;

    protected Object metadataValue;

    public SetMetaDataValueCmd(String contentItemId, String metadataName, Object metadataValue) {
        this.contentItemId = contentItemId;
        this.metadataName = metadataName;
        this.metadataValue = metadataValue;
    }

    public Void execute(CommandContext commandContext) {
        if (this.contentItemId == null)
            throw new FlowableIllegalArgumentException("contentItemId is null");
        if (this.metadataName == null)
            throw new FlowableIllegalArgumentException("metadataName is null");
        ContentItemEntity contentItemEntity = CommandContextUtil.getContentItemEntityManager(commandContext).findById(this.contentItemId);
        if (contentItemEntity == null)
            throw new FlowableObjectNotFoundException("No content item found for id " + this.contentItemId, ContentItemEntity.class);
        VariableTypes variableTypes = CommandContextUtil.getContentEngineConfiguration(commandContext).getVariableTypes();
        VariableType type = variableTypes.findVariableType(this.metadataValue);
        MetadataInstanceEntityManager metadataInstanceEntityManager = CommandContextUtil.getMetadataInstanceEntityManager(commandContext);
        MetadataInstanceEntity metadataInstance = metadataInstanceEntityManager.findMetadataInstanceByContentItemIdAndName(this.contentItemId, this.metadataName);
        if (metadataInstance != null) {
            metadataInstance.setType(type);
            metadataInstance.setValue(this.metadataValue);
            metadataInstanceEntityManager.update(metadataInstance);
        } else {
            MetadataInstanceEntity newMetadataInstance = metadataInstanceEntityManager.create(this.contentItemId, this.metadataName, type, this.metadataValue);
            metadataInstanceEntityManager.insert(newMetadataInstance);

        }
        return null;
    }
}
