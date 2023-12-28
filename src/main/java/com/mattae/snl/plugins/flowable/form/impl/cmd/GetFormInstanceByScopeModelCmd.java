package com.mattae.snl.plugins.flowable.form.impl.cmd;

import java.util.Map;

public class GetFormInstanceByScopeModelCmd extends AbstractGetFormInstanceModelCmd {

    public GetFormInstanceByScopeModelCmd(String formDefinitionKey, String parentDeploymentId, String scopeId, String scopeType,
                                          Map<String, Object> variables) {

        initializeValuesForScope(formDefinitionKey, parentDeploymentId, formDefinitionKey, null, scopeId, scopeType, variables, false);
    }

    public GetFormInstanceByScopeModelCmd(String formDefinitionKey, String parentDeploymentId, String scopeId, String scopeType,
                                          String tenantId, Map<String, Object> variables, boolean fallbackToDefaultTenant) {

        initializeValuesForScope(formDefinitionKey, parentDeploymentId, null, tenantId,
            scopeId, scopeType, variables, fallbackToDefaultTenant);
    }
}
