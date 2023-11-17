package com.mattae.snl.plugins.flowable.web.runtime;

import com.mattae.snl.plugins.flowable.content.api.DocumentDefinition;
import com.mattae.snl.plugins.flowable.content.api.DocumentRepositoryService;
import com.mattae.snl.plugins.flowable.web.runtime.model.ContentRestApiInterceptor;
import lombok.RequiredArgsConstructor;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;

@RequiredArgsConstructor
public class BaseDocumentDefinitionResource {
    protected final DocumentRepositoryService repositoryService;
    protected final ContentRestApiInterceptor restApiInterceptor;
    protected ContentRestResponseFactory restResponseFactory;

    protected DocumentDefinition getDocumentDefinitionFromRequest(String documentDefinitionId) {
        DocumentDefinition documentDefinition = this.repositoryService.getDocumentDefinition(documentDefinitionId);
        if (documentDefinition == null)
            throw new FlowableObjectNotFoundException("Could not find a document definition with id '" + documentDefinitionId + "'.", DocumentDefinition.class);
        if (this.restApiInterceptor != null)
            this.restApiInterceptor.accessDocumentDefinitionById(documentDefinition);
        return documentDefinition;
    }
}
