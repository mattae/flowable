package com.mattae.snl.plugins.flowable.content.engine.impl.cmd;

import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;

import java.io.Serializable;

public class DeleteRenditionItemsByScopeCmd implements Command<Void>, Serializable {
    private static final long serialVersionUID = 1L;

    protected String scopeId;

    protected String scopeType;

    public DeleteRenditionItemsByScopeCmd(String scopeId, String scopeType) {
        this.scopeId = scopeId;
        this.scopeType = scopeType;
    }

    public Void execute(CommandContext commandContext) {
        if (this.scopeId == null && this.scopeType == null)
            throw new FlowableIllegalArgumentException("scopeId and scopeType are null");
        CommandContextUtil.getRenditionItemEntityManager().deleteRenditionItemsByScopeIdAndScopeType(this.scopeId, this.scopeType);
        return null;
    }
}
