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

import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.IdentityService;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.GroupQuery;
import org.flowable.ui.common.model.GroupRepresentation;
import org.flowable.ui.common.model.ResultListDataRepresentation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WorkflowGroupsResource {

    private final IdentityService identityService;

    public WorkflowGroupsResource(IdentityService identityService) {
        this.identityService = identityService;
    }

    @GetMapping(value = "/rest/workflow-groups")
    public ResultListDataRepresentation getGroups(@RequestParam(value = "filter", required = false) String filter) {
        List<? extends Group> matchingGroups;

        GroupQuery groupQuery = identityService.createGroupQuery();
        if (StringUtils.isNotEmpty(filter)) {
            groupQuery.groupNameLikeIgnoreCase("%" + filter + "%");
        }
        matchingGroups = groupQuery.orderByGroupName().asc().list();
        List<GroupRepresentation> groupRepresentations = new ArrayList<>();
        for (Group group : matchingGroups) {
            groupRepresentations.add(new GroupRepresentation(group));
        }
        return new ResultListDataRepresentation(groupRepresentations);
    }

}
