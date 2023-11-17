package com.mattae.snl.plugins.flowable.web.runtime;

import com.mattae.snl.plugins.flowable.content.api.DocumentDeployment;
import com.mattae.snl.plugins.flowable.content.api.DocumentRepositoryService;
import com.mattae.snl.plugins.flowable.services.model.DeploymentResourceResponse;
import com.mattae.snl.plugins.flowable.web.runtime.model.ContentRestApiInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.rest.resolver.ContentTypeResolver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeploymentResourceResource extends BaseDeploymentResource {

    protected final ContentRestResponseFactory restResponseFactory;

    protected final ContentTypeResolver contentTypeResolver;

    public DeploymentResourceResource(DocumentRepositoryService repositoryService,
                                      ContentRestApiInterceptor restApiInterceptor,
                                      ContentRestResponseFactory restResponseFactory,
                                      ContentTypeResolver contentTypeResolver) {
        super(repositoryService, restApiInterceptor);
        this.restResponseFactory = restResponseFactory;
        this.contentTypeResolver = contentTypeResolver;
    }

    @GetMapping(value = {"/document-repository/deployments/{deploymentId}/resources/**"}, produces = {"application/json"})
    public DeploymentResourceResponse getDeploymentResource(@PathVariable("deploymentId") String deploymentId, HttpServletRequest request) {
        DocumentDeployment deployment = getDocumentDeployment(deploymentId);
        String pathInfo = request.getPathInfo();
        String resourceName = pathInfo.replace("/document-repository/deployments/" + deployment.getId() + "/resources/", "");
        List<String> resourceList = this.repositoryService.getDeploymentResourceNames(deployment.getId());
        if (resourceList.contains(resourceName)) {
            String contentType = null;
            if (resourceName.toLowerCase().endsWith(".document")) {
                contentType = "application/json";
            } else {
                contentType = this.contentTypeResolver.resolveContentType(resourceName);
            }
            return this.restResponseFactory.createDeploymentResourceResponse(deploymentId, resourceName, contentType);
        }
        throw new FlowableObjectNotFoundException("Could not find a resource with id '" + resourceName + "' in deployment '" + deploymentId + "'.");
    }
}
