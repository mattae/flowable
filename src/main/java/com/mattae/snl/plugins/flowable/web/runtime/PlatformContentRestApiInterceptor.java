package com.mattae.snl.plugins.flowable.web.runtime;

import com.mattae.snl.plugins.flowable.content.api.*;
import com.mattae.snl.plugins.flowable.web.runtime.model.ContentRestApiInterceptor;
import com.mattae.snl.plugins.flowable.web.runtime.model.RenditionItemQueryRequest;
import org.flowable.content.api.ContentItem;
import org.springframework.stereotype.Service;

@Service
public class PlatformContentRestApiInterceptor implements ContentRestApiInterceptor {


    @Override
    public void accessContentItemInfoById(ContentItem contentItemId) {

    }

    @Override
    public void accessDeploymentById(DocumentDeployment paramDocumentDeployment) {

    }

    @Override
    public void accessDeploymentsWithQuery(DocumentDeploymentQuery paramDocumentDeploymentQuery) {

    }

    @Override
    public void executeNewDeploymentForTenantId(String paramString) {

    }

    @Override
    public void deleteDeployment(DocumentDeployment paramDocumentDeployment) {

    }

    @Override
    public void accessDocumentDefinitionById(DocumentDefinition paramDocumentDefinition) {

    }

    @Override
    public void accessDocumentDefinitionsWithQuery(DocumentDefinitionQuery paramDocumentDefinitionQuery) {

    }

    @Override
    public void accessRenditionItemInfoById(RenditionItem paramRenditionItem) {

    }

    @Override
    public void accessRenditionItemInfoWithQuery(RenditionItemQuery paramRenditionItemQuery, RenditionItemQueryRequest paramRenditionItemQueryRequest) {

    }

    @Override
    public void deleteRenditionItem(RenditionItem paramRenditionItem) {

    }

    @Override
    public void accessContentManagementInfo() {

    }

    @Override
    public void accessCaseInstanceById(String paramString) {

    }

    @Override
    public void accessProcessInstanceById(String paramString) {

    }

    @Override
    public void accessTaskInstanceById(String paramString) {

    }
}
