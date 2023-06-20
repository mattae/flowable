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

import com.mattae.snl.plugins.flowable.model.runtime.CompleteFormRepresentation;
import com.mattae.snl.plugins.flowable.model.runtime.FormModelRepresentation;
import com.mattae.snl.plugins.flowable.model.runtime.SaveFormRepresentation;
import com.mattae.snl.plugins.flowable.services.runtime.FlowableTaskFormService;
import org.flowable.form.api.FormInfo;
import org.flowable.form.model.SimpleFormModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Joram Barrez
 */
@RestController
@RequestMapping("/app/rest/task-forms")
public class TaskFormResource {

    protected final FlowableTaskFormService taskFormService;

    public TaskFormResource(FlowableTaskFormService taskFormService) {
        this.taskFormService = taskFormService;
    }

    @GetMapping(value = "/{taskId}", produces = "application/json")
    public FormModelRepresentation getTaskForm(@PathVariable String taskId) {
        FormInfo formInfo = taskFormService.getTaskForm(taskId);
        SimpleFormModel formModel = (SimpleFormModel) formInfo.getFormModel();
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
