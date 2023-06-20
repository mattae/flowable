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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mattae.snl.plugins.flowable.model.runtime.TaskRepresentation;
import com.mattae.snl.plugins.flowable.services.runtime.PermissionService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.HistoryService;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.flowable.ui.common.model.ResultListDataRepresentation;
import org.flowable.ui.common.model.UserRepresentation;
import org.flowable.ui.common.security.SecurityScope;
import org.flowable.ui.common.security.SecurityUtils;
import org.flowable.ui.common.service.exception.BadRequestException;
import org.flowable.ui.common.service.exception.NotPermittedException;
import org.flowable.ui.common.service.idm.cache.UserCache;
import org.flowable.ui.common.service.idm.cache.UserCache.CachedUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/app")
public class HistoricTaskQueryResource {

    protected final HistoryService historyService;

    protected final UserCache userCache;

    protected final PermissionService permissionService;

    public HistoricTaskQueryResource(HistoryService historyService, UserCache userCache, PermissionService permissionService) {
        this.historyService = historyService;
        this.userCache = userCache;
        this.permissionService = permissionService;
    }

    @PostMapping(value = "/rest/query/history/tasks", produces = "application/json")
    public ResultListDataRepresentation listTasks(@RequestBody ObjectNode requestNode) {
        if (requestNode == null) {
            throw new BadRequestException("No request found");
        }

        HistoricTaskInstanceQuery taskQuery = historyService.createHistoricTaskInstanceQuery();

        SecurityScope currentUser = SecurityUtils.getAuthenticatedSecurityScope();

        JsonNode processInstanceIdNode = requestNode.get("processInstanceId");
        if (processInstanceIdNode != null && !processInstanceIdNode.isNull()) {
            String processInstanceId = processInstanceIdNode.asText();
            if (permissionService.hasReadPermissionOnProcessInstance(currentUser, processInstanceId)) {
                taskQuery.processInstanceId(processInstanceId);
            } else {
                throw new NotPermittedException();
            }
        }

        JsonNode finishedNode = requestNode.get("finished");
        if (finishedNode != null && !finishedNode.isNull()) {
            boolean isFinished = finishedNode.asBoolean();
            if (isFinished) {
                taskQuery.finished();
            } else {
                taskQuery.unfinished();
            }
        }

        List<HistoricTaskInstance> tasks = taskQuery.list();

        // get all users to have the user object available in the task on the client side
        ResultListDataRepresentation result = new ResultListDataRepresentation(convertTaskInfoList(tasks));
        return result;
    }

    protected List<TaskRepresentation> convertTaskInfoList(List<HistoricTaskInstance> tasks) {
        List<TaskRepresentation> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(tasks)) {
            TaskRepresentation representation = null;
            for (HistoricTaskInstance task : tasks) {
                representation = new TaskRepresentation(task);

                if (StringUtils.isNotBlank(task.getAssignee())) {
                    CachedUser cachedUser = userCache.getUser(task.getAssignee());
                    if (cachedUser != null && cachedUser.getUser() != null) {
                        representation.setAssignee(new UserRepresentation(cachedUser.getUser()));
                    } else {
                        representation.setAssignee(new UserRepresentation(task.getAssignee()));
                    }
                }

                result.add(representation);
            }
        }
        return result;
    }
}
