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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattae.snl.plugins.flowable.model.runtime.ProcessDefinitionRepresentation;
import io.github.jbella.snl.core.api.services.errors.RecordNotFoundException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.app.api.AppRepositoryService;
import org.flowable.app.api.repository.AppDefinition;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.StartEvent;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.form.api.FormInfo;
import org.flowable.form.api.FormRepositoryService;
import org.flowable.ui.common.model.ResultListDataRepresentation;
import org.flowable.ui.common.security.SecurityScope;
import org.flowable.ui.common.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tijs Rademakers
 */
@Service
@Transactional
public class FlowableProcessDefinitionService {

    protected final RepositoryService repositoryService;

    protected final ProcessEngineConfiguration processEngineConfiguration;

    protected final AppRepositoryService appRepositoryService;

    protected final FormRepositoryService formRepositoryService;

    protected final PermissionService permissionService;

    protected final ObjectMapper objectMapper;

    public FlowableProcessDefinitionService(RepositoryService repositoryService,
                                            ProcessEngineConfiguration processEngineConfiguration,
                                            AppRepositoryService appRepositoryService,
                                            FormRepositoryService formRepositoryService,
                                            PermissionService permissionService,
                                            ObjectMapper objectMapper) {
        this.repositoryService = repositoryService;
        this.processEngineConfiguration = processEngineConfiguration;
        this.appRepositoryService = appRepositoryService;
        this.formRepositoryService = formRepositoryService;
        this.permissionService = permissionService;
        this.objectMapper = objectMapper;
    }

    public FormInfo getProcessDefinitionStartForm(String processDefinitionId) {

        ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefinitionId);

        try {
            return getStartForm(processDefinition);

        } catch (FlowableObjectNotFoundException aonfe) {
            // Process definition does not exist
            throw new RecordNotFoundException("No process definition found with the given id: " + processDefinitionId);
        }
    }

    public ResultListDataRepresentation getProcessDefinitions(Boolean latest, String appDefinitionKey) {

        ProcessDefinitionQuery definitionQuery = repositoryService.createProcessDefinitionQuery();

        if (appDefinitionKey != null) {
            AppDefinition appDefinition = appRepositoryService.createAppDefinitionQuery().appDefinitionKey(appDefinitionKey).latestVersion().singleResult();
            Deployment deployment = repositoryService.createDeploymentQuery().parentDeploymentId(appDefinition.getDeploymentId()).singleResult();

            if (deployment != null) {
                definitionQuery.deploymentId(deployment.getId());
            } else {
                return new ResultListDataRepresentation(new ArrayList<ProcessDefinitionRepresentation>());
            }

        } else {

            if (latest != null && latest) {
                definitionQuery.latestVersion();
            }
        }

        List<ProcessDefinition> definitions = definitionQuery.list();

        List<ProcessDefinition> startableDefinitions = new ArrayList<>();
        SecurityScope currentUser = SecurityUtils.getCurrentSecurityScope();
        for (ProcessDefinition definition : definitions) {
            if (currentUser == null || permissionService.canStartProcess(currentUser, definition)) {
                startableDefinitions.add(definition);
            }
        }

        ResultListDataRepresentation result = new ResultListDataRepresentation(convertDefinitionList(startableDefinitions));
        return result;
    }

    protected FormInfo getStartForm(ProcessDefinition processDefinition) {
        FormInfo formInfo = null;
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());
        Process process = bpmnModel.getProcessById(processDefinition.getKey());
        FlowElement startElement = process.getInitialFlowElement();
        if (startElement instanceof StartEvent startEvent) {
            if (StringUtils.isNotEmpty(startEvent.getFormKey())) {
                Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(processDefinition.getDeploymentId()).singleResult();
                formInfo = formRepositoryService.getFormModelByKeyAndParentDeploymentId(startEvent.getFormKey(), deployment.getParentDeploymentId(),
                    processDefinition.getTenantId(), processEngineConfiguration.isFallbackToDefaultTenant());
            }
        }

        if (formInfo == null) {
            // Definition found, but no form attached
            throw new RecordNotFoundException("Process definition does not have a form defined: " + processDefinition.getId());
        }

        return formInfo;
    }

    protected List<ProcessDefinitionRepresentation> convertDefinitionList(List<ProcessDefinition> definitions) {
        List<ProcessDefinitionRepresentation> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(definitions)) {
            for (ProcessDefinition processDefinition : definitions) {
                ProcessDefinitionRepresentation rep = new ProcessDefinitionRepresentation(processDefinition);
                result.add(rep);
            }
        }
        return result;
    }

}
