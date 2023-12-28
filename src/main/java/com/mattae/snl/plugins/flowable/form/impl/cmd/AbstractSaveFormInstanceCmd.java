/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mattae.snl.plugins.flowable.form.impl.cmd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mattae.snl.plugins.flowable.form.model.FormIOFormModel;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.form.api.FormInfo;
import org.flowable.form.api.FormInstance;
import org.flowable.form.engine.FormEngineConfiguration;
import org.flowable.form.engine.impl.persistence.entity.FormInstanceEntity;
import org.flowable.form.engine.impl.persistence.entity.FormInstanceEntityManager;
import org.flowable.form.engine.impl.util.CommandContextUtil;
import org.flowable.form.model.FormField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Slf4j
public abstract class AbstractSaveFormInstanceCmd extends org.flowable.form.engine.impl.cmd.AbstractSaveFormInstanceCmd {
    public AbstractSaveFormInstanceCmd(FormInfo formInfo, Map<String, Object> variables, String taskId, String processInstanceId, String processDefinitionId, String tenantId, String outcome) {
        super(formInfo, variables, taskId, processInstanceId, processDefinitionId, tenantId, outcome);
    }

    public AbstractSaveFormInstanceCmd(String formModelId, Map<String, Object> variables, String taskId, String processInstanceId, String processDefinitionId, String tenantId, String outcome) {
        super(formModelId, variables, taskId, processInstanceId, processDefinitionId, tenantId, outcome);
    }

    public AbstractSaveFormInstanceCmd(String formModelId, Map<String, Object> variables, String taskId, String scopeId, String scopeType, String scopeDefinitionId, String tenantId, String outcome) {
        super(formModelId, variables, taskId, scopeId, scopeType, scopeDefinitionId, tenantId, outcome);
    }

    public AbstractSaveFormInstanceCmd(FormInfo formInfo, Map<String, Object> variables, String taskId, String scopeId, String scopeType, String scopeDefinitionId, String tenantId, String outcome) {
        super(formInfo, variables, taskId, scopeId, scopeType, scopeDefinitionId, tenantId, outcome);
    }


    @Override
    public FormInstance execute(CommandContext commandContext) {
        FormEngineConfiguration formEngineConfiguration = CommandContextUtil.getFormEngineConfiguration();
        if (formInfo == null) {
            if (formModelId == null) {
                throw new FlowableException("Invalid form model and no form model Id provided");
            }
            formInfo = CommandContextUtil.getFormEngineConfiguration().getFormRepositoryService().getFormModelById(formModelId);
        }

        if (formInfo == null || formInfo.getId() == null) {
            throw new FlowableException("Invalid form model provided");
        }

        ObjectMapper objectMapper = formEngineConfiguration.getObjectMapper();
        ObjectNode submittedFormValuesJson = objectMapper.createObjectNode();

        ObjectNode valuesNode = submittedFormValuesJson.putObject("values");

        // Loop over all form fields and see if a value was provided

        FormIOFormModel formModel = (FormIOFormModel) formInfo.getFormModel();
        Map<String, FormField> fieldMap = formModel.allFieldsAsMap();
        for (String fieldId : fieldMap.keySet()) {
            if (variables != null && variables.containsKey(fieldId)) {
                Object variableValue = variables.get(fieldId);
                if (variableValue == null) {
                    valuesNode.putNull(fieldId);
                } else if (variableValue instanceof Long) {
                    valuesNode.put(fieldId, (Long) variables.get(fieldId));

                } else if (variableValue instanceof Double) {
                    valuesNode.put(fieldId, (Double) variables.get(fieldId));

                } else if (variableValue instanceof Boolean) {
                    valuesNode.put(fieldId, (Boolean) variables.get(fieldId));

                } else if (variableValue instanceof LocalDate) {
                    valuesNode.put(fieldId, ((LocalDate) variableValue).format(DateTimeFormatter.ISO_DATE));

                } else {
                    valuesNode.putPOJO(fieldId, variableValue);
                }
            }
        }
        if (outcome != null) {
            submittedFormValuesJson.put("flowable_form_outcome", outcome);
        }

        FormInstanceEntityManager formInstanceEntityManager = CommandContextUtil.getFormInstanceEntityManager(commandContext);
        FormInstanceEntity formInstanceEntity = findExistingFormInstance(formEngineConfiguration);

        if (formInstanceEntity == null) {
            formInstanceEntity = formInstanceEntityManager.create();
        }

        formInstanceEntity.setFormDefinitionId(formInfo.getId());
        formInstanceEntity.setTaskId(taskId);

        if (processInstanceId != null) {
            formInstanceEntity.setProcessInstanceId(processInstanceId);
            formInstanceEntity.setProcessDefinitionId(processDefinitionId);

        } else {
            formInstanceEntity.setScopeId(scopeId);
            formInstanceEntity.setScopeType(scopeType);
            formInstanceEntity.setScopeDefinitionId(scopeDefinitionId);
        }

        formInstanceEntity.setSubmittedDate(formEngineConfiguration.getClock().getCurrentTime());
        formInstanceEntity.setSubmittedBy(Authentication.getAuthenticatedUserId());

        if (tenantId != null) {
            formInstanceEntity.setTenantId(tenantId);
        }

        try {
            formInstanceEntity.setFormValueBytes(objectMapper.writeValueAsBytes(submittedFormValuesJson));
        } catch (Exception e) {
            throw new FlowableException("Error setting form values JSON", e);
        }

        if (formInstanceEntity.getId() == null) {
            formInstanceEntityManager.insert(formInstanceEntity);
        } else {
            formInstanceEntityManager.update(formInstanceEntity);
        }
        return formInstanceEntity;
    }
}
