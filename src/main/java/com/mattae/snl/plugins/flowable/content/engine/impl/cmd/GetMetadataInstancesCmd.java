package com.mattae.snl.plugins.flowable.content.engine.impl.cmd;

import com.mattae.snl.plugins.flowable.content.api.MetadataInstance;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.MetadataInstanceEntity;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.MetadataInstanceEntityManager;
import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.content.engine.impl.persistence.entity.ContentItemEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetMetadataInstancesCmd implements Command<Map<String, MetadataInstance>>, Serializable {
    private static final long serialVersionUID = 1L;

    protected String contentItemId;

    protected Collection<String> metadataNames;

    public GetMetadataInstancesCmd(String contentItemId, Collection<String> metadataNames) {
        this.contentItemId = contentItemId;
        this.metadataNames = metadataNames;
    }

    public Map<String, MetadataInstance> execute(CommandContext commandContext) {
        if (this.contentItemId == null)
            throw new FlowableIllegalArgumentException("contentItemId is null");
        ContentItemEntity contentItemEntity = CommandContextUtil.getContentItemEntityManager(commandContext).findById(this.contentItemId);
        if (contentItemEntity == null)
            throw new FlowableObjectNotFoundException("No content item found for id " + this.contentItemId, ContentItemEntity.class);
        MetadataInstanceEntityManager metadataInstanceEntityManager = CommandContextUtil.getMetadataInstanceEntityManager(commandContext);
        List<MetadataInstanceEntity> metadataInstances = null;
        if (this.metadataNames == null) {
            metadataInstances = metadataInstanceEntityManager.findMetadataInstancesByContentItemId(this.contentItemId);
        } else {
            metadataInstances = metadataInstanceEntityManager.findMetadataInstancesByContentItemIdAndNames(this.contentItemId, this.metadataNames);
        }
        Map<String, MetadataInstance> metadataMap = new HashMap<>();
        for (MetadataInstanceEntity metadataInstanceEntity : metadataInstances)
            metadataMap.put(metadataInstanceEntity.getName(), metadataInstanceEntity);
        return metadataMap;
    }
}
