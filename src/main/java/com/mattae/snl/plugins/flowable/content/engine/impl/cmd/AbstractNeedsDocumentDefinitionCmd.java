package com.mattae.snl.plugins.flowable.content.engine.impl.cmd;

import com.mattae.snl.plugins.flowable.content.api.DocumentDefinition;
import com.mattae.snl.plugins.flowable.content.config.ContentEngineConfiguration;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.DocumentDefinitionEntity;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.DocumentDefinitionEntityManager;
import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.interceptor.CommandContext;

public abstract class AbstractNeedsDocumentDefinitionCmd {
    protected DocumentDefinition resolveDocumentDefinition(CommandContext commandContext, String documentDefinitionId, String documentDefinitionKey, String tenantId) {
        DocumentDefinitionEntity documentDefinitionEntity = null;
        ContentEngineConfiguration engineConfiguration = CommandContextUtil.getContentEngineConfiguration(commandContext);
        DocumentDefinitionEntityManager documentDefinitionEntityManager = engineConfiguration.getDocumentDefinitionEntityManager();
        DocumentDefinition documentDefinition = null;
        if (documentDefinitionId != null) {
            documentDefinition = (DocumentDefinition) documentDefinitionEntityManager.findById(documentDefinitionId);
            if (documentDefinition == null)
                throw new FlowableObjectNotFoundException("No document definition found for id = '" + documentDefinitionId + "'", DocumentDefinition.class);
        } else if (documentDefinitionKey != null) {
            if (tenantId == null || "".equals(tenantId)) {
                documentDefinitionEntity = documentDefinitionEntityManager.findLatestDocumentDefinitionByKey(documentDefinitionKey);
                if (documentDefinitionEntity == null)
                    throw new FlowableObjectNotFoundException("No document definition found for key '" + documentDefinitionKey + "'", DocumentDefinition.class);
            } else if (!"".equals(tenantId)) {
                documentDefinitionEntity = documentDefinitionEntityManager.findLatestDocumentDefinitionByKeyAndTenantId(documentDefinitionKey, tenantId);
                if (documentDefinitionEntity == null &&
                    engineConfiguration.isFallbackToDefaultTenant()) {
                    String defaultTenantValue = engineConfiguration.getDefaultTenantProvider().getDefaultTenant(tenantId, "document", documentDefinitionKey);
                    if (StringUtils.isNotEmpty(defaultTenantValue)) {
                        documentDefinitionEntity = documentDefinitionEntityManager.findLatestDocumentDefinitionByKeyAndTenantId(documentDefinitionKey, defaultTenantValue);
                    } else {
                        documentDefinitionEntity = documentDefinitionEntityManager.findLatestDocumentDefinitionByKey(documentDefinitionKey);
                    }
                }
                if (documentDefinitionEntity == null)
                    throw new FlowableObjectNotFoundException("Document definition not found", DocumentDefinition.class);
            }
        } else {
            throw new FlowableIllegalArgumentException("documentDefinitionKey and documentDefinitionId are null");
        }
        return documentDefinitionEntity;
    }
}
