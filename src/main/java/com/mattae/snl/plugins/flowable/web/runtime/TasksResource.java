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
package com.mattae.snl.plugins.flowable.web.runtime;

import com.mattae.snl.plugins.flowable.model.runtime.CreateTaskRepresentation;
import com.mattae.snl.plugins.flowable.model.runtime.TaskRepresentation;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.TaskService;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.flowable.ui.common.security.SecurityUtils;
import org.flowable.ui.common.service.exception.BadRequestException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TasksResource {

    protected final TaskService taskService;

    public TasksResource(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(value = "/rest/tasks")
    public TaskRepresentation createNewTask(@RequestBody CreateTaskRepresentation taskRepresentation, HttpServletRequest request) {
        if (StringUtils.isEmpty(taskRepresentation.getName())) {
            throw new BadRequestException("Task name is required");
        }

        TaskEntity task = (TaskEntity) taskService.newTask();
        task.setName(taskRepresentation.getName());
        task.setDescription(taskRepresentation.getDescription());
        task.setParentTaskId(taskRepresentation.getParentTaskId());
        if (StringUtils.isNotEmpty(taskRepresentation.getCategory())) {
            task.setCategory(taskRepresentation.getCategory());
        }
        task.setAssignee(taskRepresentation.getAssignee() != null ? taskRepresentation.getAssignee() : SecurityUtils.getCurrentUserId());
        taskService.saveTask(task);
        return new TaskRepresentation(taskService.createTaskQuery().taskId(task.getId()).singleResult());
    }

}
