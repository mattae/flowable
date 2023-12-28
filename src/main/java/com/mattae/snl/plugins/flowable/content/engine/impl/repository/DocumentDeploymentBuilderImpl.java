package com.mattae.snl.plugins.flowable.content.engine.impl.repository;

import com.mattae.snl.plugins.flowable.content.api.DocumentDeployment;
import com.mattae.snl.plugins.flowable.content.api.DocumentDeploymentBuilder;
import com.mattae.snl.plugins.flowable.content.config.ContentEngineConfiguration;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.DocumentDeploymentEntity;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.DocumentResourceEntity;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.DocumentResourceEntityManager;
import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.impl.util.IoUtil;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DocumentDeploymentBuilderImpl implements DocumentDeploymentBuilder {
    protected DocumentRepositoryServiceImpl repositoryService;

    protected DocumentResourceEntityManager resourceEntityManager;

    protected DocumentDeploymentEntity deployment;

    protected boolean isDuplicateFilterEnabled;

    public DocumentDeploymentBuilderImpl() {
        ContentEngineConfiguration contentEngineConfiguration = CommandContextUtil.getContentEngineConfiguration();
        this.repositoryService = (DocumentRepositoryServiceImpl) contentEngineConfiguration.getDocumentRepositoryService();
        this.deployment = contentEngineConfiguration.getDocumentDeploymentEntityManager().create();
        this.resourceEntityManager = contentEngineConfiguration.getDocumentResourceEntityManager();
    }

    public DocumentDeploymentBuilder addInputStream(String resourceName, InputStream inputStream) {
        if (inputStream == null)
            throw new FlowableException("inputStream for resource '" + resourceName + "' is null");
        byte[] bytes = null;
        try {
            bytes = IoUtil.readInputStream(inputStream, resourceName);
        } catch (Exception e) {
            throw new FlowableException("could not get byte array from resource '" + resourceName + "'");
        }
        if (bytes == null)
            throw new FlowableException("byte array for resource '" + resourceName + "' is null");
        DocumentResourceEntity resource = this.resourceEntityManager.create();
        resource.setName(stripFoldersFromResourceName(resourceName));
        resource.setBytes(bytes);
        this.deployment.addResource(resource);
        return this;
    }

    public DocumentDeploymentBuilder addClasspathResource(String resource) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resource);
        if (inputStream == null)
            throw new FlowableException("resource '" + resource + "' not found");
        return addInputStream(resource, inputStream);
    }

    public DocumentDeploymentBuilder addString(String resourceName, String text) {
        if (text == null)
            throw new FlowableException("text is null");
        DocumentResourceEntity resource = this.resourceEntityManager.create();
        resource.setName(stripFoldersFromResourceName(resourceName));
        resource.setBytes(text.getBytes(StandardCharsets.UTF_8));
        this.deployment.addResource(resource);
        return this;
    }

    public DocumentDeploymentBuilder addBytes(String resourceName, byte[] bytes) {
        if (bytes == null)
            throw new FlowableException("bytes array is null");
        DocumentResourceEntity resource = this.resourceEntityManager.create();
        resource.setName(stripFoldersFromResourceName(resourceName));
        resource.setBytes(bytes);
        this.deployment.addResource(resource);
        return this;
    }

    public DocumentDeploymentBuilder addZipInputStream(ZipInputStream zipInputStream) {
        try {
            ZipEntry entry = zipInputStream.getNextEntry();
            while (entry != null) {
                if (!entry.isDirectory()) {
                    String entryName = entry.getName();
                    byte[] bytes = IoUtil.readInputStream(zipInputStream, entryName);
                    DocumentResourceEntity resource = this.resourceEntityManager.create();
                    resource.setName(stripFoldersFromResourceName(entryName));
                    resource.setBytes(bytes);
                    this.deployment.addResource(resource);
                }
                entry = zipInputStream.getNextEntry();
            }
        } catch (Exception e) {
            throw new FlowableException("problem reading zip input stream", e);
        }
        return this;
    }

    protected String stripFoldersFromResourceName(String resourceName) {
        if (resourceName != null && !resourceName.isEmpty()) {
            int lastForwardSlashIndex = resourceName.lastIndexOf('/');
            if (lastForwardSlashIndex > 0)
                resourceName = resourceName.substring(lastForwardSlashIndex + 1);
            int lastBackSlashIndex = resourceName.lastIndexOf('\\');
            if (lastBackSlashIndex > 0)
                resourceName = resourceName.substring(lastBackSlashIndex + 1);
        }
        return resourceName;
    }

    public DocumentDeploymentBuilder name(String name) {
        this.deployment.setName(name);
        return this;
    }

    public DocumentDeploymentBuilder category(String category) {
        this.deployment.setCategory(category);
        return this;
    }

    public DocumentDeploymentBuilder key(String key) {
        this.deployment.setKey(key);
        return this;
    }

    public DocumentDeploymentBuilder parentDeploymentId(String parentDeploymentId) {
        this.deployment.setParentDeploymentId(parentDeploymentId);
        return this;
    }

    public DocumentDeploymentBuilder tenantId(String tenantId) {
        this.deployment.setTenantId(tenantId);
        return this;
    }

    public DocumentDeployment deploy() {
        return this.repositoryService.deploy(this);
    }

    public DocumentDeployment getDeployment() {
        return this.deployment;
    }

    public boolean isDuplicateFilterEnabled() {
        return this.isDuplicateFilterEnabled;
    }
}
