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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.mattae.snl.plugins.flowable.model.runtime.TaskRepresentation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.app.api.AppRepositoryService;
import org.flowable.app.api.repository.AppDefinition;
import org.flowable.cmmn.api.CmmnHistoryService;
import org.flowable.cmmn.api.CmmnRepositoryService;
import org.flowable.cmmn.api.CmmnRuntimeService;
import org.flowable.cmmn.api.history.HistoricCaseInstance;
import org.flowable.cmmn.api.repository.CaseDefinition;
import org.flowable.cmmn.api.repository.CmmnDeployment;
import org.flowable.cmmn.api.runtime.CaseInstance;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.idm.api.User;
import org.flowable.task.api.TaskInfo;
import org.flowable.task.api.TaskInfoQueryWrapper;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.flowable.ui.common.model.ResultListDataRepresentation;
import org.flowable.ui.common.model.UserRepresentation;
import org.flowable.ui.common.security.SecurityScope;
import org.flowable.ui.common.security.SecurityUtils;
import org.flowable.ui.common.service.exception.BadRequestException;
import org.flowable.ui.common.service.idm.cache.UserCache;
import org.flowable.ui.common.service.idm.cache.UserCache.CachedUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Tijs Rademakers
 */
@Service
@Transactional
public class FlowableTaskQueryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlowableTaskQueryService.class);

    private static final String SORT_CREATED_ASC = "created-asc";
    private static final String SORT_CREATED_DESC = "created-desc";
    private static final String SORT_DUE_ASC = "due-asc";
    private static final String SORT_DUE_DESC = "due-desc";

    private static final int DEFAULT_PAGE_SIZE = 25;

    protected final RepositoryService repositoryService;

    protected final AppRepositoryService appRepositoryService;

    protected final CmmnRepositoryService cmmnRepositoryService;

    protected final TaskService taskService;

    protected final RuntimeService runtimeService;

    protected final CmmnRuntimeService cmmnRuntimeService;

    protected final HistoryService historyService;

    protected final CmmnHistoryService cmmnHistoryService;

    protected final UserCache userCache;

    protected ISO8601DateFormat iso8601DateFormat = new ISO8601DateFormat();

    public FlowableTaskQueryService(RepositoryService repositoryService, AppRepositoryService appRepositoryService, CmmnRepositoryService cmmnRepositoryService, TaskService taskService, RuntimeService runtimeService, CmmnRuntimeService cmmnRuntimeService, HistoryService historyService, CmmnHistoryService cmmnHistoryService, UserCache userCache) {
        this.repositoryService = repositoryService;
        this.appRepositoryService = appRepositoryService;
        this.cmmnRepositoryService = cmmnRepositoryService;
        this.taskService = taskService;
        this.runtimeService = runtimeService;
        this.cmmnRuntimeService = cmmnRuntimeService;
        this.historyService = historyService;
        this.cmmnHistoryService = cmmnHistoryService;
        this.userCache = userCache;
    }

    public ResultListDataRepresentation listTasks(ObjectNode requestNode) {

        if (requestNode == null) {
            throw new BadRequestException("No request found");
        }
        SecurityScope currentUser = SecurityUtils.getAuthenticatedSecurityScope();

        JsonNode stateNode = requestNode.get("state");
        TaskInfoQueryWrapper taskInfoQueryWrapper = null;
        if (stateNode != null && "completed".equals(stateNode.asText())) {
            HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
            historicTaskInstanceQuery.finished();
            taskInfoQueryWrapper = new TaskInfoQueryWrapper(historicTaskInstanceQuery);
        } else if (stateNode != null && "active".equals(stateNode.asText())) {
            taskInfoQueryWrapper = new TaskInfoQueryWrapper(taskService.createTaskQuery().active());
        } else {
            taskInfoQueryWrapper = new TaskInfoQueryWrapper(taskService.createTaskQuery());
        }

        JsonNode appDefinitionKeyNode = requestNode.get("appDefinitionKey");
        if (appDefinitionKeyNode != null && !appDefinitionKeyNode.isNull()) {
            List<AppDefinition> appDefinitions = appRepositoryService.createAppDefinitionQuery().appDefinitionKey(appDefinitionKeyNode.asText()).list();
            List<String> parentDeploymentIds = new ArrayList<>();
            for (AppDefinition appDefinition : appDefinitions) {
                parentDeploymentIds.add(appDefinition.getDeploymentId());
            }

            List<Deployment> deployments = repositoryService.createDeploymentQuery().parentDeploymentIds(parentDeploymentIds).list();
            List<String> deploymentIds = new ArrayList<>();
            for (Deployment deployment : deployments) {
                deploymentIds.add(deployment.getId());
            }

            List<CmmnDeployment> cmmnDeployments = cmmnRepositoryService.createDeploymentQuery().parentDeploymentIds(parentDeploymentIds).list();
            List<String> cmmnDeploymentIds = new ArrayList<>();
            for (CmmnDeployment deployment : cmmnDeployments) {
                cmmnDeploymentIds.add(deployment.getId());
            }

            taskInfoQueryWrapper.getTaskInfoQuery().or()
                .deploymentIdIn(deploymentIds)
                .cmmnDeploymentIdIn(cmmnDeploymentIds)
                .taskCategory(appDefinitionKeyNode.asText())
                .endOr();
        }

        JsonNode processInstanceIdNode = requestNode.get("processInstanceId");
        if (processInstanceIdNode != null && !processInstanceIdNode.isNull()) {
            handleProcessInstanceFiltering(currentUser, taskInfoQueryWrapper, processInstanceIdNode);
        }

        JsonNode caseInstanceIdNode = requestNode.get("caseInstanceId");
        if (caseInstanceIdNode != null && !caseInstanceIdNode.isNull()) {
            handleCaseInstanceFiltering(currentUser, taskInfoQueryWrapper, caseInstanceIdNode);
        }

        JsonNode textNode = requestNode.get("text");
        if (textNode != null && !textNode.isNull()) {
            handleTextFiltering(taskInfoQueryWrapper, textNode);
        }

        JsonNode assignmentNode = requestNode.get("assignment");
        if (assignmentNode != null && !assignmentNode.isNull()) {
            handleAssignment(taskInfoQueryWrapper, assignmentNode, currentUser);
        }

        JsonNode processDefinitionNode = requestNode.get("processDefinitionId");
        if (processDefinitionNode != null && !processDefinitionNode.isNull()) {
            handleProcessDefinition(taskInfoQueryWrapper, processDefinitionNode);
        }

        JsonNode dueBeforeNode = requestNode.get("dueBefore");
        if (dueBeforeNode != null && !dueBeforeNode.isNull()) {
            handleDueBefore(taskInfoQueryWrapper, dueBeforeNode);
        }

        JsonNode dueAfterNode = requestNode.get("dueAfter");
        if (dueAfterNode != null && !dueAfterNode.isNull()) {
            handleDueAfter(taskInfoQueryWrapper, dueAfterNode);
        }

        JsonNode sortNode = requestNode.get("sort");
        if (sortNode != null) {
            handleSorting(taskInfoQueryWrapper, sortNode);
        }

        int page = 0;
        JsonNode pageNode = requestNode.get("page");
        if (pageNode != null && !pageNode.isNull()) {
            page = pageNode.asInt(0);
        }

        int size = DEFAULT_PAGE_SIZE;
        JsonNode sizeNode = requestNode.get("size");
        if (sizeNode != null && !sizeNode.isNull()) {
            size = sizeNode.asInt(DEFAULT_PAGE_SIZE);
        }

        List<? extends TaskInfo> tasks = taskInfoQueryWrapper.getTaskInfoQuery().listPage(page * size, size);

        JsonNode includeProcessInstanceNode = requestNode.get("includeProcessInstance");
        Map<String, String> processInstancesNames = new HashMap<>();
        Map<String, String> caseInstancesNames = new HashMap<>();
        if (includeProcessInstanceNode != null) {
            handleIncludeProcessInstance(taskInfoQueryWrapper, includeProcessInstanceNode, tasks, processInstancesNames);
            handleIncludeCaseInstance(taskInfoQueryWrapper, includeProcessInstanceNode, tasks, caseInstancesNames);
        }
        ResultListDataRepresentation result = new ResultListDataRepresentation(convertTaskInfoList(tasks, processInstancesNames, caseInstancesNames));

        // In case we're not on the first page and the size exceeds the page size, we need to do an additional count for the total
        if (page != 0 || tasks.size() == size) {
            Long totalCount = taskInfoQueryWrapper.getTaskInfoQuery().count();
            result.setTotal((long) totalCount.intValue());
            result.setStart(page * size);
        }
        return result;
    }

    protected void handleProcessInstanceFiltering(SecurityScope currentUser, TaskInfoQueryWrapper taskInfoQueryWrapper, JsonNode processInstanceIdNode) {
        String processInstanceId = processInstanceIdNode.asText();
        taskInfoQueryWrapper.getTaskInfoQuery().processInstanceId(processInstanceId);
    }

    protected void handleCaseInstanceFiltering(SecurityScope currentUser, TaskInfoQueryWrapper taskInfoQueryWrapper, JsonNode caseInstanceIdNode) {
        String caseInstanceId = caseInstanceIdNode.asText();
        taskInfoQueryWrapper.getTaskInfoQuery().scopeId(caseInstanceId).scopeType("cmmn");
    }

    protected void handleTextFiltering(TaskInfoQueryWrapper taskInfoQueryWrapper, JsonNode textNode) {
        String text = textNode.asText();
        taskInfoQueryWrapper.getTaskInfoQuery().taskNameLikeIgnoreCase("%" + text + "%");
    }

    protected void handleAssignment(TaskInfoQueryWrapper taskInfoQueryWrapper, JsonNode assignmentNode, SecurityScope currentUser) {
        String assignment = assignmentNode.asText();
        if (assignment.length() > 0) {
            String currentUserId = currentUser.getUserId();
            if ("assignee".equals(assignment)) {
                taskInfoQueryWrapper.getTaskInfoQuery().taskAssignee(currentUserId);

            } else if ("candidate".equals(assignment)) {
                taskInfoQueryWrapper.getTaskInfoQuery().taskCandidateUser(currentUserId);

                Collection<String> userGroupIds = currentUser.getGroupIds();

                if (!userGroupIds.isEmpty()) {
                    taskInfoQueryWrapper.getTaskInfoQuery().taskCandidateGroupIn(userGroupIds);
                }

            } else if (assignment.startsWith("group_")) {
                String groupIdString = assignment.replace("group_", "");
                try {
                    Long.valueOf(groupIdString);
                } catch (NumberFormatException e) {
                    throw new BadRequestException("Invalid group id");
                }
                taskInfoQueryWrapper.getTaskInfoQuery().taskCandidateGroup(groupIdString);

            } else { // Default = involved
                taskInfoQueryWrapper.getTaskInfoQuery().taskInvolvedUser(currentUserId);
            }
        }
    }

    protected void handleProcessDefinition(TaskInfoQueryWrapper taskInfoQueryWrapper, JsonNode processDefinitionIdNode) {
        String processDefinitionId = processDefinitionIdNode.asText();
        taskInfoQueryWrapper.getTaskInfoQuery().processDefinitionId(processDefinitionId);
    }

    protected void handleDueBefore(TaskInfoQueryWrapper taskInfoQueryWrapper, JsonNode dueBeforeNode) {
        String date = dueBeforeNode.asText();
        try {
            Date d = iso8601DateFormat.parse(date);
            taskInfoQueryWrapper.getTaskInfoQuery().taskDueBefore(d);

        } catch (Exception e) {
            LOGGER.error("Error parsing due before date {}, ignoring it", date);
        }
    }

    protected void handleDueAfter(TaskInfoQueryWrapper taskInfoQueryWrapper, JsonNode dueAfterNode) {
        String date = dueAfterNode.asText();
        try {
            Date d = iso8601DateFormat.parse(date);
            taskInfoQueryWrapper.getTaskInfoQuery().taskDueAfter(d);

        } catch (Exception e) {
            LOGGER.error("Error parsing due after date {}, ignoring it", date);
        }
    }

    protected void handleSorting(TaskInfoQueryWrapper taskInfoQueryWrapper, JsonNode sortNode) {
        String sort = sortNode.asText();

        if (SORT_CREATED_ASC.equals(sort)) {
            taskInfoQueryWrapper.getTaskInfoQuery().orderByTaskCreateTime().asc();
        } else if (SORT_CREATED_DESC.equals(sort)) {
            taskInfoQueryWrapper.getTaskInfoQuery().orderByTaskCreateTime().desc();
        } else if (SORT_DUE_ASC.equals(sort)) {
            taskInfoQueryWrapper.getTaskInfoQuery().orderByDueDateNullsLast().asc();
        } else if (SORT_DUE_DESC.equals(sort)) {
            taskInfoQueryWrapper.getTaskInfoQuery().orderByDueDateNullsLast().desc();
        } else {
            // Default
            taskInfoQueryWrapper.getTaskInfoQuery().orderByTaskCreateTime().desc();
        }
    }

    protected void handleIncludeProcessInstance(TaskInfoQueryWrapper taskInfoQueryWrapper, JsonNode includeProcessInstanceNode, List<? extends TaskInfo> tasks, Map<String, String> processInstanceNames) {
        if (includeProcessInstanceNode.asBoolean() && CollectionUtils.isNotEmpty(tasks)) {
            Set<String> processInstanceIds = new HashSet<>();
            for (TaskInfo task : tasks) {
                if (task.getProcessInstanceId() != null) {
                    processInstanceIds.add(task.getProcessInstanceId());
                }
            }
            if (CollectionUtils.isNotEmpty(processInstanceIds)) {
                if (taskInfoQueryWrapper.getTaskInfoQuery() instanceof HistoricTaskInstanceQuery) {
                    List<HistoricProcessInstance> processInstances = historyService.createHistoricProcessInstanceQuery().processInstanceIds(processInstanceIds).list();
                    for (HistoricProcessInstance processInstance : processInstances) {
                        processInstanceNames.put(processInstance.getId(), processInstance.getName());
                    }
                } else {
                    List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().processInstanceIds(processInstanceIds).list();
                    for (ProcessInstance processInstance : processInstances) {
                        processInstanceNames.put(processInstance.getId(), processInstance.getName());
                    }
                }
            }
        }
    }

    protected void handleIncludeCaseInstance(TaskInfoQueryWrapper taskInfoQueryWrapper, JsonNode includeProcessInstanceNode, List<? extends TaskInfo> tasks, Map<String, String> caseInstanceNames) {
        if (includeProcessInstanceNode.asBoolean() && CollectionUtils.isNotEmpty(tasks)) {
            Set<String> caseInstanceIds = new HashSet<>();
            for (TaskInfo task : tasks) {
                if (task.getScopeId() != null) {
                    caseInstanceIds.add(task.getScopeId());
                }
            }
            if (CollectionUtils.isNotEmpty(caseInstanceIds)) {
                if (taskInfoQueryWrapper.getTaskInfoQuery() instanceof HistoricTaskInstanceQuery) {
                    List<HistoricCaseInstance> caseInstances = cmmnHistoryService.createHistoricCaseInstanceQuery().caseInstanceIds(caseInstanceIds).list();
                    for (HistoricCaseInstance caseInstance : caseInstances) {
                        caseInstanceNames.put(caseInstance.getId(), caseInstance.getName());
                    }
                } else {
                    List<CaseInstance> caseInstances = cmmnRuntimeService.createCaseInstanceQuery().caseInstanceIds(caseInstanceIds).list();
                    for (CaseInstance caseInstance : caseInstances) {
                        caseInstanceNames.put(caseInstance.getId(), caseInstance.getName());
                    }
                }
            }
        }
    }

    protected List<TaskRepresentation> convertTaskInfoList(List<? extends TaskInfo> tasks, Map<String, String> processInstanceNames, Map<String, String> caseInstancesNames) {
        List<TaskRepresentation> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(tasks)) {
            for (TaskInfo task : tasks) {

                TaskRepresentation taskRepresentation = null;
                if (task.getScopeDefinitionId() != null) {
                    CaseDefinition caseDefinition = cmmnRepositoryService.getCaseDefinition(task.getScopeDefinitionId());
                    taskRepresentation = new TaskRepresentation(task, caseDefinition, caseInstancesNames.get(task.getScopeId()));

                } else {
                    ProcessDefinitionEntity processDefinition = null;
                    if (task.getProcessDefinitionId() != null) {
                        processDefinition = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(task.getProcessDefinitionId());
                    }
                    taskRepresentation = new TaskRepresentation(task, processDefinition, processInstanceNames.get(task.getProcessInstanceId()));
                }

                if (StringUtils.isNotEmpty(task.getAssignee())) {
                    CachedUser cachedUser = userCache.getUser(task.getAssignee());
                    if (cachedUser != null && cachedUser.getUser() != null) {
                        User assignee = cachedUser.getUser();
                        taskRepresentation.setAssignee(new UserRepresentation(assignee));
                    } else {
                        taskRepresentation.setAssignee(new UserRepresentation(task.getAssignee()));
                    }
                }

                result.add(taskRepresentation);
            }
        }
        return result;
    }
}
