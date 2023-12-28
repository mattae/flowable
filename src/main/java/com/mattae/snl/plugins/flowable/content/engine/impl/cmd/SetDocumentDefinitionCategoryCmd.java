package com.mattae.snl.plugins.flowable.content.engine.impl.cmd;

import com.mattae.snl.plugins.flowable.content.api.DocumentDefinition;
import com.mattae.snl.plugins.flowable.content.deployer.DocumentDefinitionCacheEntry;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.DocumentDefinitionEntity;
import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.common.engine.impl.persistence.deploy.DeploymentCache;

public class SetDocumentDefinitionCategoryCmd implements Command<Void> {
    protected String documentDefinitionId;

    protected String category;

    public SetDocumentDefinitionCategoryCmd(String documentDefinitionId, String category) {
        this.documentDefinitionId = documentDefinitionId;
        this.category = category;
    }

    public Void execute(CommandContext commandContext) {
        if (this.documentDefinitionId == null)
            throw new FlowableIllegalArgumentException("Document definition id is null");
        DocumentDefinitionEntity documentDefinition = CommandContextUtil.getDocumentDefinitionEntityManager(commandContext).findById(this.documentDefinitionId);
        if (documentDefinition == null)
            throw new FlowableObjectNotFoundException("No document definition found for id = '" + this.documentDefinitionId + "'", DocumentDefinition.class);
        documentDefinition.setCategory(this.category);
        DeploymentCache<DocumentDefinitionCacheEntry> documentDefinitionCache = CommandContextUtil.getContentEngineConfiguration(commandContext).getDocumentDefinitionCache();
        if (documentDefinitionCache != null)
            documentDefinitionCache.remove(this.documentDefinitionId);
        return null;
    }

    public String getDocumentDefinitionId() {
        return this.documentDefinitionId;
    }

    public void setDocumentDefinitionId(String documentDefinitionId) {
        this.documentDefinitionId = documentDefinitionId;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
