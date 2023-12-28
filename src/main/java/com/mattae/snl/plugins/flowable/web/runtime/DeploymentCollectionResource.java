package com.mattae.snl.plugins.flowable.web.runtime;

import com.mattae.snl.plugins.flowable.content.api.DocumentDeployment;
import com.mattae.snl.plugins.flowable.content.api.DocumentDeploymentBuilder;
import com.mattae.snl.plugins.flowable.content.api.DocumentDeploymentQuery;
import com.mattae.snl.plugins.flowable.content.api.DocumentRepositoryService;
import com.mattae.snl.plugins.flowable.content.engine.impl.repository.DocumentDeploymentQueryProperty;
import com.mattae.snl.plugins.flowable.services.model.DocumentDeploymentResponse;
import com.mattae.snl.plugins.flowable.web.runtime.model.ContentRestApiInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.api.query.QueryProperty;
import org.flowable.common.rest.api.DataResponse;
import org.flowable.common.rest.api.PaginateListUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class DeploymentCollectionResource {
    protected static final String DEPRECATED_API_DEPLOYMENT_SEGMENT = "deployment";

    private static Map<String, QueryProperty> allowedSortProperties = new HashMap<>();

    static {
        allowedSortProperties.put("id", DocumentDeploymentQueryProperty.DEPLOYMENT_ID);
        allowedSortProperties.put("name", DocumentDeploymentQueryProperty.DEPLOYMENT_NAME);
        allowedSortProperties.put("deployTime", DocumentDeploymentQueryProperty.DEPLOY_TIME);
        allowedSortProperties.put("tenantId", DocumentDeploymentQueryProperty.DEPLOYMENT_TENANT_ID);
    }

    protected final ContentRestResponseFactory restResponseFactory;
    protected final DocumentRepositoryService repositoryService;
    protected final ContentRestApiInterceptor restApiInterceptor;

    @GetMapping(value = {"/document-repository/deployments"}, produces = {"application/json"})
    public DataResponse<DocumentDeploymentResponse> getDeployments(@RequestParam Map<String, String> allRequestParams, HttpServletRequest request) {
        DocumentDeploymentQuery deploymentQuery = this.repositoryService.createDeploymentQuery();
        if (allRequestParams.containsKey("name"))
            deploymentQuery.deploymentName(allRequestParams.get("name"));
        if (allRequestParams.containsKey("nameLike"))
            deploymentQuery.deploymentNameLike(allRequestParams.get("nameLike"));
        if (allRequestParams.containsKey("category"))
            deploymentQuery.deploymentCategory(allRequestParams.get("category"));
        if (allRequestParams.containsKey("categoryNotEquals"))
            deploymentQuery.deploymentCategoryNotEquals(allRequestParams.get("categoryNotEquals"));
        if (allRequestParams.containsKey("parentDeploymentId"))
            deploymentQuery.parentDeploymentId(allRequestParams.get("parentDeploymentId"));
        if (allRequestParams.containsKey("parentDeploymentIdLike"))
            deploymentQuery.parentDeploymentIdLike(allRequestParams.get("parentDeploymentIdLike"));
        if (allRequestParams.containsKey("tenantId"))
            deploymentQuery.deploymentTenantId(allRequestParams.get("tenantId"));
        if (allRequestParams.containsKey("tenantIdLike"))
            deploymentQuery.deploymentTenantIdLike(allRequestParams.get("tenantIdLike"));
        if (allRequestParams.containsKey("withoutTenantId")) {
            Boolean withoutTenantId = Boolean.valueOf(allRequestParams.get("withoutTenantId"));
            if (withoutTenantId.booleanValue())
                deploymentQuery.deploymentWithoutTenantId();
        }
        if (this.restApiInterceptor != null)
            this.restApiInterceptor.accessDeploymentsWithQuery(deploymentQuery);
        Objects.requireNonNull(this.restResponseFactory);
        return PaginateListUtil.paginateList(allRequestParams, deploymentQuery, "id", allowedSortProperties, this.restResponseFactory::createDeploymentResponseList);
    }

    @PostMapping(value = {"/document-repository/deployments"}, produces = {"application/json"}, consumes = {"multipart/form-data"})
    public DocumentDeploymentResponse uploadDeployment(@RequestParam(value = "deploymentKey", required = false) String deploymentKey, @RequestParam(value = "deploymentName", required = false) String deploymentName, @RequestParam(value = "tenantId", required = false) String tenantId, HttpServletRequest request, HttpServletResponse response) {
        if (!(request instanceof MultipartHttpServletRequest))
            throw new FlowableIllegalArgumentException("Multipart request is required");
        if (this.restApiInterceptor != null)
            this.restApiInterceptor.executeNewDeploymentForTenantId(tenantId);
        String queryString = request.getQueryString();
        Map<String, String> decodedQueryStrings = splitQueryString(queryString);
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        if (multipartRequest.getFileMap().isEmpty())
            throw new FlowableIllegalArgumentException("Multipart request with file content is required");
        MultipartFile file = multipartRequest.getFileMap().values().iterator().next();
        try {
            DocumentDeploymentBuilder deploymentBuilder = this.repositoryService.createDeployment();
            String fileName = file.getOriginalFilename();
            if (StringUtils.isEmpty(fileName) || !fileName.endsWith(".document"))
                fileName = file.getName();
            if (fileName.endsWith(".document")) {
                deploymentBuilder.addInputStream(fileName, file.getInputStream());
            } else {
                throw new FlowableIllegalArgumentException("File must be of type .document");
            }
            if (!decodedQueryStrings.containsKey("deploymentName") || StringUtils.isEmpty(decodedQueryStrings.get("deploymentName"))) {
                String fileNameWithoutExtension = fileName.split("\\.")[0];
                if (StringUtils.isNotEmpty(fileNameWithoutExtension))
                    fileName = fileNameWithoutExtension;
                deploymentBuilder.name(fileName);
            } else {
                deploymentBuilder.name(decodedQueryStrings.get("deploymentName"));
            }
            if (decodedQueryStrings.containsKey("deploymentKey") || StringUtils.isNotEmpty(decodedQueryStrings.get("deploymentKey")))
                deploymentBuilder.key(decodedQueryStrings.get("deploymentKey"));
            if (tenantId != null)
                deploymentBuilder.tenantId(tenantId);
            DocumentDeployment deployment = deploymentBuilder.deploy();
            response.setStatus(HttpStatus.CREATED.value());
            return this.restResponseFactory.createDeploymentResponse(deployment);
        } catch (FlowableException e) {
            throw e;
        } catch (Exception e) {
            throw new FlowableException(e.getMessage(), e);
        }
    }

    public Map<String, String> splitQueryString(String queryString) {
        if (StringUtils.isEmpty(queryString))
            return Collections.emptyMap();
        Map<String, String> queryMap = new HashMap<>();
        for (String param : queryString.split("&"))
            queryMap.put(StringUtils.substringBefore(param, "="), decode(StringUtils.substringAfter(param, "=")));
        return queryMap;
    }

    protected String decode(String string) {
        if (string != null)
            try {
                return URLDecoder.decode(string, "UTF-8");
            } catch (UnsupportedEncodingException uee) {
                throw new IllegalStateException("JVM does not support UTF-8 encoding.", uee);
            }
        return null;
    }
}
