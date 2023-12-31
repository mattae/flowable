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

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mattae.snl.plugins.flowable.content.api.RenditionService;
import com.mattae.snl.plugins.flowable.model.runtime.CreateProcessInstanceRepresentation;
import com.mattae.snl.plugins.flowable.model.runtime.ProcessInstanceRepresentation;
import com.mattae.snl.plugins.flowable.services.model.ExtendedUserRepresentation;
import io.github.jbella.snl.core.api.services.errors.RecordNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.flowable.content.api.ContentService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.form.api.FormInfo;
import org.flowable.form.api.FormRepositoryService;
import org.flowable.form.api.FormService;
import org.flowable.identitylink.api.IdentityLink;
import org.flowable.identitylink.api.IdentityLinkType;
import org.flowable.idm.api.Picture;
import org.flowable.idm.api.User;
import org.flowable.ui.common.security.SecurityScope;
import org.flowable.ui.common.security.SecurityUtils;
import org.flowable.ui.common.service.exception.BadRequestException;
import org.flowable.ui.common.service.exception.NotPermittedException;
import org.flowable.ui.common.service.idm.cache.UserCache;
import org.flowable.ui.common.service.idm.cache.UserCache.CachedUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @author Tijs Rademakers
 */
@Service
@Transactional
public class FlowableProcessInstanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlowableProcessInstanceService.class);

    protected final RepositoryService repositoryService;

    protected final HistoryService historyService;

    protected final RuntimeService runtimeService;

    protected final FormService formService;

    protected final FormRepositoryService formRepositoryService;

    protected final PermissionService permissionService;

    protected final ContentService contentService;
    protected final RenditionService renditionService;

    protected final FlowableCommentService commentService;

    protected final UserCache userCache;
    protected final IdentityService identityService;

    public FlowableProcessInstanceService(RepositoryService repositoryService, HistoryService historyService,
                                          RuntimeService runtimeService, FormService formService,
                                          FormRepositoryService formRepositoryService, PermissionService permissionService,
                                          ContentService contentService, FlowableCommentService commentService,
                                          IdentityService identityService, UserCache userCache,
                                          RenditionService renditionService) {
        this.repositoryService = repositoryService;
        this.historyService = historyService;
        this.runtimeService = runtimeService;
        this.formService = formService;
        this.formRepositoryService = formRepositoryService;
        this.permissionService = permissionService;
        this.contentService = contentService;
        this.commentService = commentService;
        this.userCache = userCache;
        this.identityService = identityService;
        this.renditionService = renditionService;
    }

    public ProcessInstanceRepresentation getProcessInstance(String processInstanceId, HttpServletResponse response) {

        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

        if (!permissionService.hasReadPermissionOnProcessInstance(SecurityUtils.getAuthenticatedSecurityScope(), processInstance, processInstanceId)) {
            throw new RecordNotFoundException("Process with id: " + processInstanceId + " does not exist or is not available for this user");
        }

        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());

        User userRep = null;
        if (processInstance.getStartUserId() != null) {
            CachedUser user = userCache.getUser(processInstance.getStartUserId());
            if (user != null && user.getUser() != null) {
                userRep = user.getUser();
            }
        }

        ProcessInstanceRepresentation processInstanceResult = new ProcessInstanceRepresentation(processInstance, processDefinition, processDefinition.isGraphicalNotationDefined(), userRep);

        if (processDefinition.hasStartFormKey()) {
            FormInfo formInfo = runtimeService.getStartFormModel(processInstance.getProcessDefinitionId(), processInstance.getId());
            if (formInfo != null) {
                processInstanceResult.setStartFormDefined(true);
            }
        }

        return processInstanceResult;
    }

    public FormInfo getProcessInstanceStartForm(String processInstanceId, HttpServletResponse response) {

        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

        if (!permissionService.hasReadPermissionOnProcessInstance(SecurityUtils.getAuthenticatedSecurityScope(), processInstance, processInstanceId)) {
            throw new RecordNotFoundException("Process with id: " + processInstanceId + " does not exist or is not available for this user");
        }

        return runtimeService.getStartFormModel(processInstance.getProcessDefinitionId(), processInstance.getId());
    }

    public ProcessInstanceRepresentation startNewProcessInstance(CreateProcessInstanceRepresentation startRequest) {
        if (StringUtils.isEmpty(startRequest.getProcessDefinitionId())) {
            throw new BadRequestException("Process definition id is required");
        }

        ProcessDefinition processDefinition = repositoryService.getProcessDefinition(startRequest.getProcessDefinitionId());

        if (!permissionService.canStartProcess(SecurityUtils.getAuthenticatedSecurityScope(), processDefinition)) {
            throw new NotPermittedException("User is not listed as potential starter for process definition with id: " + processDefinition.getId());
        }

        ProcessInstance processInstance = runtimeService.startProcessInstanceWithForm(startRequest.getProcessDefinitionId(),
            startRequest.getOutcome(), startRequest.getValues(), startRequest.getName());

        User user = null;
        if (processInstance.getStartUserId() != null) {
            CachedUser cachedUser = userCache.getUser(processInstance.getStartUserId());
            if (cachedUser != null && cachedUser.getUser() != null) {
                user = cachedUser.getUser();
            }
        }
        return new ProcessInstanceRepresentation(processInstance, processDefinition,
            ((ProcessDefinitionEntity) processDefinition).isGraphicalNotationDefined(), user);

    }

    public void deleteProcessInstance(String processInstanceId) {

        SecurityScope currentUser = SecurityUtils.getAuthenticatedSecurityScope();

        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery()
            .processInstanceId(processInstanceId)
            .startedBy(String.valueOf(currentUser.getUserId())) // Permission
            .singleResult();

        if (processInstance == null) {
            throw new RecordNotFoundException("Process with id: " + processInstanceId + " does not exist or is not started by this user");
        }

        if (processInstance.getEndTime() != null) {
            // Check if a hard delete of process instance is allowed
            if (!permissionService.canDeleteProcessInstance(currentUser, processInstance)) {
                throw new RecordNotFoundException("Process with id: " + processInstanceId + " is already completed and can't be deleted");
            }

            // Delete all content related to the process instance
            contentService.deleteContentItemsByProcessInstanceId(processInstanceId);

            // Delete all comments on tasks and process instances
            commentService.deleteAllCommentsForProcessInstance(processInstanceId);

            // Finally, delete all history for this instance in the engine
            historyService.deleteHistoricProcessInstance(processInstanceId);

        } else {
            runtimeService.deleteProcessInstance(processInstanceId, "Cancelled by " + SecurityUtils.getCurrentUserId());
        }
    }

    public void involveUser(String processInstanceId, ObjectNode requestNode) {
        SecurityScope currentUser = SecurityUtils.getAuthenticatedSecurityScope();

        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery()
            .processInstanceId(processInstanceId)
            .startedBy(String.valueOf(currentUser.getUserId())) // Permission
            .singleResult();

        if (processInstance == null) {
            throw new RecordNotFoundException("Process with id: " + processInstanceId + " does not exist or is not started by this user");
        }

        if (requestNode.get("userId") != null) {
            String userId = requestNode.get("userId").asText();
            CachedUser user = userCache.getUser(userId);
            if (user == null) {
                throw new BadRequestException("Invalid user id");
            }
            runtimeService.addUserIdentityLink(processInstanceId, userId, IdentityLinkType.PARTICIPANT);

        } else {
            throw new BadRequestException("User id is required");
        }

    }

    public void removeInvolvedUser(String processInstanceId, ObjectNode requestNode) {
        SecurityScope currentUser = SecurityUtils.getAuthenticatedSecurityScope();

        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery()
            .processInstanceId(processInstanceId)
            .startedBy(String.valueOf(currentUser.getUserId())) // Permission
            .singleResult();

        if (processInstance == null) {
            throw new RecordNotFoundException("Process with id: " + processInstanceId + " does not exist or is not started by this user");
        }

        String assigneeString;
        if (requestNode.get("userId") != null) {
            String userId = requestNode.get("userId").asText();
            if (userCache.getUser(userId) == null) {
                throw new BadRequestException("Invalid user id");
            }
            assigneeString = String.valueOf(userId);

        } else if (requestNode.get("email") != null) {

            assigneeString = requestNode.get("email").asText();

        } else {
            throw new BadRequestException("User id or email is required");
        }

        runtimeService.deleteUserIdentityLink(processInstanceId, assigneeString, IdentityLinkType.PARTICIPANT);
    }

    public List<ExtendedUserRepresentation> getInvolvedUsers(String processInstanceId) {
        List<IdentityLink> idLinks = runtimeService.getIdentityLinksForProcessInstance(processInstanceId);
        List<ExtendedUserRepresentation> result = new ArrayList<>(idLinks.size());

        for (IdentityLink link : idLinks) {
            // Only include users and non-assignee links
            if (link.getUserId() != null && !IdentityLinkType.ASSIGNEE.equals(link.getType())) {
                CachedUser cachedUser = userCache.getUser(link.getUserId());
                if (cachedUser != null && cachedUser.getUser() != null) {
                    Picture picture = identityService.getUserPicture(cachedUser.getUser().getId());
                    if (picture != null && StringUtils.isNotBlank(picture.getMimeType())) {
                        byte[] bytes = picture.getBytes();
                        String url = String.format("data:%s;base64,%s", picture.getMimeType(), Base64.getEncoder().encodeToString(bytes));
                        result.add(new ExtendedUserRepresentation(cachedUser.getUser(), url));
                    } else {
                        result.add(new ExtendedUserRepresentation(cachedUser.getUser(), null));
                    }
                } else {
                    var user = identityService.createUserQuery().userId(link.getUserId()).singleResult();
                    if (user != null) {
                        Picture picture = identityService.getUserPicture(user.getId());
                        if (picture != null && StringUtils.isNotBlank(picture.getMimeType())) {
                            byte[] bytes = picture.getBytes();
                            String url = String.format("data:%s;base64,%s", picture.getMimeType(), Base64.getEncoder().encodeToString(bytes));
                            result.add(new ExtendedUserRepresentation(user, url));
                        } else {
                            result.add(new ExtendedUserRepresentation(user, null));
                        }
                    }
                }
            }
        }
        return result;
    }
}
