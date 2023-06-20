package com.mattae.snl.plugins.flowable.web.runtime;

import io.swagger.annotations.*;
import jakarta.servlet.http.HttpServletRequest;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.engine.impl.form.FormData;
import org.flowable.form.api.FormInstance;
import org.flowable.form.api.FormService;
import org.flowable.form.rest.FormRestResponseFactory;
import org.flowable.form.rest.service.api.form.FormInstanceResponse;
import org.flowable.task.api.Task;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yvo Swillens
 */
@RestController
@Api(tags = {"Form Instances"}, description = "Manage Form Instances", authorizations = {@Authorization(value = "basicAuth")})
public class FormInstanceResource {

    protected final FormService formService;

    protected final FormRestResponseFactory formRestResponseFactory;

    public FormInstanceResource(FormService formService, FormRestResponseFactory formRestResponseFactory) {
        this.formService = formService;
        this.formRestResponseFactory = formRestResponseFactory;
    }

    @GetMapping(value = "/app/rest/form/form-instance/{formInstanceId}", produces = "application/json")
    public FormInstanceResponse getFormInstance(@ApiParam(name = "formInstanceId") @PathVariable String formInstanceId, HttpServletRequest request) {
        FormInstance formInstance = formService.createFormInstanceQuery().id(formInstanceId).singleResult();

        if (formInstance == null) {
            throw new FlowableObjectNotFoundException("Could not find a form instance");
        }

        return formRestResponseFactory.createFormInstanceResponse(formInstance);
    }

    /*@GetMapping("/app/rest/form/form-data/{taskId}")
    public FormData getFormData(@PathVariable String taskId) {
        Task task;
        return formService.getTaskFormData(taskId);
    }*/
}
