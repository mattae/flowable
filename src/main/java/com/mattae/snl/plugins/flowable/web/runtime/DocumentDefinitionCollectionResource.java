package com.mattae.snl.plugins.flowable.web.runtime;

import com.mattae.snl.plugins.flowable.content.api.DocumentDefinitionQuery;
import com.mattae.snl.plugins.flowable.content.api.DocumentRepositoryService;
import com.mattae.snl.plugins.flowable.content.engine.impl.repository.DocumentDefinitionQueryProperty;
import com.mattae.snl.plugins.flowable.services.model.DocumentDefinitionResponse;
import com.mattae.snl.plugins.flowable.web.runtime.model.ContentRestApiInterceptor;
import com.mattae.snl.plugins.flowable.web.runtime.model.DocumentDefinitionQueryRequest;
import org.apache.commons.lang3.StringUtils;
import org.flowable.common.engine.api.query.QueryProperty;
import org.flowable.common.rest.api.DataResponse;
import org.flowable.common.rest.api.PaginateListUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class DocumentDefinitionCollectionResource {
    private static final Map<String, QueryProperty> properties = new HashMap<>();

    static {
        properties.put("id", DocumentDefinitionQueryProperty.DOCUMENT_DEFINITION_ID);
        properties.put("key", DocumentDefinitionQueryProperty.DOCUMENT_DEFINITION_KEY);
        properties.put("category", DocumentDefinitionQueryProperty.DOCUMENT_DEFINITION_CATEGORY);
        properties.put("name", DocumentDefinitionQueryProperty.DOCUMENT_DEFINITION_NAME);
        properties.put("version", DocumentDefinitionQueryProperty.DOCUMENT_DEFINITION_VERSION);
        properties.put("deploymentId", DocumentDefinitionQueryProperty.DOCUMENT_DEFINITION_DEPLOYMENT_ID);
        properties.put("tenantId", DocumentDefinitionQueryProperty.DOCUMENT_DEFINITION_TENANT_ID);
    }

    protected final ContentRestResponseFactory restResponseFactory;
    protected final DocumentRepositoryService repositoryService;
    protected final ContentRestApiInterceptor restApiInterceptor;
    @Value("${flowable.platform.rest.default-list-response-size:100}")
    protected Integer defaultListResponseSize;

    public DocumentDefinitionCollectionResource(ContentRestResponseFactory restResponseFactory,
                                                DocumentRepositoryService repositoryService,
                                                ContentRestApiInterceptor restApiInterceptor) {
        this.restResponseFactory = restResponseFactory;
        this.repositoryService = repositoryService;
        this.restApiInterceptor = restApiInterceptor;
    }

    @GetMapping(value = {"/document-repository/document-definitions"}, produces = {"application/json"})
    public DataResponse<DocumentDefinitionResponse> getDocumentDefinitions(@ModelAttribute DocumentDefinitionQueryRequest request) {
        DocumentDefinitionQuery documentDefinitionQuery = this.repositoryService.createDocumentDefinitionQuery();
        if (StringUtils.isNotEmpty(request.getId()))
            documentDefinitionQuery.id(request.getId());
        if (StringUtils.isNotEmpty(request.getIds()))
            documentDefinitionQuery.ids(convertToList(request.getIds()));
        if (StringUtils.isNotEmpty(request.getKey()))
            documentDefinitionQuery.key(request.getKey());
        if (StringUtils.isNotEmpty(request.getKeyLike()))
            documentDefinitionQuery.keyLike(request.getKeyLike());
        if (StringUtils.isNotEmpty(request.getKeys()))
            documentDefinitionQuery.keys(convertToList(request.getKeys()));
        if (StringUtils.isNotEmpty(request.getName()))
            documentDefinitionQuery.name(request.getName());
        if (StringUtils.isNotEmpty(request.getNameLike()))
            documentDefinitionQuery.nameLike(request.getNameLike());
        if (StringUtils.isNotEmpty(request.getCategory()))
            documentDefinitionQuery.category(request.getCategory());
        if (StringUtils.isNotEmpty(request.getCategoryLike()))
            documentDefinitionQuery.categoryLike(request.getCategoryLike());
        if (request.getVersion() != null)
            documentDefinitionQuery.version(request.getVersion());
        if (request.getLatest() != null && request.getLatest())
            documentDefinitionQuery.latestVersion();
        if (StringUtils.isNotEmpty(request.getDeploymentId()))
            documentDefinitionQuery.deploymentId(request.getDeploymentId());
        if (StringUtils.isNotEmpty(request.getAccessibleByUser()))
            documentDefinitionQuery.accessibleByUser(request.getAccessibleByUser());
        if (StringUtils.isNotEmpty(request.getTenantId()))
            documentDefinitionQuery.tenantId(request.getTenantId());
        if (StringUtils.isNotEmpty(request.getTenantIdLike()))
            documentDefinitionQuery.tenantIdLike(request.getTenantIdLike());
        if (this.restApiInterceptor != null)
            this.restApiInterceptor.accessDocumentDefinitionsWithQuery(documentDefinitionQuery);
        if (request.getSize() == null || request.getSize() <= 0)
            request.setSize(this.defaultListResponseSize);
        Objects.requireNonNull(this.restResponseFactory);
        return PaginateListUtil.paginateList(request, documentDefinitionQuery, "name", properties, this.restResponseFactory::createDocumentDefinitionResponseList);
    }

    protected List<String> convertToList(String requestValue) {
        if (StringUtils.isNotEmpty(requestValue)) {
            String[] values = requestValue.split(",");
            List<String> valueList = new ArrayList<>();
            for (String value : values)
                valueList.add(value.trim());
            return valueList;
        }
        return null;
    }
}
