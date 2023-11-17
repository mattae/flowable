package com.mattae.snl.plugins.flowable.content.engine.impl.cmd;

import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.MetadataInstanceEntity;
import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetMetadataValuesCmd implements Command<Map<String, Object>> {
    protected String contentItemId;

    public GetMetadataValuesCmd(String contentItemId) {
        this.contentItemId = contentItemId;
    }

    public Map<String, Object> execute(CommandContext commandContext) {
        if (this.contentItemId == null)
            throw new FlowableIllegalArgumentException("contentItemId is null");
        List<MetadataInstanceEntity> metadataInstanceEntities = CommandContextUtil.getMetadataInstanceEntityManager(commandContext).findMetadataInstancesByContentItemId(this.contentItemId);
        Map<String, Object> metadataValues = new HashMap<>(metadataInstanceEntities.size());
        for (MetadataInstanceEntity metadataInstanceEntity : metadataInstanceEntities)
            metadataValues.put(metadataInstanceEntity.getName(), metadataInstanceEntity.getValue());
        return metadataValues;
    }
}
