package com.mattae.snl.plugins.flowable.content.deployer;

import com.mattae.snl.plugins.flowable.content.api.DocumentDefinitionModel;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.DocumentDefinitionEntity;

import java.io.Serializable;

public class DocumentDefinitionCacheEntry implements Serializable {
    private static final long serialVersionUID = 1L;

    protected DocumentDefinitionEntity documentDefinitionEntity;

    protected DocumentDefinitionModel documentDefinitionModel;

    public DocumentDefinitionCacheEntry(DocumentDefinitionEntity documentDefinitionEntity, DocumentDefinitionModel documentDefinitionModel) {
        this.documentDefinitionEntity = documentDefinitionEntity;
        this.documentDefinitionModel = documentDefinitionModel;
    }

    public DocumentDefinitionEntity getDocumentDefinitionEntity() {
        return this.documentDefinitionEntity;
    }

    public void setDocumentDefinitionEntity(DocumentDefinitionEntity documentDefinitionEntity) {
        this.documentDefinitionEntity = documentDefinitionEntity;
    }

    public DocumentDefinitionModel getDocumentDefinitionModel() {
        return this.documentDefinitionModel;
    }

    public void setDocumentDefinitionModel(DocumentDefinitionModel documentDefinitionModel) {
        this.documentDefinitionModel = documentDefinitionModel;
    }
}
