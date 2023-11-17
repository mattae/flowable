package com.mattae.snl.plugins.flowable.content.engine.impl.cmd;

import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;

import java.io.Serializable;

public class DeleteDeploymentCmd implements Command<Void>, Serializable {
    private static final long serialVersionUID = 1L;

    protected String deploymentId;

    public DeleteDeploymentCmd(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public Void execute(CommandContext commandContext) {
        if (this.deploymentId == null)
            throw new FlowableIllegalArgumentException("deploymentId is null");
        CommandContextUtil.getContentEngineConfiguration(commandContext).getDeploymentManager().removeDeployment(this.deploymentId);
        return null;
    }
}
