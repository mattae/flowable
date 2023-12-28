package com.mattae.snl.plugins.flowable.web.runtime;

import com.mattae.snl.plugins.flowable.content.api.DocumentDeployment;
import com.mattae.snl.plugins.flowable.content.api.DocumentRepositoryService;
import com.mattae.snl.plugins.flowable.web.runtime.model.ContentRestApiInterceptor;
import lombok.RequiredArgsConstructor;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;

@RequiredArgsConstructor
public class BaseDeploymentResource {

    protected final DocumentRepositoryService repositoryService;

    protected final ContentRestApiInterceptor restApiInterceptor;

    protected DocumentDeployment getDocumentDeployment(String deploymentId) {
        DocumentDeployment deployment = this.repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
        if (deployment == null)
            throw new FlowableObjectNotFoundException("Could not find a deployment with id '" + deploymentId + "'.", DocumentDeployment.class);
        if (this.restApiInterceptor != null)
            this.restApiInterceptor.accessDeploymentById(deployment);
        return deployment;
    }
}
