package com.mattae.snl.plugins.flowable.web.runtime;

import com.mattae.snl.plugins.flowable.content.api.DocumentDefinition;
import com.mattae.snl.plugins.flowable.content.api.DocumentRepositoryService;
import com.mattae.snl.plugins.flowable.web.runtime.model.ContentRestApiInterceptor;
import jakarta.servlet.http.HttpServletResponse;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.rest.resolver.ContentTypeResolver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DocumentDefinitionResourceDataResource extends BaseDeploymentResourceDataResource {
    public DocumentDefinitionResourceDataResource(ContentTypeResolver contentTypeResolver, DocumentRepositoryService repositoryService, ContentRestApiInterceptor restApiInterceptor) {
        super(contentTypeResolver, repositoryService, restApiInterceptor);
    }

    @GetMapping({"/document-repository/document-definitions/{documentDefinitionId}/resourcedata"})
    public byte[] getDocumentDefinitionResource(@PathVariable String documentDefinitionId, HttpServletResponse response) {
        DocumentDefinition documentDefinition = getDocumentDefinitionFromRequest(documentDefinitionId);
        return getDeploymentResourceData(documentDefinition.getDeploymentId(), documentDefinition.getResourceName(), response);
    }

    protected DocumentDefinition getDocumentDefinitionFromRequest(String documentDefinitionId) {
        DocumentDefinition documentDefinition = this.repositoryService.createDocumentDefinitionQuery().id(documentDefinitionId).singleResult();
        if (documentDefinition == null)
            throw new FlowableObjectNotFoundException("Could not find a document definition with id '" + documentDefinitionId + "'.", DocumentDefinition.class);
        if (this.restApiInterceptor != null)
            this.restApiInterceptor.accessDocumentDefinitionById(documentDefinition);
        return documentDefinition;
    }
}
