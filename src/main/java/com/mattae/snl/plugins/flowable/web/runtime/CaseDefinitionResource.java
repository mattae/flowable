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

import com.mattae.snl.plugins.flowable.model.runtime.FormModelRepresentation;
import com.mattae.snl.plugins.flowable.services.runtime.FlowableCaseDefinitionService;
import org.flowable.form.api.FormInfo;
import org.flowable.form.model.SimpleFormModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class CaseDefinitionResource {

    protected final FlowableCaseDefinitionService caseDefinitionService;

    public CaseDefinitionResource(FlowableCaseDefinitionService caseDefinitionService) {
        this.caseDefinitionService = caseDefinitionService;
    }

    @GetMapping(value = "/rest/case-definitions/{caseDefinitionId}/start-form", produces = "application/json")
    public FormModelRepresentation getCaseDefinitionStartForm(@PathVariable String caseDefinitionId) {
        FormInfo formInfo = caseDefinitionService.getCaseDefinitionStartForm(caseDefinitionId);
        SimpleFormModel formModel = (SimpleFormModel) formInfo.getFormModel();
        return new FormModelRepresentation(formInfo, formModel);
    }

}
