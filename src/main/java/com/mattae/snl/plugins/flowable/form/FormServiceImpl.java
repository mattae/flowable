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
package com.mattae.snl.plugins.flowable.form;

import com.mattae.snl.plugins.flowable.form.impl.cmd.*;
import org.flowable.form.api.FormInfo;
import org.flowable.form.api.FormInstance;
import org.flowable.form.api.FormInstanceInfo;

import java.util.Map;

/**
 * @author Matthew Edor
 */

public class FormServiceImpl extends org.flowable.form.engine.impl.FormServiceImpl {

    public FormServiceImpl(FormEngineConfiguration engineConfiguration) {
        super(engineConfiguration);
    }


    @Override
    public void validateFormFields(FormInfo formInfo, Map<String, Object> values) {
        this.commandExecutor.execute(new ValidateFormFieldsCmd(formInfo, values));
    }

    @Override
    public FormInstance createFormInstance(Map<String, Object> variables, FormInfo formInfo, String taskId, String processInstanceId,
                                           String processDefinitionId, String tenantId, String outcome) {

        return commandExecutor.execute(new CreateFormInstanceCmd(formInfo, variables, taskId, processInstanceId, processDefinitionId, tenantId, outcome));
    }

    @Override
    public FormInstance saveFormInstance(Map<String, Object> variables, FormInfo formInfo, String taskId, String processInstanceId,
                                         String processDefinitionId, String tenantId, String outcome) {

        return commandExecutor.execute(new SaveFormInstanceCmd(formInfo, variables, taskId, processInstanceId, processDefinitionId, tenantId, outcome));
    }

    @Override
    public FormInstance saveFormInstanceByFormDefinitionId(Map<String, Object> variables, String formDefinitionId,
                                                           String taskId, String processInstanceId, String processDefinitionId, String tenantId, String outcome) {

        return commandExecutor.execute(new SaveFormInstanceCmd(formDefinitionId, variables, taskId, processInstanceId, processDefinitionId, tenantId, outcome));
    }

    @Override
    public FormInstance createFormInstanceWithScopeId(Map<String, Object> variables, FormInfo formInfo, String taskId,
                                                      String scopeId, String scopeType, String scopeDefinitionId, String tenantId, String outcome) {

        return commandExecutor.execute(new CreateFormInstanceCmd(formInfo, variables, taskId, scopeId, scopeType, scopeDefinitionId, tenantId, outcome));
    }

    @Override
    public FormInstance saveFormInstanceWithScopeId(Map<String, Object> variables, FormInfo formInfo, String taskId,
                                                    String scopeId, String scopeType, String scopeDefinitionId, String tenantId, String outcome) {

        return commandExecutor.execute(new SaveFormInstanceCmd(formInfo, variables, taskId, scopeId, scopeType, scopeDefinitionId, tenantId, outcome));
    }

    @Override
    public FormInstance saveFormInstanceWithScopeId(Map<String, Object> variables, String formModelId, String taskId,
                                                    String scopeId, String scopeType, String scopeDefinitionId, String tenantId, String outcome) {

        return commandExecutor.execute(new SaveFormInstanceCmd(formModelId, variables, taskId, scopeId, scopeType, scopeDefinitionId, tenantId, outcome));
    }

    @Override
    public FormInstanceInfo getFormInstanceModelByKeyAndScopeId(String formDefinitionKey, String scopeId, String scopeType, Map<String, Object> variables) {
        return commandExecutor.execute(new GetFormInstanceByScopeModelCmd(formDefinitionKey, scopeId, scopeType, null, variables));
    }

    @Override
    public FormInstanceInfo getFormInstanceModelByKeyAndScopeId(String formDefinitionKey, String scopeId, String scopeType,
                                                                Map<String, Object> variables, String tenantId, boolean fallbackToDefaultTenant) {

        return commandExecutor.execute(new GetFormInstanceByScopeModelCmd(formDefinitionKey, null, scopeId, scopeType,
            tenantId, variables, fallbackToDefaultTenant));
    }

    @Override
    public FormInstanceInfo getFormInstanceModelByKeyAndParentDeploymentIdAndScopeId(String formDefinitionKey, String parentDeploymentId,
                                                                                     String scopeId, String scopeType, Map<String, Object> variables) {

        return commandExecutor.execute(new GetFormInstanceByScopeModelCmd(formDefinitionKey, parentDeploymentId,
            scopeId, scopeType, null, variables, false));
    }

    @Override
    public FormInstanceInfo getFormInstanceModelByKeyAndParentDeploymentIdAndScopeId(String formDefinitionKey, String parentDeploymentId,
                                                                                     String scopeId, String scopeType, Map<String, Object> variables, String tenantId, boolean fallbackToDefaultTenant) {

        return commandExecutor.execute(new GetFormInstanceByScopeModelCmd(formDefinitionKey, parentDeploymentId,
            scopeId, scopeType, tenantId, variables, fallbackToDefaultTenant));
    }

    @Override
    public FormInstanceInfo getFormInstanceModelById(String formInstanceId, Map<String, Object> variables) {

        return commandExecutor.execute(new GetFormInstanceModelCmd(formInstanceId, variables));
    }

    @Override
    public FormInstanceInfo getFormInstanceModelById(String formDefinitionId, String taskId, String processInstanceId, Map<String, Object> variables) {
        return commandExecutor.execute(new GetFormInstanceModelCmd(null, formDefinitionId, taskId, processInstanceId, variables));
    }

    @Override
    public FormInstanceInfo getFormInstanceModelById(String formDefinitionId, String taskId, String processInstanceId,
                                                     Map<String, Object> variables, String tenantId, boolean fallbackToDefaultTenant) {

        return commandExecutor.execute(new GetFormInstanceModelCmd(null, null, formDefinitionId, taskId, processInstanceId,
            tenantId, variables, fallbackToDefaultTenant));
    }

    @Override
    public FormInstanceInfo getFormInstanceModelByKey(String formDefinitionKey, String taskId, String processInstanceId, Map<String, Object> variables) {
        return commandExecutor.execute(new GetFormInstanceModelCmd(formDefinitionKey, null, taskId, processInstanceId, variables));
    }

    @Override
    public FormInstanceInfo getFormInstanceModelByKey(String formDefinitionKey, String taskId, String processInstanceId,
                                                      Map<String, Object> variables, String tenantId, boolean fallbackToDefaultTenant) {

        return commandExecutor.execute(new GetFormInstanceModelCmd(formDefinitionKey, null, null, taskId, processInstanceId, tenantId, variables, fallbackToDefaultTenant));
    }

    @Override
    public FormInstanceInfo getFormInstanceModelByKeyAndParentDeploymentId(String formDefinitionKey, String parentDeploymentId,
                                                                           String taskId, String processInstanceId, Map<String, Object> variables) {

        return commandExecutor.execute(new GetFormInstanceModelCmd(formDefinitionKey, parentDeploymentId, null, taskId, processInstanceId, variables));
    }

    @Override
    public FormInstanceInfo getFormInstanceModelByKeyAndParentDeploymentId(String formDefinitionKey, String parentDeploymentId,
                                                                           String taskId, String processInstanceId, Map<String, Object> variables, String tenantId, boolean fallbackToDefaultTenant) {

        return commandExecutor.execute(new GetFormInstanceModelCmd(formDefinitionKey, parentDeploymentId, null,
            taskId, processInstanceId, tenantId, variables, fallbackToDefaultTenant));
    }

    @Override
    public FormInfo getFormModelWithVariablesById(String formDefinitionId, String taskId, Map<String, Object> variables) {
        return commandExecutor.execute(new GetFormModelWithVariablesCmd(null, formDefinitionId, taskId, variables));
    }

    @Override
    public FormInfo getFormModelWithVariablesById(String formDefinitionId, String taskId, Map<String, Object> variables,
                                                  String tenantId, boolean fallbackToDefaultTenant) {

        return commandExecutor.execute(new GetFormModelWithVariablesCmd(null, null, formDefinitionId, taskId,
            tenantId, variables, fallbackToDefaultTenant));
    }

    @Override
    public FormInfo getFormModelWithVariablesByKey(String formDefinitionKey, String taskId, Map<String, Object> variables) {
        return commandExecutor.execute(new GetFormModelWithVariablesCmd(formDefinitionKey, null, taskId, variables));
    }

    @Override
    public FormInfo getFormModelWithVariablesByKey(String formDefinitionKey, String taskId, Map<String, Object> variables,
                                                   String tenantId, boolean fallbackToDefaultTenant) {

        return commandExecutor.execute(new GetFormModelWithVariablesCmd(formDefinitionKey, null, null, taskId,
            tenantId, variables, fallbackToDefaultTenant));
    }

    @Override
    public FormInfo getFormModelWithVariablesByKeyAndParentDeploymentId(String formDefinitionKey, String parentDeploymentId,
                                                                        String taskId, Map<String, Object> variables) {

        return commandExecutor.execute(new GetFormModelWithVariablesCmd(formDefinitionKey, parentDeploymentId, null, taskId, variables));
    }

    @Override
    public FormInfo getFormModelWithVariablesByKeyAndParentDeploymentId(String formDefinitionKey, String parentDeploymentId, String taskId,
                                                                        Map<String, Object> variables, String tenantId, boolean fallbackToDefaultTenant) {

        return commandExecutor.execute(new GetFormModelWithVariablesCmd(formDefinitionKey, parentDeploymentId, null, taskId,
            tenantId, variables, fallbackToDefaultTenant));
    }

    public Map<String, Object> getVariablesFromFormSubmission(FormInfo formInfo, Map<String, Object> values) {
        return commandExecutor.execute(new GetVariablesFromFormSubmissionCmd(formInfo, values));
    }

    @Override
    public Map<String, Object> getVariablesFromFormSubmission(FormInfo formInfo, Map<String, Object> values, String outcome) {
        return commandExecutor.execute(new GetVariablesFromFormSubmissionCmd(formInfo, values, outcome));
    }
}
