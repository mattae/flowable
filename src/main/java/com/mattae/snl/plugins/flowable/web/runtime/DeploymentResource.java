package com.mattae.snl.plugins.flowable.web.runtime;

import com.mattae.snl.plugins.flowable.content.api.DocumentDeployment;
import com.mattae.snl.plugins.flowable.content.api.DocumentRepositoryService;
import com.mattae.snl.plugins.flowable.services.model.DocumentDeploymentResponse;
import com.mattae.snl.plugins.flowable.web.runtime.model.ContentRestApiInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeploymentResource extends BaseDeploymentResource {
    protected final ContentRestResponseFactory restResponseFactory;

    public DeploymentResource(DocumentRepositoryService repositoryService,
                              ContentRestApiInterceptor restApiInterceptor,
                              ContentRestResponseFactory restResponseFactory) {
        super(repositoryService, restApiInterceptor);
        this.restResponseFactory = restResponseFactory;
    }

    @GetMapping(value = {"/document-repository/deployments/{deploymentId}"}, produces = {"application/json"})
    public DocumentDeploymentResponse getDeployment(@PathVariable String deploymentId, HttpServletRequest request) {
        DocumentDeployment deployment = getDocumentDeployment(deploymentId);
        return this.restResponseFactory.createDeploymentResponse(deployment);
    }

    @DeleteMapping(value = {"/document-repository/deployments/{deploymentId}"}, produces = {"application/json"})
    public void deleteDeployment(@PathVariable String deploymentId, HttpServletResponse response) {
        DocumentDeployment deployment = getDocumentDeployment(deploymentId);
        if (this.restApiInterceptor != null)
            this.restApiInterceptor.deleteDeployment(deployment);
        this.repositoryService.deleteDeployment(deploymentId);
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }
}
