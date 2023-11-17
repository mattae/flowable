package com.mattae.snl.plugins.flowable.web.runtime;

import com.mattae.snl.plugins.flowable.content.api.DocumentDeployment;
import com.mattae.snl.plugins.flowable.content.api.DocumentRepositoryService;
import com.mattae.snl.plugins.flowable.services.model.DeploymentResourceResponse;
import com.mattae.snl.plugins.flowable.web.runtime.model.ContentRestApiInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.flowable.common.rest.resolver.ContentTypeResolver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeploymentResourceCollectionResource extends BaseDeploymentResource {

    protected final ContentRestResponseFactory restResponseFactory;

    protected final ContentTypeResolver contentTypeResolver;

    public DeploymentResourceCollectionResource(DocumentRepositoryService repositoryService,
                                                ContentRestApiInterceptor restApiInterceptor,
                                                ContentRestResponseFactory restResponseFactory,
                                                ContentTypeResolver contentTypeResolver) {
        super(repositoryService, restApiInterceptor);
        this.restResponseFactory = restResponseFactory;
        this.contentTypeResolver = contentTypeResolver;
    }

    @GetMapping(value = {"/document-repository/deployments/{deploymentId}/resources"}, produces = {"application/json"})
    public List<DeploymentResourceResponse> getDeploymentResources(@PathVariable String deploymentId, HttpServletRequest request) {
        DocumentDeployment deployment = getDocumentDeployment(deploymentId);
        List<String> resourceList = this.repositoryService.getDeploymentResourceNames(deployment.getId());
        return this.restResponseFactory.createDeploymentResourceResponseList(deploymentId, resourceList, this.contentTypeResolver);
    }
}
