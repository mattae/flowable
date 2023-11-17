package com.mattae.snl.plugins.flowable.content.engine.impl.cmd;

import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;

public class DeleteRenditionItemsByTaskIdCmd implements Command<Void> {
    protected final String taskId;

    public DeleteRenditionItemsByTaskIdCmd(String taskId) {
        this.taskId = taskId;
    }

    public Void execute(CommandContext commandContext) {
        if (this.taskId == null)
            throw new FlowableIllegalArgumentException("taskId is null");
        CommandContextUtil.getRenditionItemEntityManager().deleteRenditionItemsByTaskId(this.taskId);
        return null;
    }
}
