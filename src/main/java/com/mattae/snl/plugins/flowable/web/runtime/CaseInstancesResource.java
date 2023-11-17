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

import com.mattae.snl.plugins.flowable.model.runtime.CaseInstanceRepresentation;
import com.mattae.snl.plugins.flowable.model.runtime.CreateCaseInstanceRepresentation;
import com.mattae.snl.plugins.flowable.services.runtime.FlowableCaseInstanceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CaseInstancesResource {

    protected final FlowableCaseInstanceService caseInstanceService;

    public CaseInstancesResource(FlowableCaseInstanceService caseInstanceService) {
        this.caseInstanceService = caseInstanceService;
    }

    @PostMapping(value = "/rest/case-instances")
    public CaseInstanceRepresentation startNewCaseInstance(@RequestBody CreateCaseInstanceRepresentation startRequest) {
        return caseInstanceService.startNewCaseInstance(startRequest);
    }
}
