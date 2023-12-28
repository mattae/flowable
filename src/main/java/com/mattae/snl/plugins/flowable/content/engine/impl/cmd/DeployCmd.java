package com.mattae.snl.plugins.flowable.content.engine.impl.cmd;

import com.mattae.snl.plugins.flowable.content.api.DocumentDeployment;
import com.mattae.snl.plugins.flowable.content.api.DocumentDeploymentQuery;
import com.mattae.snl.plugins.flowable.content.config.ContentEngineConfiguration;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.DocumentDeploymentEntity;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.DocumentResourceEntity;
import com.mattae.snl.plugins.flowable.content.engine.impl.repository.DocumentDeploymentBuilderImpl;
import com.mattae.snl.plugins.flowable.content.engine.impl.repository.DocumentDeploymentQueryImpl;
import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.api.repository.EngineDeployment;
import org.flowable.common.engine.api.repository.EngineResource;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;

import java.util.*;

public class DeployCmd implements Command<DocumentDeployment> {
    protected DocumentDeploymentBuilderImpl deploymentBuilder;

    public DeployCmd(DocumentDeploymentBuilderImpl deploymentBuilder) {
        this.deploymentBuilder = deploymentBuilder;
    }

    public DocumentDeployment execute(CommandContext commandContext) {
        ContentEngineConfiguration contentEngineConfiguration = CommandContextUtil.getContentEngineConfiguration(commandContext);
        DocumentDeploymentEntity deployment = (DocumentDeploymentEntity) this.deploymentBuilder.getDeployment();
        if (this.deploymentBuilder.isDuplicateFilterEnabled()) {
            List<DocumentDeployment> existingDeployments = new ArrayList<>();
            if (deployment.getTenantId() == null || "".equals(deployment.getTenantId())) {
                List<DocumentDeployment> deploymentEntities = ((DocumentDeploymentQuery) (new DocumentDeploymentQueryImpl(contentEngineConfiguration.getCommandExecutor())).deploymentName(deployment.getName()).deploymentWithoutTenantId().orderByDeploymentTime().desc()).listPage(0, 1);
                if (!deploymentEntities.isEmpty())
                    existingDeployments.add(deploymentEntities.get(0));
            } else {
                List<DocumentDeployment> deploymentList = ((DocumentDeploymentQuery) contentEngineConfiguration.getDocumentRepositoryService().createDeploymentQuery().deploymentName(deployment.getName()).deploymentTenantId(deployment.getTenantId()).orderByDeploymentTime().desc()).listPage(0, 1);
                if (!deploymentList.isEmpty())
                    existingDeployments.addAll(deploymentList);
            }
            if (!existingDeployments.isEmpty()) {
                DocumentDeploymentEntity existingDeployment = (DocumentDeploymentEntity) existingDeployments.get(0);
                Map<String, EngineResource> resourceMap = new HashMap<>();
                List<DocumentResourceEntity> resourceList = CommandContextUtil.getDocumentResourceEntityManager().findResourcesByDeploymentId(existingDeployment.getId());
                for (DocumentResourceEntity resourceEntity : resourceList)
                    resourceMap.put(resourceEntity.getName(), resourceEntity);
                existingDeployment.setResources(resourceMap);
                if (!deploymentsDiffer(deployment, existingDeployment))
                    return (DocumentDeployment) existingDeployment;
            }
        }
        deployment.setDeploymentTime(contentEngineConfiguration.getClock().getCurrentTime());
        deployment.setNew(true);
        CommandContextUtil.getDocumentDeploymentEntityManager(commandContext).insert(deployment);
        contentEngineConfiguration.getDeploymentManager().deploy((EngineDeployment) deployment, null);
        return (DocumentDeployment) deployment;
    }

    protected boolean deploymentsDiffer(DocumentDeploymentEntity deployment, DocumentDeploymentEntity saved) {
        if (deployment.getResources() == null || saved.getResources() == null)
            return true;
        Map<String, EngineResource> resources = deployment.getResources();
        Map<String, EngineResource> savedResources = saved.getResources();
        for (String resourceName : resources.keySet()) {
            EngineResource savedResource = savedResources.get(resourceName);
            if (savedResource == null)
                return true;
            EngineResource resource = resources.get(resourceName);
            byte[] bytes = resource.getBytes();
            byte[] savedBytes = savedResource.getBytes();
            if (!Arrays.equals(bytes, savedBytes))
                return true;
        }
        return false;
    }
}
