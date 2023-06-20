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


import jakarta.servlet.http.HttpServletRequest;
import org.flowable.common.engine.api.query.QueryProperty;
import org.flowable.common.rest.api.DataResponse;
import org.flowable.form.api.FormDefinitionQuery;
import org.flowable.form.api.FormRepositoryService;
import org.flowable.form.engine.impl.FormQueryProperty;
import org.flowable.form.rest.FormRestResponseFactory;
import org.flowable.form.rest.service.api.repository.FormDefinitionResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.flowable.common.rest.api.PaginateListUtil.paginateList;



/**
 * @author Yvo Swillens
 */

@RestController
@RequestMapping("/app/rest")
public class FormDefinitionCollectionResource {

    private static final Map<String, QueryProperty> properties = new HashMap<>();

    static {
        properties.put("id", FormQueryProperty.FORM_ID);
        properties.put("key", FormQueryProperty.FORM_DEFINITION_KEY);
        properties.put("category", FormQueryProperty.FORM_CATEGORY);
        properties.put("name", FormQueryProperty.FORM_NAME);
        properties.put("version", FormQueryProperty.FORM_VERSION);
        properties.put("deploymentId", FormQueryProperty.DEPLOYMENT_ID);
        properties.put("tenantId", FormQueryProperty.FORM_TENANT_ID);
    }

    protected final FormRestResponseFactory formRestResponseFactory;

    protected final FormRepositoryService formRepositoryService;

    public FormDefinitionCollectionResource(FormRestResponseFactory formRestResponseFactory, FormRepositoryService formRepositoryService) {
        this.formRestResponseFactory = formRestResponseFactory;
        this.formRepositoryService = formRepositoryService;
    }


    @GetMapping(value = "/form-repository/form-definitions", produces = "application/json")
    public DataResponse<FormDefinitionResponse> getForms(@RequestParam Map<String, String> allRequestParams, HttpServletRequest request) {
        FormDefinitionQuery formDefinitionQuery = formRepositoryService.createFormDefinitionQuery();

        // Populate filter-parameters
        if (allRequestParams.containsKey("category")) {
            formDefinitionQuery.formCategory(allRequestParams.get("category"));
        }
        if (allRequestParams.containsKey("categoryLike")) {
            formDefinitionQuery.formCategoryLike(allRequestParams.get("categoryLike"));
        }
        if (allRequestParams.containsKey("categoryNotEquals")) {
            formDefinitionQuery.formCategoryNotEquals(allRequestParams.get("categoryNotEquals"));
        }
        if (allRequestParams.containsKey("key")) {
            formDefinitionQuery.formDefinitionKey(allRequestParams.get("key"));
        }
        if (allRequestParams.containsKey("keyLike")) {
            formDefinitionQuery.formDefinitionKeyLike(allRequestParams.get("keyLike"));
        }
        if (allRequestParams.containsKey("name")) {
            formDefinitionQuery.formName(allRequestParams.get("name"));
        }
        if (allRequestParams.containsKey("nameLike")) {
            formDefinitionQuery.formNameLike(allRequestParams.get("nameLike"));
        }
        if (allRequestParams.containsKey("resourceName")) {
            formDefinitionQuery.formResourceName(allRequestParams.get("resourceName"));
        }
        if (allRequestParams.containsKey("resourceNameLike")) {
            formDefinitionQuery.formResourceNameLike(allRequestParams.get("resourceNameLike"));
        }
        if (allRequestParams.containsKey("version")) {
            formDefinitionQuery.formVersion(Integer.valueOf(allRequestParams.get("version")));
        }
        if (allRequestParams.containsKey("versionGreaterThan")) {
            formDefinitionQuery.formVersionGreaterThan(Integer.valueOf(allRequestParams.get("versionGreaterThan")));
        }
        if (allRequestParams.containsKey("versionGreaterThanOrEquals")) {
            formDefinitionQuery.formVersionGreaterThanOrEquals(Integer.valueOf(allRequestParams.get("versionGreaterThanOrEquals")));
        }
        if (allRequestParams.containsKey("versionLowerThan")) {
            formDefinitionQuery.formVersionLowerThan(Integer.valueOf(allRequestParams.get("versionLowerThan")));
        }
        if (allRequestParams.containsKey("versionLowerThanOrEquals")) {
            formDefinitionQuery.formVersionLowerThanOrEquals(Integer.valueOf(allRequestParams.get("versionLowerThanOrEquals")));
        }
        if (allRequestParams.containsKey("deploymentId")) {
            formDefinitionQuery.deploymentId(allRequestParams.get("deploymentId"));
        }
        if (allRequestParams.containsKey("parentDeploymentId")) {
            formDefinitionQuery.parentDeploymentId(allRequestParams.get("parentDeploymentId"));
        }
        if (allRequestParams.containsKey("tenantId")) {
            formDefinitionQuery.formTenantId(allRequestParams.get("tenantId"));
        }
        if (allRequestParams.containsKey("tenantIdLike")) {
            formDefinitionQuery.formTenantIdLike(allRequestParams.get("tenantIdLike"));
        }
        if (allRequestParams.containsKey("withoutTenantId")) {
            boolean withoutTenantId = Boolean.parseBoolean(allRequestParams.get("withoutTenantId"));
            if (withoutTenantId) {
                formDefinitionQuery.formWithoutTenantId();
            }
        }
        if (allRequestParams.containsKey("latest")) {
            boolean latest = Boolean.parseBoolean(allRequestParams.get("latest"));
            if (latest) {
                formDefinitionQuery.latestVersion();
            }
        }


        return paginateList(allRequestParams, formDefinitionQuery, "name", properties, formRestResponseFactory::createFormResponseList);
    }
}
