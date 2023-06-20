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
package com.mattae.snl.plugins.flowable.services.runtime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattae.snl.plugins.flowable.model.runtime.CompleteFormRepresentation;
import com.mattae.snl.plugins.flowable.model.runtime.SaveFormRepresentation;
import io.github.jbella.snl.core.api.services.errors.RecordNotFoundException;
import org.flowable.cmmn.api.CmmnTaskService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.TaskService;
import org.flowable.form.api.FormInfo;
import org.flowable.form.api.FormRepositoryService;
import org.flowable.form.api.FormService;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.ui.common.security.SecurityScope;
import org.flowable.ui.common.security.SecurityUtils;
import org.flowable.ui.common.service.exception.NotPermittedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author Tijs Rademakers
 */
@Service
@Transactional
public class FlowableTaskFormService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlowableTaskFormService.class);

    protected final TaskService taskService;

    protected final CmmnTaskService cmmnTaskService;

    protected final RepositoryService repositoryService;

    protected final HistoryService historyService;

    protected final FormRepositoryService formRepositoryService;

    protected final FormService formService;

    protected final PermissionService permissionService;

    protected final ObjectMapper objectMapper;

    public FlowableTaskFormService(TaskService taskService,
                                   CmmnTaskService cmmnTaskService,
                                   RepositoryService repositoryService,
                                   HistoryService historyService,
                                   FormRepositoryService formRepositoryService,
                                   FormService formService,
                                   PermissionService permissionService,
                                   ObjectMapper objectMapper) {
        this.taskService = taskService;
        this.cmmnTaskService = cmmnTaskService;
        this.repositoryService = repositoryService;
        this.historyService = historyService;
        this.formRepositoryService = formRepositoryService;
        this.formService = formService;
        this.permissionService = permissionService;
        this.objectMapper = objectMapper;
    }

    public FormInfo getTaskForm(String taskId) {
        HistoricTaskInstance task = permissionService.validateReadPermissionOnTask(SecurityUtils.getAuthenticatedSecurityScope(), taskId);
        if (task.getProcessInstanceId() != null || task.getScopeType() == null) {
            return taskService.getTaskFormModel(task.getId());
        } else {
            return cmmnTaskService.getTaskFormModel(task.getId());
        }
    }

    public void saveTaskForm(String taskId, SaveFormRepresentation saveFormRepresentation) {

        // Get the form definition
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        if (task == null) {
            throw new RecordNotFoundException("Task not found with id: " + taskId);
        }

        checkCurrentUserCanModifyTask(task);

        FormInfo formInfo = formRepositoryService.getFormModelById(saveFormRepresentation.getFormId());
        Map<String, Object> formVariables = saveFormRepresentation.getValues();

        if (task.getProcessInstanceId() != null) {
            formService.saveFormInstanceByFormDefinitionId(formVariables, saveFormRepresentation.getFormId(), taskId,
                            task.getProcessInstanceId(), task.getProcessDefinitionId(), task.getTenantId(), null);

        } else {
            formService.saveFormInstanceWithScopeId(formVariables, saveFormRepresentation.getFormId(), taskId,
                            task.getScopeId(), task.getScopeType(), task.getScopeDefinitionId(), task.getTenantId(), null);
        }

    }

    public void completeTaskForm(String taskId, CompleteFormRepresentation completeTaskFormRepresentation) {

        // Get the form definition
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        if (task == null) {
            throw new RecordNotFoundException("Task not found with id: " + taskId);
        }

        checkCurrentUserCanModifyTask(task);

        if (task.getProcessInstanceId() != null || task.getScopeType() == null) {
            taskService.completeTaskWithForm(taskId, completeTaskFormRepresentation.getFormId(),
                    completeTaskFormRepresentation.getOutcome(), completeTaskFormRepresentation.getValues());
        } else {
            cmmnTaskService.completeTaskWithForm(taskId, completeTaskFormRepresentation.getFormId(), completeTaskFormRepresentation.getOutcome(),
                            completeTaskFormRepresentation.getValues());
        }
    }

    protected void checkCurrentUserCanModifyTask(Task task) {
        SecurityScope currentUser = SecurityUtils.getAuthenticatedSecurityScope();
        if (!permissionService.isTaskOwnerOrAssignee(currentUser, task.getId())) {
            if (!permissionService.validateIfUserIsInitiatorAndCanCompleteTask(currentUser, task)) {
                throw new NotPermittedException();
            }
        }
    }
}
