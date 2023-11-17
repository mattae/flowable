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

import com.mattae.snl.plugins.flowable.services.model.ExtendedUserRepresentation;
import org.apache.commons.lang3.StringUtils;
import org.flowable.cmmn.api.CmmnRuntimeService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.identitylink.api.IdentityLink;
import org.flowable.idm.api.Picture;
import org.flowable.idm.api.User;
import org.flowable.idm.api.UserQuery;
import org.flowable.ui.common.model.ResultListDataRepresentation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Rest resource for managing users, specifically related to tasks and processes.
 */
@RestController
@RequestMapping("/api")
public class WorkflowUsersResource {

    private static final int MAX_USER_SIZE = 100;

    private final IdentityService identityService;

    private final RuntimeService runtimeService;

    private final TaskService taskService;
    private final CmmnRuntimeService cmmnRuntimeService;

    public WorkflowUsersResource(IdentityService identityService, RuntimeService runtimeService,
                                 TaskService taskService, CmmnRuntimeService cmmnRuntimeService) {
        this.identityService = identityService;
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.cmmnRuntimeService = cmmnRuntimeService;
    }

    @GetMapping(value = "/rest/workflow-users")
    public ResultListDataRepresentation getUsers(@RequestParam(value = "filter", required = false) String filter,
                                                 @RequestParam(value = "excludeTaskId", required = false) String excludeTaskId,
                                                 @RequestParam(value = "excludeProcessId", required = false) String excludeProcessId,
                                                 @RequestParam(value = "excludeCaseId", required = false) String excludeCaseId) {

        List<? extends User> matchingUsers;
        UserQuery userQuery = identityService.createUserQuery();
        if (StringUtils.isNotEmpty(filter)) {
            userQuery.userFullNameLikeIgnoreCase("%" + filter + "%");
        }

        matchingUsers = userQuery.listPage(0, MAX_USER_SIZE);


        // Filter out users already part of the task/process of which the ID has been passed
        if (excludeTaskId != null) {
            filterUsersInvolvedInTask(excludeTaskId, matchingUsers);
        } else if (excludeProcessId != null) {
            filterUsersInvolvedInProcess(excludeProcessId, matchingUsers);
        } else if (excludeCaseId != null) {
            filterUsersInvolvedInCase(excludeCaseId, matchingUsers);
        }

        List<ExtendedUserRepresentation> userRepresentations = new ArrayList<>(matchingUsers.size());
        for (User user : matchingUsers) {
            Picture picture = identityService.getUserPicture(user.getId());
            if (picture != null && StringUtils.isNotBlank(picture.getMimeType())) {
                byte[] bytes = picture.getBytes();
                String url = String.format("data:%s;base64,%s", picture.getMimeType(), Base64.getEncoder().encodeToString(bytes));
                userRepresentations.add(new ExtendedUserRepresentation(user, url));
            } else {
                userRepresentations.add(new ExtendedUserRepresentation(user, null));
            }
        }

        return new ResultListDataRepresentation(userRepresentations);

    }

    protected void filterUsersInvolvedInProcess(String excludeProcessId, List<? extends User> matchingUsers) {
        Set<String> involvedUsers = getInvolvedUsersAsSet(
            runtimeService.getIdentityLinksForProcessInstance(excludeProcessId));
        removeInvolvedUsers(matchingUsers, involvedUsers);
    }

    protected void filterUsersInvolvedInTask(String excludeTaskId, List<? extends User> matchingUsers) {
        Set<String> involvedUsers = getInvolvedUsersAsSet(taskService.getIdentityLinksForTask(excludeTaskId));
        removeInvolvedUsers(matchingUsers, involvedUsers);
    }

    protected void filterUsersInvolvedInCase(String excludeCaseId, List<? extends User> matchingUsers) {
        Set<String> involvedUsers = getInvolvedUsersAsSet(cmmnRuntimeService.getIdentityLinksForCaseInstance(excludeCaseId));
        removeInvolvedUsers(matchingUsers, involvedUsers);
    }

    protected Set<String> getInvolvedUsersAsSet(List<IdentityLink> involvedPeople) {
        Set<String> involved = null;
        if (involvedPeople.size() > 0) {
            involved = new HashSet<>();
            for (IdentityLink link : involvedPeople) {
                if (link.getUserId() != null) {
                    involved.add(link.getUserId());
                }
            }
        }
        return involved;
    }

    protected void removeInvolvedUsers(List<? extends User> matchingUsers, Set<String> involvedUsers) {
        if (involvedUsers != null) {
            matchingUsers.removeIf(user -> involvedUsers.contains(user.getId()));
        }
    }

}
