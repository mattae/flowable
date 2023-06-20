package com.mattae.snl.plugins.flowable.form.cmd;

import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.impl.cmd.NeedsActiveTaskCmd;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.engine.impl.util.ProcessDefinitionUtil;
import org.flowable.engine.impl.util.TaskHelper;
import org.flowable.form.api.FormFieldHandler;
import org.flowable.form.api.FormInfo;
import org.flowable.form.api.FormRepositoryService;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;

import java.util.Map;

public class CompleteTaskWithFormCmd extends NeedsActiveTaskCmd<Void> {
    private static final long serialVersionUID = 1L;
    protected String formDefinitionId;
    protected String outcome;
    protected Map<String, Object> variables;
    protected Map<String, Object> variablesLocal;
    protected Map<String, Object> transientVariables;
    protected Map<String, Object> transientVariablesLocal;

    public CompleteTaskWithFormCmd(String taskId, String formDefinitionId, String outcome, Map<String, Object> variables) {
        super(taskId);
        this.formDefinitionId = formDefinitionId;
        this.outcome = outcome;
        this.variables = variables;
    }

    public CompleteTaskWithFormCmd(String taskId, String formDefinitionId, String outcome, Map<String, Object> variables, boolean localScope) {
        this(taskId, formDefinitionId, outcome, variables);
        if (localScope) {
            this.variablesLocal = variables;
        } else {
            this.variables = variables;
        }

    }

    public CompleteTaskWithFormCmd(String taskId, String formDefinitionId, String outcome, Map<String, Object> variables, Map<String, Object> transientVariables) {
        this(taskId, formDefinitionId, outcome, variables);
        this.transientVariables = transientVariables;
    }

    public CompleteTaskWithFormCmd(String taskId, String formDefinitionId, String outcome, Map<String, Object> variables, Map<String, Object> variablesLocal, Map<String, Object> transientVariables, Map<String, Object> transientVariablesLocal) {
        this(taskId, formDefinitionId, outcome, variables);
        this.variablesLocal = variablesLocal;
        this.transientVariables = transientVariables;
        this.transientVariablesLocal = transientVariablesLocal;
    }

    protected Void execute(CommandContext commandContext, TaskEntity task) {
        if (StringUtils.isNotEmpty(task.getScopeId()) && "cmmn".equals(task.getScopeType())) {
            throw new FlowableException("The task instance is created by the cmmn engine and should be completed via the cmmn engine API");
        } else {
            var formService = CommandContextUtil.getFormService();
            if (formService == null) {
                throw new FlowableIllegalArgumentException("Form engine is not initialized");
            } else {
                FormRepositoryService formRepositoryService = CommandContextUtil.getFormRepositoryService();
                FormInfo formInfo = formRepositoryService.getFormModelById(this.formDefinitionId);
                boolean local = this.variablesLocal != null && !this.variablesLocal.isEmpty();
                Map formVariables;
                if (local) {
                    formVariables = this.variablesLocal;
                } else {
                    formVariables = this.variables;
                }

                Map<String, Object> taskVariables = null;
                if (formInfo != null) {
                    ProcessEngineConfigurationImpl processEngineConfiguration = CommandContextUtil.getProcessEngineConfiguration(commandContext);
                    FormFieldHandler formFieldHandler = processEngineConfiguration.getFormFieldHandler();
                    if (this.isFormFieldValidationEnabled(task, processEngineConfiguration, task.getProcessDefinitionId(), task.getTaskDefinitionKey())) {
                        formService.validateFormFields(formInfo, formVariables);
                    }

                    taskVariables = formService.getVariablesFromFormSubmission(formInfo, formVariables, this.outcome);
                    if (task.getProcessInstanceId() != null || task.getScopeType() == null) {
                        formService.saveFormInstance(formVariables, formInfo, task.getId(), task.getProcessInstanceId(), task.getProcessDefinitionId(), task.getTenantId(), this.outcome);
                    } else {
                        formService.saveFormInstanceWithScopeId(formVariables, formInfo, task.getId(), task.getScopeId(), task.getScopeType(), task.getScopeDefinitionId(), task.getTenantId(), this.outcome);
                    }

                    formFieldHandler.handleFormFieldsOnSubmit(formInfo, task.getId(), task.getProcessInstanceId(), (String) null, (String) null, taskVariables, task.getTenantId());
                }

                if (local) {
                    TaskHelper.completeTask(task, this.variables, taskVariables, this.transientVariables, this.transientVariablesLocal, commandContext);
                } else {
                    TaskHelper.completeTask(task, taskVariables, this.variablesLocal, this.transientVariables, this.transientVariablesLocal, commandContext);
                }

                return null;
            }
        }
    }

    protected boolean isFormFieldValidationEnabled(TaskEntity task, ProcessEngineConfigurationImpl processEngineConfiguration, String processDefinitionId, String taskDefinitionKey) {
        if (processEngineConfiguration.isFormFieldValidationEnabled()) {
            UserTask userTask = (UserTask) ProcessDefinitionUtil.getBpmnModel(processDefinitionId).getFlowElement(taskDefinitionKey);
            String formFieldValidationExpression = userTask.getValidateFormFields();
            return TaskHelper.isFormFieldValidationEnabled(task, processEngineConfiguration, formFieldValidationExpression);
        } else {
            return false;
        }
    }

    protected String getSuspendedTaskException() {
        return "Cannot complete a suspended task";
    }
}
