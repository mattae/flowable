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
import com.mattae.snl.plugins.flowable.model.runtime.CaseDefinitionRepresentation;
import io.github.jbella.snl.core.api.services.errors.RecordNotFoundException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.app.api.AppRepositoryService;
import org.flowable.app.api.repository.AppDefinition;
import org.flowable.cmmn.api.CmmnRepositoryService;
import org.flowable.cmmn.api.repository.CaseDefinition;
import org.flowable.cmmn.api.repository.CaseDefinitionQuery;
import org.flowable.cmmn.api.repository.CmmnDeployment;
import org.flowable.cmmn.model.Case;
import org.flowable.cmmn.model.CmmnModel;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.form.api.FormInfo;
import org.flowable.form.api.FormRepositoryService;
import org.flowable.ui.common.model.ResultListDataRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tijs Rademakers
 */
@Service
@Transactional
public class FlowableCaseDefinitionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlowableCaseDefinitionService.class);

    protected final CmmnRepositoryService cmmnRepositoryService;

    protected final AppRepositoryService appRepositoryService;

    protected final PermissionService permissionService;

    protected final ObjectMapper objectMapper;

    protected final FormRepositoryService formRepositoryService;

    public FlowableCaseDefinitionService(CmmnRepositoryService cmmnRepositoryService, AppRepositoryService appRepositoryService, PermissionService permissionService, ObjectMapper objectMapper, FormRepositoryService formRepositoryService) {
        this.cmmnRepositoryService = cmmnRepositoryService;
        this.appRepositoryService = appRepositoryService;
        this.permissionService = permissionService;
        this.objectMapper = objectMapper;
        this.formRepositoryService = formRepositoryService;
    }

    public ResultListDataRepresentation getCaseDefinitions(Boolean latest, String appDefinitionKey) {

        CaseDefinitionQuery definitionQuery = cmmnRepositoryService.createCaseDefinitionQuery();

        if (appDefinitionKey != null) {
            AppDefinition appDefinition = appRepositoryService.createAppDefinitionQuery().appDefinitionKey(appDefinitionKey).latestVersion().singleResult();
            CmmnDeployment deployment = cmmnRepositoryService.createDeploymentQuery().parentDeploymentId(appDefinition.getDeploymentId()).singleResult();

            if (deployment != null) {
                definitionQuery.deploymentId(deployment.getId());
            } else {
                return new ResultListDataRepresentation(new ArrayList<CaseDefinitionRepresentation>());
            }

        } else {

            if (latest != null && latest) {
                definitionQuery.latestVersion();
            }
        }

        List<CaseDefinition> definitions = definitionQuery.list();

        return new ResultListDataRepresentation(convertDefinitionList(definitions));
    }

    public FormInfo getCaseDefinitionStartForm(String caseDefinitionId) {

        CaseDefinition caseDefinition = cmmnRepositoryService.getCaseDefinition(caseDefinitionId);

        try {
            return getStartForm(caseDefinition);

        } catch (FlowableObjectNotFoundException aonfe) {
            throw new RecordNotFoundException("No case definition found with the given id: " + caseDefinitionId);
        }
    }

    protected List<CaseDefinitionRepresentation> convertDefinitionList(List<CaseDefinition> definitions) {
        List<CaseDefinitionRepresentation> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(definitions)) {
            for (CaseDefinition caseDefinition : definitions) {
                CaseDefinitionRepresentation rep = new CaseDefinitionRepresentation(caseDefinition);
                result.add(rep);
            }
        }
        return result;
    }

    protected FormInfo getStartForm(CaseDefinition caseDefinition) {
        CmmnModel cmmnModel = this.cmmnRepositoryService.getCmmnModel(caseDefinition.getId());
        List<Case> cases = cmmnModel.getCases();
        if (cases == null || cases.size() != 1) {
            throw new FlowableObjectNotFoundException("Case definition " + caseDefinition.getId() + " start form was not found.");
        }

        Case caze = cases.get(0);
        if (caze == null || caze.getPlanModel() == null || StringUtils.isEmpty(caze.getPlanModel().getFormKey())) {
            throw new FlowableObjectNotFoundException("Case from case definition " + caseDefinition.getId() + " does not contain any start form.");
        }

        String formKey = caze.getPlanModel().getFormKey();
        return formRepositoryService.getFormModelByKey(formKey);
    }

}
