package com.mattae.snl.plugins.flowable.web.runtime;

import com.mattae.snl.plugins.flowable.content.api.DocumentDeployment;
import com.mattae.snl.plugins.flowable.content.api.DocumentRepositoryService;
import com.mattae.snl.plugins.flowable.web.runtime.model.ContentRestApiInterceptor;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.rest.resolver.ContentTypeResolver;

import java.io.InputStream;
import java.util.List;

@RequiredArgsConstructor
public class BaseDeploymentResourceDataResource {
    protected final ContentTypeResolver contentTypeResolver;
    protected final DocumentRepositoryService repositoryService;

    protected final ContentRestApiInterceptor restApiInterceptor;

    protected byte[] getDeploymentResourceData(String deploymentId, String resourceName, HttpServletResponse response) {
        if (deploymentId == null)
            throw new FlowableIllegalArgumentException("No deployment id provided");
        if (resourceName == null)
            throw new FlowableIllegalArgumentException("No resource name provided");
        DocumentDeployment deployment = this.repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
        if (deployment == null)
            throw new FlowableObjectNotFoundException("Could not find a deployment with id '" + deploymentId + "'.", DocumentDeployment.class);
        if (this.restApiInterceptor != null)
            this.restApiInterceptor.accessDeploymentById(deployment);
        List<String> resourceList = this.repositoryService.getDeploymentResourceNames(deploymentId);
        if (resourceList.contains(resourceName)) {
            InputStream resourceStream = this.repositoryService.getResourceAsStream(deploymentId, resourceName);
            String contentType = null;
            if (resourceName.toLowerCase().endsWith(".document")) {
                contentType = "application/json";
            } else {
                contentType = this.contentTypeResolver.resolveContentType(resourceName);
            }
            response.setContentType(contentType);
            try {
                return IOUtils.toByteArray(resourceStream);
            } catch (Exception e) {
                throw new FlowableException("Error converting resource stream", e);
            }
        }
        throw new FlowableObjectNotFoundException("Could not find a resource with name '" + resourceName + "' in deployment '" + deploymentId + "'.", String.class);
    }
}
