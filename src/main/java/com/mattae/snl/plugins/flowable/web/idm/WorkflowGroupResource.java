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
package com.mattae.snl.plugins.flowable.web.idm;

import io.github.jbella.snl.core.api.services.errors.RecordNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.flowable.engine.IdentityService;
import org.flowable.idm.api.Group;
import org.flowable.ui.common.model.GroupRepresentation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WorkflowGroupResource {

    private final IdentityService identityService;

    public WorkflowGroupResource(IdentityService identityService) {
        this.identityService = identityService;
    }


    @GetMapping(value = "/rest/workflow-groups/{groupId}")
    public GroupRepresentation getGroup(@PathVariable String groupId, HttpServletResponse response) {
        Group group = identityService.createGroupQuery().groupId(groupId).singleResult();

        if (group == null) {
            throw new RecordNotFoundException("Group with id: " + groupId + " does not exist or is inactive");
        }

        return new GroupRepresentation(group);
    }

}
