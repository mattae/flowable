package com.mattae.snl.plugins.flowable.content.deployer;

import com.mattae.snl.plugins.flowable.content.api.DocumentDeploymentBuilder;
import com.mattae.snl.plugins.flowable.content.api.DocumentRepositoryService;
import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.api.repository.EngineDeployment;
import org.flowable.common.engine.api.repository.EngineResource;
import org.flowable.common.engine.impl.EngineDeployer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class DocumentDefinitionDeployer implements EngineDeployer {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentDefinitionDeployer.class);

    public void deploy(EngineDeployment deployment, Map<String, Object> deploymentSettings) {
        if (!deployment.isNew())
            return;
        LOGGER.debug("DocumentDefinitionDeployer: processing deployment {}", deployment.getName());
        DocumentDeploymentBuilder documentDeploymentBuilder = null;
        Map<String, EngineResource> resources = deployment.getResources();
        for (String resourceName : resources.keySet()) {
            if (resourceName.endsWith(".document")) {
                LOGGER.info("DocumentDefinitionDeployer: processing resource {}", resourceName);
                if (documentDeploymentBuilder == null) {
                    DocumentRepositoryService documentRepositoryService = CommandContextUtil.getDocumentRepositoryService();
                    documentDeploymentBuilder = documentRepositoryService.createDeployment().name(deployment.getName());
                }
                documentDeploymentBuilder.addBytes(resourceName, ((EngineResource) resources.get(resourceName)).getBytes());
            }
        }
        if (documentDeploymentBuilder != null) {
            documentDeploymentBuilder.parentDeploymentId(deployment.getId());
            if (deployment.getTenantId() != null && !deployment.getTenantId().isEmpty())
                documentDeploymentBuilder.tenantId(deployment.getTenantId());
            documentDeploymentBuilder.deploy();
        }
    }
}
