package com.mattae.snl.plugins.flowable.content.engine.impl.cmd;

import com.mattae.snl.plugins.flowable.content.api.MetadataInstance;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.MetadataInstanceEntityManager;
import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.content.engine.impl.persistence.entity.ContentItemEntity;

import java.io.Serializable;

public class GetMetadataInstanceCmd implements Command<MetadataInstance>, Serializable {
    private static final long serialVersionUID = 1L;

    protected String contentItemId;

    protected String metadataName;

    public GetMetadataInstanceCmd(String contentItemId, String metadataName) {
        this.contentItemId = contentItemId;
        this.metadataName = metadataName;
    }

    public MetadataInstance execute(CommandContext commandContext) {
        if (this.contentItemId == null)
            throw new FlowableIllegalArgumentException("contentItemId is null");
        if (this.metadataName == null)
            throw new FlowableIllegalArgumentException("metadataName is null");
        ContentItemEntity contentItemEntity = (ContentItemEntity) CommandContextUtil.getContentItemEntityManager(commandContext).findById(this.contentItemId);
        if (contentItemEntity == null)
            throw new FlowableObjectNotFoundException("No content item found for id " + this.contentItemId, ContentItemEntity.class);
        MetadataInstanceEntityManager metadataInstanceEntityManager = CommandContextUtil.getMetadataInstanceEntityManager(commandContext);
        return (MetadataInstance) metadataInstanceEntityManager.findMetadataInstanceByContentItemIdAndName(this.contentItemId, this.metadataName);
    }
}
