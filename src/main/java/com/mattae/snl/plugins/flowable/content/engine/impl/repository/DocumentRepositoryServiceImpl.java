package com.mattae.snl.plugins.flowable.content.engine.impl.repository;

import com.mattae.snl.plugins.flowable.content.api.*;
import com.mattae.snl.plugins.flowable.content.config.ContentEngineConfiguration;
import com.mattae.snl.plugins.flowable.content.engine.impl.cmd.*;
import org.flowable.common.engine.impl.service.CommonEngineServiceImpl;

import java.io.InputStream;
import java.util.List;

public class DocumentRepositoryServiceImpl extends CommonEngineServiceImpl<ContentEngineConfiguration> implements DocumentRepositoryService {
    public DocumentRepositoryServiceImpl(ContentEngineConfiguration engineConfiguration) {
        super(engineConfiguration);
    }

    public DocumentDeploymentBuilder createDeployment() {
        return this.commandExecutor.execute(commandContext -> new DocumentDeploymentBuilderImpl());
    }

    public List<String> getDeploymentResourceNames(String deploymentId) {
        return this.commandExecutor.execute(new GetDeploymentResourceNamesCmd(deploymentId));
    }

    public InputStream getResourceAsStream(String deploymentId, String resourceName) {
        return this.commandExecutor.execute(new GetDeploymentResourceCmd(deploymentId, resourceName));
    }

    public DocumentDeployment deploy(DocumentDeploymentBuilderImpl deploymentBuilder) {
        return this.commandExecutor.execute(new DeployCmd(deploymentBuilder));
    }

    public DocumentDefinition getDocumentDefinition(String documentDefinitionId) {
        return this.commandExecutor.execute(new GetDeploymentDocumentDefinitionCmd(documentDefinitionId));
    }

    public DocumentDefinition getDocumentDefinitionByKey(String documentDefinitionKey) {
        return this.commandExecutor.execute(new GetDeploymentDocumentDefinitionCmd(documentDefinitionKey, null));
    }

    public DocumentDefinition getDocumentDefinitionByKeyAndTenantId(String documentDefinitionKey, String tenantId) {
        return this.commandExecutor.execute(new GetDeploymentDocumentDefinitionCmd(documentDefinitionKey, tenantId));
    }

    public DocumentDefinitionModel getDocumentDefinitionModel(String documentDefinitionId) {
        return this.commandExecutor.execute(new GetDocumentDefinitionModelCmd(documentDefinitionId));
    }

    public void setDocumentDefinitionCategory(String documentDefinitionId, String category) {
        this.commandExecutor.execute(new SetDocumentDefinitionCategoryCmd(documentDefinitionId, category));
    }

    public String convertDocumentDefinitionModelToJson(String documentDefinitionId) {
        return this.commandExecutor.execute(new GetDocumentDefinitionModelJsonCmd(documentDefinitionId));
    }

    public void deleteDeployment(String deploymentId) {
        this.commandExecutor.execute(new DeleteDeploymentCmd(deploymentId));
    }

    public DocumentDeploymentQuery createDeploymentQuery() {
        return this.configuration.getDocumentDeploymentEntityManager().createDocumentDeploymentQuery();
    }

    public DocumentDefinitionQuery createDocumentDefinitionQuery() {
        return this.configuration.getDocumentDefinitionEntityManager().createDocumentDefinitionQuery();
    }
}
