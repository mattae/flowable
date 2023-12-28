package com.mattae.snl.plugins.flowable.web.runtime;

/**
 * @author Yvo Swillens
 */

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.api.query.QueryProperty;
import org.flowable.dmn.engine.impl.DeploymentQueryProperty;
import org.flowable.form.api.FormDeployment;
import org.flowable.form.api.FormDeploymentBuilder;
import org.flowable.form.api.FormRepositoryService;
import org.flowable.form.rest.FormRestResponseFactory;
import org.flowable.form.rest.service.api.repository.FormDeploymentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/rest")
@Transactional
public class FormDeploymentCollectionResource {

    private static final Map<String, QueryProperty> allowedSortProperties = new HashMap<>();

    static {
        allowedSortProperties.put("id", DeploymentQueryProperty.DEPLOYMENT_ID);
        allowedSortProperties.put("name", DeploymentQueryProperty.DEPLOYMENT_NAME);
        allowedSortProperties.put("deployTime", DeploymentQueryProperty.DEPLOY_TIME);
        allowedSortProperties.put("tenantId", DeploymentQueryProperty.DEPLOYMENT_TENANT_ID);
    }

    protected final FormRestResponseFactory formRestResponseFactory;

    protected final FormRepositoryService formRepositoryService;

    public FormDeploymentCollectionResource(FormRestResponseFactory formRestResponseFactory, FormRepositoryService formRepositoryService) {
        this.formRestResponseFactory = formRestResponseFactory;
        this.formRepositoryService = formRepositoryService;
    }

    @PostMapping(value = "/form-repository/deployments", produces = "application/json", consumes = "multipart/form-data")
    public FormDeploymentResponse uploadDeployment(@RequestParam(value = "tenantId", required = false) String tenantId,
                                                   HttpServletRequest request, HttpServletResponse response) {

        if (!(request instanceof MultipartHttpServletRequest multipartRequest)) {
            throw new FlowableIllegalArgumentException("Multipart request is required");
        }

        if (multipartRequest.getFileMap().size() == 0) {
            throw new FlowableIllegalArgumentException("Multipart request with file content is required");
        }

        MultipartFile file = multipartRequest.getFileMap().values().iterator().next();

        try {
            FormDeploymentBuilder deploymentBuilder = formRepositoryService.createDeployment();
            String fileName = file.getOriginalFilename();
            if (StringUtils.isEmpty(fileName) || (!fileName.endsWith(".form") && !fileName.endsWith(".json"))) {
                fileName = file.getName();
            }
            if (fileName.endsWith(".form") || fileName.endsWith(".json")) {
                deploymentBuilder.addInputStream(fileName, file.getInputStream());
            } else {
                throw new FlowableIllegalArgumentException("File must be of type .json or .form");
            }
            deploymentBuilder.name(fileName);

            if (tenantId != null) {
                deploymentBuilder.tenantId(tenantId);
            }

            FormDeployment deployment = deploymentBuilder.deploy();
            response.setStatus(HttpStatus.CREATED.value());

            return formRestResponseFactory.createFormDeploymentResponse(deployment);

        } catch (Exception e) {
            if (e instanceof FlowableException) {
                throw (FlowableException) e;
            }
            throw new FlowableException(e.getMessage(), e);
        }
    }
}
