package com.mattae.snl.plugins.flowable.content.engine.impl.cmd;

import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.MetadataInstanceEntity;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.MetadataInstanceEntityManager;
import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.content.engine.impl.persistence.entity.ContentItemEntity;

import java.util.Collection;
import java.util.List;

public class RemoveMetadataValuesCmd implements Command<Void> {
    protected String contentItemId;

    protected Collection<String> metadataNames;

    public RemoveMetadataValuesCmd(String contentItemId, Collection<String> metadataNames) {
        this.contentItemId = contentItemId;
        this.metadataNames = metadataNames;
    }

    public Void execute(CommandContext commandContext) {
        if (this.contentItemId == null)
            throw new FlowableIllegalArgumentException("contentItemId is null");
        if (this.metadataNames == null)
            throw new FlowableIllegalArgumentException("metadataNames is null");
        ContentItemEntity contentItemEntity = (ContentItemEntity) CommandContextUtil.getContentItemEntityManager(commandContext).findById(this.contentItemId);
        if (contentItemEntity == null)
            throw new FlowableObjectNotFoundException("No content item found for id " + this.contentItemId, ContentItemEntity.class);
        MetadataInstanceEntityManager metadataInstanceEntityManager = CommandContextUtil.getMetadataInstanceEntityManager(commandContext);
        List<MetadataInstanceEntity> metadataInstances = metadataInstanceEntityManager.findMetadataInstancesByContentItemIdAndNames(this.contentItemId, this.metadataNames);
        for (MetadataInstanceEntity metadataInstanceEntity : metadataInstances)
            metadataInstanceEntityManager.delete(metadataInstanceEntity);
        return null;
    }
}
