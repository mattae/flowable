package com.mattae.snl.plugins.flowable.content.config;

import com.mattae.snl.plugins.flowable.content.services.ContentServiceImpl;
import com.mattae.snl.plugins.flowable.content.api.*;
import com.mattae.snl.plugins.flowable.content.deployer.DocumentDefinitionCacheEntry;
import com.mattae.snl.plugins.flowable.content.deployer.DocumentDefinitionDeployer;
import com.mattae.snl.plugins.flowable.content.deployer.DocumentDeploymentManager;
import com.mattae.snl.plugins.flowable.content.deployer.DocumentResourceConverterImpl;
import com.mattae.snl.plugins.flowable.content.engine.impl.MetadataServiceImpl;
import com.mattae.snl.plugins.flowable.content.engine.impl.RenditionItemContentObjectStorageMetadata;
import com.mattae.snl.plugins.flowable.content.engine.impl.RenditionServiceImpl;
import com.mattae.snl.plugins.flowable.content.engine.impl.cmd.SchemaOperationsContentEngineBuild;
import com.mattae.snl.plugins.flowable.content.engine.impl.db.ContentDbSchemaManager;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.*;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.data.*;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.data.impl.*;
import com.mattae.snl.plugins.flowable.content.engine.impl.repository.DocumentRepositoryServiceImpl;
import com.mattae.snl.plugins.flowable.content.rendition.DefaultContentRenditionManager;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.impl.EngineDeployer;
import org.flowable.common.engine.impl.persistence.deploy.DefaultDeploymentCache;
import org.flowable.common.engine.impl.persistence.deploy.DeploymentCache;
import org.flowable.common.engine.impl.persistence.entity.TableDataManager;
import org.flowable.content.api.ContentManagementService;
import org.flowable.content.engine.impl.ContentManagementServiceImpl;
import org.flowable.variable.api.types.VariableTypes;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class ContentEngineConfiguration extends org.flowable.content.engine.ContentEngineConfiguration {

    protected String contentEngineName = "default";

    protected ContentManagementService contentManagementService = new ContentManagementServiceImpl(this);

    protected DocumentRepositoryService documentRepositoryService = new DocumentRepositoryServiceImpl(this);


    protected RenditionService renditionService = new RenditionServiceImpl();

    protected MetadataService metadataService = new MetadataServiceImpl();

    protected ContentRenditionManager contentRenditionManager;

    protected DocumentDeploymentDataManager documentDeploymentDataManager;

    protected DocumentDefinitionDataManager documentDefinitionDataManager;

    protected DocumentResourceDataManager documentResourceDataManager;

    protected RenditionItemDataManager renditionItemDataManager;

    protected MetadataInstanceDataManager metadataInstanceDataManager;

    protected DocumentDeploymentEntityManager documentDeploymentEntityManager;

    protected DocumentDefinitionEntityManager documentDefinitionEntityManager;

    protected DocumentResourceEntityManager documentResourceEntityManager;

    protected RenditionItemEntityManager renditionItemEntityManager;

    protected MetadataInstanceEntityManager metadataInstanceEntityManager;

    protected DocumentDefinitionDeployer documentDefinitionDeployer;

    protected DocumentDeploymentManager deploymentManager;

    protected DocumentResourceConverter documentResourceConverter;
    protected int documentDefinitionCacheLimit = -1;

    protected DeploymentCache<DocumentDefinitionCacheEntry> documentDefinitionCache;

    protected List<RenditionConverter> renditionConverters = new ArrayList<>();

    protected VariableTypes variableTypes;

    protected RenditionItemContentObjectStorageMetadataProvider renditionItemContentObjectStorageMetadataProvider = RenditionItemContentObjectStorageMetadata::new;


    protected void init() {
        super.init();
        initDeployers();
        initDocumentDefinitionCache();
        initDocumentResourceConverter();
        initDeploymentManager();
        initEventDispatcher();
        initContentRenditionManager();
    }

    protected void initServices() {
        contentService = new ContentServiceImpl();
        initService(this.contentManagementService);
        initService(this.documentRepositoryService);
        initService(this.contentService);
        initService(this.renditionService);
        initService(this.metadataService);
    }

    @Override
    public void initSchemaManager() {
        if (this.schemaManager == null) {
            this.schemaManager = new ContentDbSchemaManager();
        }
        super.initSchemaManager();
    }

    protected void initDeployers() {
        if (this.documentDefinitionDeployer == null) {
            this.deployers = new ArrayList<>();
            if (this.customPreDeployers != null)
                this.deployers.addAll(this.customPreDeployers);
            this.deployers.addAll(getDefaultDeployers());
            if (this.customPostDeployers != null)
                this.deployers.addAll(this.customPostDeployers);
        }
    }

    public void initDataManagers() {
        super.initDataManagers();
        if (this.documentDeploymentDataManager == null)
            this.documentDeploymentDataManager = new MybatisDocumentDeploymentDataManager(this);
        if (this.documentDefinitionDataManager == null)
            this.documentDefinitionDataManager = new MybatisDocumentDefinitionDataManager(this);
        if (this.documentResourceDataManager == null)
            this.documentResourceDataManager = new MybatisDocumentResourceDataManager(this);

        if (this.renditionItemDataManager == null)
            this.renditionItemDataManager = new MybatisRenditionItemDataManager(this);
        if (this.metadataInstanceDataManager == null)
            this.metadataInstanceDataManager = new MybatisMetadataInstanceDataManager(this);
    }

    public void initEntityManagers() {
        super.initEntityManagers();
        if (this.documentDeploymentEntityManager == null)
            this.documentDeploymentEntityManager = new DocumentDeploymentEntityManagerImpl(this, this.documentDeploymentDataManager);
        if (this.documentDefinitionEntityManager == null)
            this.documentDefinitionEntityManager = new DocumentDefinitionEntityManagerImpl(this, this.documentDefinitionDataManager);
        if (this.documentResourceEntityManager == null)
            this.documentResourceEntityManager = new DocumentResourceEntityManagerImpl(this, this.documentResourceDataManager);

        if (this.renditionItemEntityManager == null)
            this.renditionItemEntityManager = new RenditionItemEntityManagerImpl(this, this.renditionItemDataManager);
        if (this.metadataInstanceEntityManager == null)
            this.metadataInstanceEntityManager = new MetadataInstanceEntityManagerImpl(this, this.metadataInstanceDataManager);

    }

    public void initCommandExecutors() {
        initDefaultCommandConfig();
        initSchemaCommandConfig();
        initCommandInvoker();
        initCommandInterceptors();
        initCommandExecutor();
    }

    public InputStream getMyBatisXmlConfigurationStream() {
        return getResourceAsStream("installers/flowable/content/db/mapping/mappings.xml");
    }

    public void initSchemaManagementCommand() {
        if (this.schemaManagementCmd == null && this.usingRelationalDatabase && this.databaseSchemaUpdate != null) {
            this.schemaManagementCmd = new SchemaOperationsContentEngineBuild();
        }

    }

    public Collection<? extends EngineDeployer> getDefaultDeployers() {
        List<EngineDeployer> defaultDeployers = new ArrayList<>();
        if (this.documentDefinitionDeployer == null)
            this.documentDefinitionDeployer = new DocumentDefinitionDeployer();
        defaultDeployers.add(this.documentDefinitionDeployer);
        return defaultDeployers;
    }

    protected void initDocumentDefinitionCache() {
        if (this.documentDefinitionCache == null)
            if (this.documentDefinitionCacheLimit <= 0) {
                this.documentDefinitionCache = new DefaultDeploymentCache<>();
            } else {
                this.documentDefinitionCache = new DefaultDeploymentCache<>(this.documentDefinitionCacheLimit);
            }
    }

    protected void initDocumentResourceConverter() {
        if (this.documentResourceConverter == null)
            this.documentResourceConverter = new DocumentResourceConverterImpl(this.objectMapper);
    }

    protected void initDeploymentManager() {
        if (this.deploymentManager == null) {
            this.deploymentManager = new DocumentDeploymentManager();
            this.deploymentManager.setContentEngineConfiguration(this);
            this.deploymentManager.setDocumentDefinitionCache(this.documentDefinitionCache);
            this.deploymentManager.setDeployers(this.deployers);
            this.deploymentManager.setDocumentDefinitionEntityManager(this.documentDefinitionEntityManager);
            this.deploymentManager.setDeploymentEntityManager(this.documentDeploymentEntityManager);
            this.deploymentManager.setResourceEntityManager(this.documentResourceEntityManager);
        }
    }

    public void initContentRenditionManager() {
        if (this.contentRenditionManager == null)
            this.contentRenditionManager = new DefaultContentRenditionManager(this.renditionConverters);
    }

    public DocumentRepositoryService getDocumentRepositoryService() {
        return this.documentRepositoryService;
    }

    public ContentEngineConfiguration setDocumentRepositoryService(DocumentRepositoryService documentRepositoryService) {
        this.documentRepositoryService = documentRepositoryService;
        return this;
    }

    public RenditionService getRenditionService() {
        return this.renditionService;
    }

    public ContentEngineConfiguration setRenditionService(RenditionService renditionService) {
        this.renditionService = renditionService;
        return this;
    }

    public RenditionItemContentObjectStorageMetadataProvider getRenditionItemContentObjectStorageMetadataProvider() {
        return this.renditionItemContentObjectStorageMetadataProvider;
    }

    public MetadataService getMetadataService() {
        return this.metadataService;
    }

    public ContentEngineConfiguration setMetadataService(MetadataService metadataService) {
        this.metadataService = metadataService;
        return this;
    }

    public ContentEngineConfiguration getContentEngineConfiguration() {
        return this;
    }

    public DocumentDeploymentDataManager getDocumentDeploymentDataManager() {
        return this.documentDeploymentDataManager;
    }

    public ContentEngineConfiguration setDocumentDeploymentDataManager(DocumentDeploymentDataManager documentDeploymentDataManager) {
        this.documentDeploymentDataManager = documentDeploymentDataManager;
        return this;
    }

    public DocumentDefinitionDataManager getDocumentDefinitionDataManager() {
        return this.documentDefinitionDataManager;
    }

    public ContentEngineConfiguration setDocumentDefinitionDataManager(DocumentDefinitionDataManager documentDefinitionDataManager) {
        this.documentDefinitionDataManager = documentDefinitionDataManager;
        return this;
    }

    public DocumentResourceDataManager getDocumentResourceDataManager() {
        return this.documentResourceDataManager;
    }

    public ContentEngineConfiguration setDocumentResourceDataManager(DocumentResourceDataManager documentResourceDataManager) {
        this.documentResourceDataManager = documentResourceDataManager;
        return this;
    }

    public RenditionItemDataManager getRenditionItemDataManager() {
        return this.renditionItemDataManager;
    }

    public ContentEngineConfiguration setRenditionItemDataManager(RenditionItemDataManager renditionItemDataManager) {
        this.renditionItemDataManager = renditionItemDataManager;
        return this;
    }

    public MetadataInstanceDataManager getMetadataInstanceDataManager() {
        return this.metadataInstanceDataManager;
    }

    public ContentEngineConfiguration setMetadataInstanceDataManager(MetadataInstanceDataManager metadataInstanceDataManager) {
        this.metadataInstanceDataManager = metadataInstanceDataManager;
        return this;
    }

    public DocumentDeploymentEntityManager getDocumentDeploymentEntityManager() {
        return this.documentDeploymentEntityManager;
    }

    public ContentEngineConfiguration setDocumentDeploymentEntityManager(DocumentDeploymentEntityManager documentDeploymentEntityManager) {
        this.documentDeploymentEntityManager = documentDeploymentEntityManager;
        return this;
    }

    public DocumentDefinitionEntityManager getDocumentDefinitionEntityManager() {
        return this.documentDefinitionEntityManager;
    }

    public ContentEngineConfiguration setDocumentDefinitionEntityManager(DocumentDefinitionEntityManager documentDefinitionEntityManager) {
        this.documentDefinitionEntityManager = documentDefinitionEntityManager;
        return this;
    }

    public DocumentResourceEntityManager getDocumentResourceEntityManager() {
        return this.documentResourceEntityManager;
    }

    public ContentEngineConfiguration setDocumentResourceEntityManager(DocumentResourceEntityManager documentResourceEntityManager) {
        this.documentResourceEntityManager = documentResourceEntityManager;
        return this;
    }

    public RenditionItemEntityManager getRenditionItemEntityManager() {
        return this.renditionItemEntityManager;
    }

    public ContentEngineConfiguration setRenditionItemEntityManager(RenditionItemEntityManager renditionItemEntityManager) {
        this.renditionItemEntityManager = renditionItemEntityManager;
        return this;
    }

    public MetadataInstanceEntityManager getMetadataInstanceEntityManager() {
        return this.metadataInstanceEntityManager;
    }

    public ContentEngineConfiguration setMetadataInstanceEntityManager(MetadataInstanceEntityManager metadataInstanceEntityManager) {
        this.metadataInstanceEntityManager = metadataInstanceEntityManager;
        return this;
    }

    public ContentEngineConfiguration setTableDataManager(TableDataManager tableDataManager) {
        this.tableDataManager = tableDataManager;
        return this;
    }


    public String getContentRootFolder() {
        return this.contentRootFolder;
    }

    public ContentEngineConfiguration setContentRootFolder(String contentRootFolder) {
        this.contentRootFolder = contentRootFolder;
        return this;
    }

    public ContentRenditionManager getContentRenditionManager() {
        return this.contentRenditionManager;
    }

    public void setContentRenditionManager(ContentRenditionManager contentRenditionManager) {
        this.contentRenditionManager = contentRenditionManager;
    }

    public List<RenditionConverter> getRenditionConverters() {
        return this.renditionConverters;
    }

    public DocumentDefinitionDeployer getDocumentDefinitionDeployer() {
        return this.documentDefinitionDeployer;
    }

    public ContentEngineConfiguration setDocumentDefinitionDeployer(DocumentDefinitionDeployer documentDefinitionDeployer) {
        this.documentDefinitionDeployer = documentDefinitionDeployer;
        return this;
    }

    public DocumentDeploymentManager getDeploymentManager() {
        return this.deploymentManager;
    }

    public ContentEngineConfiguration setDeploymentManager(DocumentDeploymentManager deploymentManager) {
        this.deploymentManager = deploymentManager;
        return this;
    }

    public DocumentResourceConverter getDocumentResourceConverter() {
        return this.documentResourceConverter;
    }

    public ContentEngineConfiguration setDocumentResourceConverter(DocumentResourceConverter documentResourceConverter) {
        this.documentResourceConverter = documentResourceConverter;
        return this;
    }

    public int getDocumentDefinitionCacheLimit() {
        return this.documentDefinitionCacheLimit;
    }

    public ContentEngineConfiguration setDocumentDefinitionCacheLimit(int documentDefinitionCacheLimit) {
        this.documentDefinitionCacheLimit = documentDefinitionCacheLimit;
        return this;
    }

    public DeploymentCache<DocumentDefinitionCacheEntry> getDocumentDefinitionCache() {
        return this.documentDefinitionCache;
    }

    public ContentEngineConfiguration setDocumentDefinitionCache(DeploymentCache<DocumentDefinitionCacheEntry> documentDefinitionCache) {
        this.documentDefinitionCache = documentDefinitionCache;
        return this;
    }

    public VariableTypes getVariableTypes() {
        return this.variableTypes;
    }
}
