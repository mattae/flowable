package com.mattae.snl.plugins.flowable.content.engine.impl.cmd;

import com.mattae.snl.plugins.flowable.content.api.DocumentDefinition;
import com.mattae.snl.plugins.flowable.content.api.DocumentDefinitionModel;
import com.mattae.snl.plugins.flowable.content.deployer.DocumentDeploymentManager;
import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;

public class GetDocumentDefinitionModelCmd implements Command<DocumentDefinitionModel> {
    protected String documentDefinitionId;

    public GetDocumentDefinitionModelCmd(String documentDefinitionId) {
        this.documentDefinitionId = documentDefinitionId;
    }

    public DocumentDefinitionModel execute(CommandContext commandContext) {
        if (this.documentDefinitionId == null)
            throw new FlowableIllegalArgumentException("documentDefinitionId is null");
        DocumentDeploymentManager deploymentManager = CommandContextUtil.getDeploymentManager(commandContext);
        DocumentDefinition documentDefinition = deploymentManager.findDeployedDocumentDefinitionById(this.documentDefinitionId);
        if (documentDefinition != null)
            return deploymentManager.resolveDocumentDefinition(documentDefinition).getDocumentDefinitionModel();
        return null;
    }
}
