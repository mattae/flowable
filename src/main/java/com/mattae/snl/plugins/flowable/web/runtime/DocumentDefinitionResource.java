package com.mattae.snl.plugins.flowable.web.runtime;

import com.mattae.snl.plugins.flowable.content.api.DocumentDefinition;
import com.mattae.snl.plugins.flowable.content.api.DocumentDefinitionModel;
import com.mattae.snl.plugins.flowable.content.api.DocumentDeployment;
import com.mattae.snl.plugins.flowable.content.api.DocumentRepositoryService;
import com.mattae.snl.plugins.flowable.services.model.DocumentDefinitionResponse;
import com.mattae.snl.plugins.flowable.web.runtime.model.ContentRestApiInterceptor;
import com.mattae.snl.plugins.flowable.web.runtime.model.DocumentDefinitionActionRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.form.api.FormInfo;
import org.flowable.form.api.FormModel;
import org.flowable.form.api.FormRepositoryService;
import org.springframework.web.bind.annotation.*;

@RestController
public class DocumentDefinitionResource extends BaseDocumentDefinitionResource {
    protected final FormRepositoryService formRepositoryService;

    public DocumentDefinitionResource(DocumentRepositoryService repositoryService,
                                      ContentRestApiInterceptor restApiInterceptor,
                                      FormRepositoryService formRepositoryService) {
        super(repositoryService, restApiInterceptor);
        this.formRepositoryService = formRepositoryService;
    }

    @GetMapping(value = {"/document-repository/document-definitions/{documentDefinitionId}"}, produces = {"application/json"})
    public DocumentDefinitionResponse getDocumentDefinition(@PathVariable String documentDefinitionId, HttpServletRequest request) {
        DocumentDefinition documentDefinition = getDocumentDefinitionFromRequest(documentDefinitionId);

        return this.restResponseFactory.createDocumentDefinitionResponse(documentDefinition);
    }

    @PutMapping(value = {"/document-repository/document-definitions/{documentDefinitionId}"}, produces = {"application/json"})
    public DocumentDefinitionResponse executeDocumentDefinitionAction(@PathVariable String documentDefinitionId, @RequestBody DocumentDefinitionActionRequest actionRequest, HttpServletRequest request) {
        if (actionRequest == null)
            throw new FlowableIllegalArgumentException("No action found in request body.");
        DocumentDefinition documentDefinition = getDocumentDefinitionFromRequest(documentDefinitionId);
        if (actionRequest.getCategory() != null) {
            this.repositoryService.setDocumentDefinitionCategory(documentDefinition.getId(), actionRequest.getCategory());
            DocumentDefinitionResponse response = this.restResponseFactory.createDocumentDefinitionResponse(documentDefinition);
            response.setCategory(actionRequest.getCategory());
            return response;
        }
        throw new FlowableIllegalArgumentException("Category missing in request");
    }

    @GetMapping(value = {"/document-repository/document-definitions/{documentDefinitionId}/form-model"}, produces = {"application/json"})
    public FormModel getDocumentDefinitionForm(@PathVariable String documentDefinitionId) {
        if (this.formRepositoryService == null)
            throw new FlowableException("unable to retrieve form for document definition");
        DocumentDefinition documentDefinition = getDocumentDefinitionFromRequest(documentDefinitionId);
        DocumentDefinitionModel documentDefinitionModel = this.repositoryService.getDocumentDefinitionModel(documentDefinitionId);

        String formKey = documentDefinitionModel.getFormKey();
        if (formKey == null)
            throw new FlowableObjectNotFoundException("Form not found");
        DocumentDeployment documentDeployment = this.repositoryService.createDeploymentQuery().deploymentId(documentDefinition.getDeploymentId()).singleResult();
        FormInfo formInfo = this.formRepositoryService.getFormModelByKeyAndParentDeploymentId(formKey, documentDeployment
            .getParentDeploymentId(), documentDefinition.getTenantId(), true);
        if (formInfo != null) {
            return formInfo.getFormModel();
        }
        throw new FlowableObjectNotFoundException("Form for action instance not found");
    }
}
