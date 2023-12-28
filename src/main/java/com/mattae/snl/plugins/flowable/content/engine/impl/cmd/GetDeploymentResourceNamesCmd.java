package com.mattae.snl.plugins.flowable.content.engine.impl.cmd;

import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;

import java.util.List;

public class GetDeploymentResourceNamesCmd implements Command<List<String>> {
    protected String deploymentId;

    public GetDeploymentResourceNamesCmd(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public List<String> execute(CommandContext commandContext) {
        if (this.deploymentId == null)
            throw new FlowableIllegalArgumentException("deploymentId is null");
        return CommandContextUtil.getDocumentDeploymentEntityManager(commandContext).getDeploymentResourceNames(this.deploymentId);
    }
}
