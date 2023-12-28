package com.mattae.snl.plugins.flowable.web.runtime.model;

import com.mattae.snl.plugins.flowable.content.api.*;
import org.flowable.content.api.ContentItem;

public interface ContentRestApiInterceptor {
    void accessContentItemInfoById(ContentItem contentItemId);

    void accessDeploymentById(DocumentDeployment paramDocumentDeployment);

    void accessDeploymentsWithQuery(DocumentDeploymentQuery paramDocumentDeploymentQuery);

    void executeNewDeploymentForTenantId(String paramString);

    void deleteDeployment(DocumentDeployment paramDocumentDeployment);

    void accessDocumentDefinitionById(DocumentDefinition paramDocumentDefinition);

    void accessDocumentDefinitionsWithQuery(DocumentDefinitionQuery paramDocumentDefinitionQuery);

    void accessRenditionItemInfoById(RenditionItem paramRenditionItem);

    void accessRenditionItemInfoWithQuery(RenditionItemQuery paramRenditionItemQuery, RenditionItemQueryRequest paramRenditionItemQueryRequest);

    void deleteRenditionItem(RenditionItem paramRenditionItem);

    void accessContentManagementInfo();

    void accessCaseInstanceById(String paramString);

    void accessProcessInstanceById(String paramString);

    void accessTaskInstanceById(String paramString);
}
