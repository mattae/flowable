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


import java.util.Map;

/**
 * @author Tijs Rademakers
 */
public class GetFormInstanceModelCmd extends AbstractGetFormInstanceModelCmd {


    public GetFormInstanceModelCmd(String formInstanceId, Map<String, Object> variables) {
        initializeValues(null, null, null, null, null, null, variables, false);
        this.formInstanceId = formInstanceId;
    }

    public GetFormInstanceModelCmd(String formDefinitionKey, String formDefinitionId, String taskId,
                                   String processInstanceId, Map<String, Object> variables) {

        initializeValues(formDefinitionKey, null, formDefinitionId, null, taskId, processInstanceId, variables, false);
    }

    public GetFormInstanceModelCmd(String formDefinitionKey, String parentDeploymentId, String formDefinitionId, String taskId,
                                   String processInstanceId, Map<String, Object> variables) {

        initializeValues(formDefinitionKey, parentDeploymentId, formDefinitionId, null, taskId, processInstanceId, variables, false);
    }

    public GetFormInstanceModelCmd(String formDefinitionKey, String parentDeploymentId, String formDefinitionId, String taskId,
                                   String processInstanceId, String tenantId, Map<String, Object> variables, boolean fallbackToDefaultTenant) {

        initializeValues(formDefinitionKey, parentDeploymentId, formDefinitionId, tenantId, taskId, processInstanceId, variables, fallbackToDefaultTenant);
    }
}
