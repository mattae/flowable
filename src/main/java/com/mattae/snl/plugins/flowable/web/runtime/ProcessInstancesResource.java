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

import com.mattae.snl.plugins.flowable.model.runtime.CreateProcessInstanceRepresentation;
import com.mattae.snl.plugins.flowable.model.runtime.ProcessInstanceRepresentation;
import com.mattae.snl.plugins.flowable.services.runtime.FlowableProcessInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class ProcessInstancesResource {

    protected final FlowableProcessInstanceService processInstanceService;

    public ProcessInstancesResource(FlowableProcessInstanceService processInstanceService) {
        this.processInstanceService = processInstanceService;
    }

    @PostMapping(value = "/rest/process-instances")
    public ProcessInstanceRepresentation startNewProcessInstance(@RequestBody CreateProcessInstanceRepresentation startRequest) {
        return processInstanceService.startNewProcessInstance(startRequest);
    }
}