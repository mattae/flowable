package com.mattae.snl.plugins.flowable.content.engine.impl.cmd;

import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.MetadataInstanceEntity;
import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;

public class GetMetadataValueCmd implements Command<Object> {
    protected String contentItemId;

    protected String variableName;

    public GetMetadataValueCmd(String contentItemId, String variableName) {
        this.contentItemId = contentItemId;
        this.variableName = variableName;
    }

    public Object execute(CommandContext commandContext) {
        if (this.contentItemId == null)
            throw new FlowableIllegalArgumentException("contentItemId is null");
        MetadataInstanceEntity metadataInstanceEntity = CommandContextUtil.getMetadataInstanceEntityManager(commandContext).findMetadataInstanceByContentItemIdAndName(this.contentItemId, this.variableName);
        if (metadataInstanceEntity != null)
            return metadataInstanceEntity.getValue();
        return null;
    }
}
