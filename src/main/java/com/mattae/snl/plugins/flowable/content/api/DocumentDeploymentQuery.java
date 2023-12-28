package com.mattae.snl.plugins.flowable.content.api;

import org.flowable.common.engine.api.query.Query;

import java.util.List;

public interface DocumentDeploymentQuery extends Query<DocumentDeploymentQuery, DocumentDeployment> {
    DocumentDeploymentQuery deploymentId(String paramString);

    DocumentDeploymentQuery deploymentIds(List<String> paramList);

    DocumentDeploymentQuery deploymentName(String paramString);

    DocumentDeploymentQuery deploymentNameLike(String paramString);

    DocumentDeploymentQuery deploymentKey(String paramString);

    DocumentDeploymentQuery deploymentCategory(String paramString);

    DocumentDeploymentQuery deploymentCategoryNotEquals(String paramString);

    DocumentDeploymentQuery deploymentTenantId(String paramString);

    DocumentDeploymentQuery deploymentTenantIdLike(String paramString);

    DocumentDeploymentQuery deploymentWithoutTenantId();

    DocumentDeploymentQuery documentDefinitionKey(String paramString);

    DocumentDeploymentQuery documentDefinitionKeyLike(String paramString);

    DocumentDeploymentQuery parentDeploymentId(String paramString);

    DocumentDeploymentQuery parentDeploymentIdLike(String paramString);

    DocumentDeploymentQuery latest();

    DocumentDeploymentQuery orderByDeploymentId();

    DocumentDeploymentQuery orderByDeploymentName();

    DocumentDeploymentQuery orderByDeploymentTime();

    DocumentDeploymentQuery orderByTenantId();
}
