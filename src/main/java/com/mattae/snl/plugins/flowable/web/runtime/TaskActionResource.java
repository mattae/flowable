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

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mattae.snl.plugins.flowable.model.runtime.TaskRepresentation;
import com.mattae.snl.plugins.flowable.services.runtime.FlowableTaskActionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TaskActionResource {

    protected final FlowableTaskActionService taskActionService;

    public TaskActionResource(FlowableTaskActionService taskActionService) {
        this.taskActionService = taskActionService;
    }

    @PutMapping(value = "/rest/tasks/{taskId}/action/complete")
    @ResponseStatus(value = HttpStatus.OK)
    public void completeTask(@PathVariable String taskId) {
        taskActionService.completeTask(taskId);
    }

    @PutMapping(value = "/rest/tasks/{taskId}/action/assign")
    public TaskRepresentation assignTask(@PathVariable String taskId, @RequestBody ObjectNode requestNode) {
        return taskActionService.assignTask(taskId, requestNode);
    }

    @PutMapping(value = "/rest/tasks/{taskId}/action/involve", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public void involveUser(@PathVariable("taskId") String taskId, @RequestBody ObjectNode requestNode) {
        taskActionService.involveUser(taskId, requestNode);
    }

    @PutMapping(value = "/rest/tasks/{taskId}/action/remove-involved", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public void removeInvolvedUser(@PathVariable("taskId") String taskId, @RequestBody ObjectNode requestNode) {
        taskActionService.removeInvolvedUser(taskId, requestNode);
    }

    @PutMapping(value = "/rest/tasks/{taskId}/action/claim")
    @ResponseStatus(value = HttpStatus.OK)
    public void claimTask(@PathVariable String taskId) {
        taskActionService.claimTask(taskId);
    }

    @PutMapping(value = "/rest/tasks/{taskId}/action/un-claim")
    @ResponseStatus(value = HttpStatus.OK)
    public void unClaimTask(@PathVariable String taskId) {
        taskActionService.unClaimTask(taskId);
    }

}
