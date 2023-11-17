package com.mattae.snl.plugins.flowable.content.engine.impl.cmd;

import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;

public class DeleteRenditionItemsByProcessInstanceIdCmd implements Command<Void> {
    protected final String processInstanceId;

    public DeleteRenditionItemsByProcessInstanceIdCmd(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Void execute(CommandContext commandContext) {
        if (this.processInstanceId == null)
            throw new FlowableIllegalArgumentException("processInstanceId is null");
        CommandContextUtil.getRenditionItemEntityManager().deleteRenditionItemsByProcessInstanceId(this.processInstanceId);
        return null;
    }
}
