package com.mattae.snl.plugins.flowable.web.runtime;

import com.mattae.snl.plugins.flowable.content.api.DocumentDefinition;
import com.mattae.snl.plugins.flowable.content.api.DocumentDefinitionModel;
import com.mattae.snl.plugins.flowable.content.api.DocumentRepositoryService;
import com.mattae.snl.plugins.flowable.web.runtime.model.ContentRestApiInterceptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DocumentDefinitionModelResource extends BaseDocumentDefinitionResource {
    public DocumentDefinitionModelResource(DocumentRepositoryService repositoryService,
                                           ContentRestApiInterceptor restApiInterceptor) {
        super(repositoryService, restApiInterceptor);
    }

    @GetMapping(value = {"/document-repository/document-definitions/{documentDefinitionId}/model"}, produces = {"application/json"})
    public DocumentDefinitionModel getModelResource(@PathVariable String documentDefinitionId) {
        DocumentDefinition documentDefinition = getDocumentDefinitionFromRequest(documentDefinitionId);
        return this.repositoryService.getDocumentDefinitionModel(documentDefinition.getId());
    }
}
