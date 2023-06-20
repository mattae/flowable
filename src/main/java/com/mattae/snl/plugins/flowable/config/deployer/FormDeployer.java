

package com.mattae.snl.plugins.flowable.config.deployer;

import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.repository.EngineDeployment;
import org.flowable.common.engine.api.repository.EngineResource;
import org.flowable.common.engine.impl.EngineDeployer;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.form.api.FormDeploymentBuilder;
import org.flowable.form.api.FormRepositoryService;

import java.util.Map;

@Slf4j
public class FormDeployer implements EngineDeployer {
    public void deploy(final EngineDeployment deployment, final Map<String, Object> deploymentSettings) {
        if (!deployment.isNew()) {
            return;
        }
        LOG.debug("FormDeployer: processing deployment {}", deployment.getName());
        FormDeploymentBuilder formDeploymentBuilder = null;
        final Map<String, EngineResource> resources = deployment.getResources();
        for (final String resourceName : resources.keySet()) {
            if (resourceName.endsWith(".form")) {
                LOG.info("FormDeployer: processing resource {}", resourceName);
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