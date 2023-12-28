package com.mattae.snl.plugins.flowable.content.engine.impl.cmd;

import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.DocumentDeploymentEntity;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.DocumentResourceEntity;
import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class GetDeploymentResourceCmd implements Command<InputStream> {
    protected String deploymentId;

    protected String resourceName;

    public GetDeploymentResourceCmd(String deploymentId, String resourceName) {
        this.deploymentId = deploymentId;
        this.resourceName = resourceName;
    }

    public InputStream execute(CommandContext commandContext) {
        if (this.deploymentId == null)
            throw new FlowableIllegalArgumentException("deploymentId is null");
        if (this.resourceName == null)
            throw new FlowableIllegalArgumentException("resourceName is null");
        DocumentResourceEntity resource = CommandContextUtil.getDocumentResourceEntityManager(commandContext).findResourceByDeploymentIdAndResourceName(this.deploymentId, this.resourceName);
        if (resource == null) {
            if (CommandContextUtil.getDocumentDeploymentEntityManager(commandContext).findById(this.deploymentId) == null)
                throw new FlowableObjectNotFoundException("deployment does not exist: " + this.deploymentId, DocumentDeploymentEntity.class);
            throw new FlowableObjectNotFoundException("no resource found with name '" + this.resourceName + "' in deployment '" + this.deploymentId + "'", DocumentResourceEntity.class);
        }
        return new ByteArrayInputStream(resource.getBytes());
    }
}
