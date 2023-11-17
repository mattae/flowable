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

import com.mattae.snl.plugins.flowable.model.runtime.TaskRepresentation;
import com.mattae.snl.plugins.flowable.model.runtime.TaskUpdateRepresentation;
import com.mattae.snl.plugins.flowable.services.model.ExtendedUserRepresentation;
import com.mattae.snl.plugins.flowable.services.runtime.FlowableTaskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskResource {

    protected final FlowableTaskService taskService;

    public TaskResource(FlowableTaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(value = "/rest/tasks/{taskId}", produces = "application/json")
    public TaskRepresentation getTask(@PathVariable String taskId) {
        return taskService.getTask(taskId);
    }

    @PutMapping(value = "/rest/tasks/{taskId}", produces = "application/json")
    public TaskRepresentation updateTask(@PathVariable("taskId") String taskId, @RequestBody TaskUpdateRepresentation updated) {
        return taskService.updateTask(taskId, updated);
    }

    @GetMapping(value = "/rest/tasks/{taskId}/subtasks", produces = "application/json")
    public List<TaskRepresentation> getSubTasks(@PathVariable String taskId) {
        return taskService.getSubTasks(taskId);
    }

    @GetMapping(value = "/rest/tasks/{taskId}/involved-users", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ExtendedUserRepresentation> getInvolvedUsers(@PathVariable("taskId") String taskId) {
        return taskService.getInvolvedUsers(taskId);
    }
}
