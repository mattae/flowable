package com.mattae.snl.plugins.flowable.content.engine.impl.cmd;

import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.MetadataInstanceEntity;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.MetadataInstanceEntityManager;
import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.content.engine.impl.persistence.entity.ContentItemEntity;

public class RemoveMetadataValueCmd implements Command<Void> {
    protected String contentItemId;

    protected String metadataName;

    public RemoveMetadataValueCmd(String contentItemId, String metadataName) {
        this.contentItemId = contentItemId;
        this.metadataName = metadataName;
    }

    public Void execute(CommandContext commandContext) {
        if (this.contentItemId == null)
            throw new FlowableIllegalArgumentException("contentItemId is null");
        if (this.metadataName == null)
            throw new FlowableIllegalArgumentException("metadataName is null");
        ContentItemEntity contentItemEntity = (ContentItemEntity) CommandContextUtil.getContentItemEntityManager(commandContext).findById(this.contentItemId);
        if (contentItemEntity == null)
            throw new FlowableObjectNotFoundException("No content item found for id " + this.contentItemId, ContentItemEntity.class);
        MetadataInstanceEntityManager metadataInstanceEntityManager = CommandContextUtil.getMetadataInstanceEntityManager(commandContext);
        MetadataInstanceEntity metadataInstanceEntity = metadataInstanceEntityManager.findMetadataInstanceByContentItemIdAndName(this.contentItemId, this.metadataName);
        metadataInstanceEntityManager.delete(metadataInstanceEntity);
        return null;
    }
}
