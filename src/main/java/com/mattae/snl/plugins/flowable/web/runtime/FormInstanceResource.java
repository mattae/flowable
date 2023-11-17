package com.mattae.snl.plugins.flowable.web.runtime;

import jakarta.servlet.http.HttpServletRequest;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.form.api.FormInstance;
import org.flowable.form.api.FormInstanceInfo;
import org.flowable.form.api.FormService;
import org.flowable.form.rest.FormRestResponseFactory;
import org.flowable.form.rest.service.api.form.FormInstanceModelResponse;
import org.flowable.form.rest.service.api.form.FormInstanceResponse;
import org.flowable.form.rest.service.api.form.FormRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yvo Swillens
 */
@RestController
@RequestMapping("/api/rest")
@Transactional
public class FormInstanceResource {

    protected final FormService formService;

    protected final FormRestResponseFactory formRestResponseFactory;

    public FormInstanceResource(FormService formService, FormRestResponseFactory formRestResponseFactory) {
        this.formService = formService;
        this.formRestResponseFactory = formRestResponseFactory;
    }

    @GetMapping(value = "/form/form-instance/{formInstanceId}", produces = "application/json")
    public FormInstanceResponse getFormInstance(@PathVariable String formInstanceId, HttpServletRequest request) {
        FormInstance formInstance = formService.createFormInstanceQuery().id(formInstanceId).singleResult();

        if (formInstance == null) {
            throw new FlowableObjectNotFoundException("Could not find a form instance");
        }

        return formRestResponseFactory.createFormInstanceResponse(formInstance);
    }

    @PostMapping(value = "/form/form-instance-model", produces = "application/json")
    public FormInstanceModelResponse getFormInstanceModel(@RequestBody FormRequest formRequest, HttpServletRequest request) {

        FormInstanceInfo formInstanceModel = null;

        boolean fallbackToDefaultTenant = false;
        if (formRequest.getFallbackToDefaultTenant() != null) {
            fallbackToDefaultTenant = formRequest.getFallbackToDefaultTenant();
        }

        if (formRequest.getFormInstanceId() != null) {
            formInstanceModel = formService.getFormInstanceModelById(
                formRequest.getFormInstanceId(),
                null);
        } else if (formRequest.getParentDeploymentId() != null) {
            formInstanceModel = formService.getFormInstanceModelByKeyAndParentDeploymentId(
                formRequest.getParentDeploymentId(),
                formRequest.getFormDefinitionKey(),
                formRequest.getTaskId(),
                formRequest.getProcessInstanceId(),
                formRequest.getVariables(),
                formRequest.getTenantId(),
                fallbackToDefaultTenant);

        } else if (formRequest.getFormDefinitionKey() != null) {
            formInstanceModel = formService.getFormInstanceModelByKey(
                formRequest.getFormDefinitionKey(),
                formRequest.getTaskId(),
                formRequest.getProcessInstanceId(),
                formRequest.getVariables(),
                formRequest.getTenantId(),
                fallbackToDefaultTenant);

        } else if (formRequest.getFormDefinitionId() != null) {
            formInstanceModel = formService.getFormInstanceModelById(
                formRequest.getFormDefinitionId(),
                formRequest.getTaskId(),
                formRequest.getProcessInstanceId(),
                formRequest.getVariables(),
                formRequest.getTenantId(),
                fallbackToDefaultTenant);

        } else {
            throw new FlowableIllegalArgumentException("Either parent deployment key, form definition key or form definition id must be provided in the request");
        }

        if (formInstanceModel == null) {
            throw new FlowableObjectNotFoundException("Could not find a form instance");
        }

        return formRestResponseFactory.createFormInstanceModelResponse(formInstanceModel);
    }
}
