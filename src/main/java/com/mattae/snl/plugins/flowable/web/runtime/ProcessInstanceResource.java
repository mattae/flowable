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
import com.mattae.snl.plugins.flowable.form.model.FormIOFormModel;
import com.mattae.snl.plugins.flowable.model.runtime.FormModelRepresentation;
import com.mattae.snl.plugins.flowable.model.runtime.ProcessInstanceRepresentation;
import com.mattae.snl.plugins.flowable.services.model.ExtendedUserRepresentation;
import com.mattae.snl.plugins.flowable.services.runtime.FlowableProcessInstanceService;
import jakarta.servlet.http.HttpServletResponse;
import org.flowable.form.api.FormInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing a process instance.
 */
@RestController
@RequestMapping("/api")
public class ProcessInstanceResource {

    protected final FlowableProcessInstanceService processInstanceService;

    public ProcessInstanceResource(FlowableProcessInstanceService processInstanceService) {
        this.processInstanceService = processInstanceService;
    }

    @GetMapping(value = "/rest/process-instances/{processInstanceId}", produces = "application/json")
    public ProcessInstanceRepresentation getProcessInstance(@PathVariable String processInstanceId, HttpServletResponse response) {
        return processInstanceService.getProcessInstance(processInstanceId, response);
    }

    @GetMapping(value = "/rest/process-instances/{processInstanceId}/start-form", produces = "application/json")
    public FormModelRepresentation getProcessInstanceStartForm(@PathVariable String processInstanceId, HttpServletResponse response) {
        FormInfo formInfo = processInstanceService.getProcessInstanceStartForm(processInstanceId, response);
        FormIOFormModel formModel = (FormIOFormModel) formInfo.getFormModel();
        return new FormModelRepresentation(formInfo, formModel);
    }

    @DeleteMapping(value = "/rest/process-instances/{processInstanceId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteProcessInstance(@PathVariable String processInstanceId) {
        processInstanceService.deleteProcessInstance(processInstanceId);
    }

    @PutMapping(value = "/rest/process-instances/{processInstanceId}/action/involve", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public void involveUser(@PathVariable("processInstanceId") String processInstanceId, @RequestBody ObjectNode requestNode) {
        processInstanceService.involveUser(processInstanceId, requestNode);
    }

    @PutMapping(value = "/rest/process-instances/{processInstanceId}/action/remove-involved", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public void removeInvolvedUser(@PathVariable("processInstanceId") String processInstanceId, @RequestBody ObjectNode requestNode) {
        processInstanceService.removeInvolvedUser(processInstanceId, requestNode);
    }

    @GetMapping(value = "/rest/process-instances/{processInstanceId}/involved-users", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ExtendedUserRepresentation> getInvolvedUsers(@PathVariable("processInstanceId") String processInstanceId) {
        return processInstanceService.getInvolvedUsers(processInstanceId);
    }
}
