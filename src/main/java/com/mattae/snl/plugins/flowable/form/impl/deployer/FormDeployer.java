package com.mattae.snl.plugins.flowable.form.impl.deployer;

import org.flowable.common.engine.api.repository.EngineDeployment;
import org.flowable.common.engine.api.repository.EngineResource;
import org.flowable.common.engine.impl.EngineDeployer;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.form.api.FormDeploymentBuilder;
import org.flowable.form.api.FormRepositoryService;

import java.util.Map;

public class FormDeployer implements EngineDeployer {
    public void deploy(final EngineDeployment deployment, final Map<String, Object> deploymentSettings) {
        if (!deployment.isNew()) {
            return;
        }
        FormDeploymentBuilder formDeploymentBuilder = null;
        final Map<String, EngineResource> resources = deployment.getResources();
        for (final String resourceName : resources.keySet()) {
            if (resourceName.endsWith(".form")) {
                if (formDeploymentBuilder == null) {
                    final FormRepositoryService formRepositoryService = CommandContextUtil.getFormRepositoryService();
                    formDeploymentBuilder = formRepositoryService.createDeployment().name(deployment.getName());
                }
                formDeploymentBuilder.addFormBytes(resourceName, resources.get(resourceName).getBytes());
            }
        }
        if (formDeploymentBuilder != null) {
            formDeploymentBuilder.parentDeploymentId(deployment.getId());
            if (deployment.getTenantId() != null && !deployment.getTenantId().isEmpty()) {
                formDeploymentBuilder.tenantId(deployment.getTenantId());
            }
            formDeploymentBuilder.deploy();
        }
    }
}
