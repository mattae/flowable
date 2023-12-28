package com.mattae.snl.plugins.flowable.web.runtime;

import com.mattae.snl.plugins.flowable.content.api.DocumentRepositoryService;
import com.mattae.snl.plugins.flowable.web.runtime.model.ContentRestApiInterceptor;
import jakarta.servlet.http.HttpServletResponse;
import org.flowable.common.rest.resolver.ContentTypeResolver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeploymentResourceDataResource extends BaseDeploymentResourceDataResource {
    public DeploymentResourceDataResource(ContentTypeResolver contentTypeResolver,
                                          DocumentRepositoryService repositoryService,
                                          ContentRestApiInterceptor restApiInterceptor) {
        super(contentTypeResolver, repositoryService, restApiInterceptor);
    }

    @ResponseBody
    @GetMapping({"/document-repository/deployments/{deploymentId}/resourcedata/{resourceName}"})
    public byte[] getDeploymentResource(@PathVariable("deploymentId") String deploymentId, String resourceName, HttpServletResponse response) {
        return getDeploymentResourceData(deploymentId, resourceName, response);
    }
}
