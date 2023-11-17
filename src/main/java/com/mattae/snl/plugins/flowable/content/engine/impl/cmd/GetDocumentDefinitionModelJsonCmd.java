package com.mattae.snl.plugins.flowable.content.engine.impl.cmd;

import com.mattae.snl.plugins.flowable.content.api.DocumentDefinition;
import com.mattae.snl.plugins.flowable.content.config.ContentEngineConfiguration;
import com.mattae.snl.plugins.flowable.content.deployer.DocumentDeploymentManager;
import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;

public class GetDocumentDefinitionModelJsonCmd implements Command<String> {
    protected String documentDefinitionId;

    public GetDocumentDefinitionModelJsonCmd(String documentDefinitionId) {
        this.documentDefinitionId = documentDefinitionId;
    }

    public String execute(CommandContext commandContext) {
        if (this.documentDefinitionId == null)
            throw new FlowableIllegalArgumentException("documentDefinitionId is null");
        ContentEngineConfiguration contentEngineConfiguration = CommandContextUtil.getContentEngineConfiguration(commandContext);
        DocumentDeploymentManager deploymentManager = contentEngineConfiguration.getDeploymentManager();
        DocumentDefinition documentDefinition = deploymentManager.findDeployedDocumentDefinitionById(this.documentDefinitionId);
        if (documentDefinition != null)
            return contentEngineConfiguration.getDocumentResourceConverter().convertDocumentDefinitionModelToJson(deploymentManager
                .resolveDocumentDefinition(documentDefinition).getDocumentDefinitionModel());
        return null;
    }
}
