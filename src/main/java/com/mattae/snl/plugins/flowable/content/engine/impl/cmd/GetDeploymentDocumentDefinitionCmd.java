package com.mattae.snl.plugins.flowable.content.engine.impl.cmd;

import com.mattae.snl.plugins.flowable.content.api.DocumentDefinition;
import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;

import java.io.Serializable;

public class GetDeploymentDocumentDefinitionCmd extends AbstractNeedsDocumentDefinitionCmd implements Command<DocumentDefinition>, Serializable {
    private static final long serialVersionUID = 1L;

    protected String documentDefinitionId;

    protected String documentDefinitionKey;

    protected String tenantId;

    public GetDeploymentDocumentDefinitionCmd(String documentDefinitionId) {
        this.documentDefinitionId = documentDefinitionId;
    }

    public GetDeploymentDocumentDefinitionCmd(String documentDefinitionKey, String tenantId) {
        this.documentDefinitionKey = documentDefinitionKey;
        this.tenantId = tenantId;
    }

    public DocumentDefinition execute(CommandContext commandContext) {
        DocumentDefinition documentDefinition = resolveDocumentDefinition(commandContext, this.documentDefinitionId, this.documentDefinitionKey, this.tenantId);
        return CommandContextUtil.getDeploymentManager(commandContext).findDeployedDocumentDefinitionById(documentDefinition.getId());
    }
}
