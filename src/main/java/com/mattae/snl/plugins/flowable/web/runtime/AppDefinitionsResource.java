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

import com.mattae.snl.plugins.flowable.model.runtime.AppDefinitionRepresentation;
import com.mattae.snl.plugins.flowable.services.runtime.FlowableAppDefinitionService;
import org.flowable.ui.common.model.ResultListDataRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing the app definitions.
 */
@RestController
@RequestMapping("/api")
public class AppDefinitionsResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppDefinitionsResource.class);

    protected final FlowableAppDefinitionService appDefinitionService;

    public AppDefinitionsResource(FlowableAppDefinitionService appDefinitionService) {
        this.appDefinitionService = appDefinitionService;
    }

    @GetMapping(value = "/rest/runtime/app-definitions")
    public ResultListDataRepresentation getAppDefinitions() {
        return appDefinitionService.getAppDefinitions();
    }

    @GetMapping(value = "/rest/runtime/app-definitions/{appDefinitionKey}")
    public AppDefinitionRepresentation getAppDefinition(@PathVariable("appDefinitionKey") String appDefinitionKey) {
        return appDefinitionService.getAppDefinition(appDefinitionKey);
    }

}
