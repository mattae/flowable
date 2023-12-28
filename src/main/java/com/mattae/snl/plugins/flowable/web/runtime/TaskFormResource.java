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

import com.mattae.snl.plugins.flowable.form.model.FormIOFormModel;
import com.mattae.snl.plugins.flowable.model.runtime.CompleteFormRepresentation;
import com.mattae.snl.plugins.flowable.model.runtime.FormModelRepresentation;
import com.mattae.snl.plugins.flowable.model.runtime.SaveFormRepresentation;
import com.mattae.snl.plugins.flowable.services.runtime.FlowableTaskFormService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.form.api.FormInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author Joram Barrez
 */
@RestController
@RequestMapping("/api/rest/task-forms")
@Slf4j
public class TaskFormResource {

    protected final FlowableTaskFormService taskFormService;

    public TaskFormResource(FlowableTaskFormService taskFormService) {
        this.taskFormService = taskFormService;
    }

    @GetMapping(value = "/{taskId}", produces = "application/json")
    public FormModelRepresentation getTaskForm(@PathVariable String taskId) {
        FormInfo formInfo = taskFormService.getTaskForm(taskId);
        FormIOFormModel formModel = (FormIOFormModel) formInfo.getFormModel();
        return new FormModelRepresentation(formInfo, formModel);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value = "/{taskId}", produces = "application/json")
    public void completeTaskForm(@PathVariable String taskId, @RequestBody CompleteFormRepresentation completeTaskFormRepresentation) {
        taskFormService.completeTaskForm(taskId, completeTaskFormRepresentation);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value = "/{taskId}/save-form", produces = "application/json")
    public void saveTaskForm(@PathVariable String taskId, @RequestBody SaveFormRepresentation saveFormRepresentation) {
        taskFormService.saveTaskForm(taskId, saveFormRepresentation);
    }
}
