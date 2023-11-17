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
package com.mattae.snl.plugins.flowable.services.runtime;

import com.mattae.snl.plugins.flowable.model.runtime.TaskRepresentation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.ExtensionElement;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.UserTask;
import org.flowable.cmmn.api.CmmnRepositoryService;
import org.flowable.cmmn.api.CmmnTaskService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.identitylink.api.IdentityLinkType;
import org.flowable.identitylink.api.history.HistoricIdentityLink;
import org.flowable.task.api.TaskInfo;
import org.flowable.ui.common.security.SecurityScope;
import org.flowable.ui.common.service.idm.cache.UserCache;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

@Slf4j
public abstract class FlowableAbstractTaskService {

    @Autowired
    protected RepositoryService repositoryService;

    @Autowired
    protected CmmnRepositoryService cmmnRepositoryService;

    @Autowired
    protected TaskService taskService;

    @Autowired
    protected CmmnTaskService cmmnTaskService;

    @Autowired
    protected HistoryService historyService;

    @Autowired
    protected UserCache userCache;

    @Autowired
    protected PermissionService permissionService;

    public void fillPermissionInformation(TaskRepresentation taskRepresentation, TaskInfo task, SecurityScope currentUser) {
        verifyProcessInstanceStartUser(taskRepresentation, task);

        List<HistoricIdentityLink> taskIdentityLinks = historyService.getHistoricIdentityLinksForTask(task.getId());
        if (!taskIdentityLinks.isEmpty()) {
            verifyCandidateGroups(taskRepresentation, currentUser, taskIdentityLinks);
            verifyCandidateUsers(taskRepresentation, currentUser, taskIdentityLinks);
        } else if (taskRepresentation.getAssignee() == null) {
            taskRepresentation.setMemberOfCandidateUsers(true);
            taskRepresentation.setMemberOfCandidateGroup(true);
        }
    }

    protected void verifyProcessInstanceStartUser(TaskRepresentation taskRepresentation, TaskInfo task) {
        if (task.getProcessInstanceId() != null) {
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
            if (historicProcessInstance != null && StringUtils.isNotEmpty(historicProcessInstance.getStartUserId())) {
                taskRepresentation.setProcessInstanceStartUserId(historicProcessInstance.getStartUserId());
                BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
                FlowElement flowElement = bpmnModel.getFlowElement(task.getTaskDefinitionKey());
                if (flowElement instanceof UserTask userTask) {
                    List<ExtensionElement> extensionElements = userTask.getExtensionElements().get("initiator-can-complete");
                    if (CollectionUtils.isNotEmpty(extensionElements)) {
                        String value = extensionElements.get(0).getElementText();
                        if (StringUtils.isNotEmpty(value)) {
                            taskRepresentation.setInitiatorCanCompleteTask(Boolean.parseBoolean(value));
                        }
                    }
                }
            }
        }
    }

    protected void verifyCandidateGroups(TaskRepresentation taskRepresentation, SecurityScope currentUser, List<HistoricIdentityLink> taskIdentityLinks) {
        Collection<String> userGroups = currentUser.getGroupIds();
        taskRepresentation.setMemberOfCandidateGroup(userGroupsMatchTaskCandidateGroups(userGroups, taskIdentityLinks));
    }

    protected boolean userGroupsMatchTaskCandidateGroups(Collection<String> userGroups, List<HistoricIdentityLink> taskIdentityLinks) {
        for (String group : userGroups) {
            for (HistoricIdentityLink identityLink : taskIdentityLinks) {
                LOG.info("Group identity: {}, {}", identityLink.getGroupId(), identityLink.getUserId());
                if (identityLink.getGroupId() != null
                    && identityLink.getType().equals(IdentityLinkType.CANDIDATE)
                    && group.equals(identityLink.getGroupId())) {
                    return true;
                }
            }
        }
        return false;
    }

    protected void verifyCandidateUsers(TaskRepresentation taskRepresentation, SecurityScope currentUser, List<HistoricIdentityLink> taskIdentityLinks) {
        taskRepresentation.setMemberOfCandidateUsers(currentUserMatchesTaskCandidateUsers(currentUser, taskIdentityLinks));
    }

    protected boolean currentUserMatchesTaskCandidateUsers(SecurityScope currentUser, List<HistoricIdentityLink> taskIdentityLinks) {
        for (HistoricIdentityLink identityLink : taskIdentityLinks) {
            if (identityLink.getUserId() != null
                && identityLink.getType().equals(IdentityLinkType.CANDIDATE)
                && identityLink.getUserId().equals(currentUser.getUserId())) {
                return true;
            }
        }
        return false;
    }

}
