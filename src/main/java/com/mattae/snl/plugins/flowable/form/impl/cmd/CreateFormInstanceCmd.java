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

import org.flowable.form.api.FormInfo;
import org.flowable.form.engine.FormEngineConfiguration;
import org.flowable.form.engine.impl.persistence.entity.FormInstanceEntity;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Tijs Rademakers
 */
public class CreateFormInstanceCmd extends AbstractSaveFormInstanceCmd implements Serializable {

    private static final long serialVersionUID = 1L;

    public CreateFormInstanceCmd(FormInfo formInfo, Map<String, Object> variables, String taskId, String processInstanceId,
                                 String processDefinitionId, String tenantId, String outcome) {

        super(formInfo, variables, taskId, processInstanceId, processDefinitionId, tenantId, outcome);
    }

    public CreateFormInstanceCmd(String formModelId, Map<String, Object> variables, String taskId, String processInstanceId,
                                 String processDefinitionId, String tenantId, String outcome) {

        super(formModelId, variables, taskId, processInstanceId, processDefinitionId, tenantId, outcome);
    }

    public CreateFormInstanceCmd(FormInfo formInfo, Map<String, Object> variables, String taskId, String scopeId,
                                 String scopeType, String scopeDefinitionId, String tenantId, String outcome) {

        super(formInfo, variables, taskId, scopeId, scopeType, scopeDefinitionId, tenantId, outcome);
    }

    public CreateFormInstanceCmd(String formModelId, Map<String, Object> variables, String taskId, String scopeId,
                                 String scopeType, String scopeDefinitionId, String tenantId, String outcome) {

        super(formModelId, variables, taskId, scopeId, scopeType, scopeDefinitionId, tenantId, outcome);
    }

    @Override
    protected FormInstanceEntity findExistingFormInstance(FormEngineConfiguration formEngineConfiguration) {
        // We always want to create a formInstance.
        return null;
    }

}
