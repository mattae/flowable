package com.mattae.snl.plugins.flowable.content.engine.util;

import com.mattae.snl.plugins.flowable.content.api.ContentRenditionManager;
import com.mattae.snl.plugins.flowable.content.api.DocumentRepositoryService;
import com.mattae.snl.plugins.flowable.content.config.ContentEngineConfiguration;
import com.mattae.snl.plugins.flowable.content.deployer.DocumentDeploymentManager;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.*;
import org.flowable.common.engine.impl.context.Context;
import org.flowable.common.engine.impl.db.DbSqlSession;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.common.engine.impl.persistence.entity.TableDataManager;
import org.flowable.content.api.ContentService;
import org.flowable.content.engine.impl.persistence.entity.ContentItemEntityManager;
import org.flowable.identitylink.service.IdentityLinkServiceConfiguration;
import org.flowable.job.service.JobServiceConfiguration;

public class CommandContextUtil {
    public static ContentEngineConfiguration getContentEngineConfiguration() {
        return getContentEngineConfiguration(getCommandContext());
    }

    public static ContentEngineConfiguration getContentEngineConfiguration(CommandContext commandContext) {
        if (commandContext != null)
            return (ContentEngineConfiguration) commandContext.getEngineConfigurations().get("cfg.contentEngine");
        return null;
    }

    public static JobServiceConfiguration getJobServiceConfiguration() {
        return getJobServiceConfiguration(getCommandContext());
    }

    public static JobServiceConfiguration getJobServiceConfiguration(CommandContext commandContext) {
        if (commandContext != null)
            return (JobServiceConfiguration) getContentEngineConfiguration(commandContext).getServiceConfigurations().get("cfg.jobService");
        return null;
    }

    public static IdentityLinkServiceConfiguration getIdentityLinkServiceConfiguration() {
        return getIdentityLinkServiceConfiguration(getCommandContext());
    }

    public static IdentityLinkServiceConfiguration getIdentityLinkServiceConfiguration(CommandContext commandContext) {
        if (commandContext != null)
            return (IdentityLinkServiceConfiguration) getContentEngineConfiguration(commandContext).getServiceConfigurations().get("cfg.identityLinkService");
        return null;
    }

    public static DocumentRepositoryService getDocumentRepositoryService() {
        return getDocumentRepositoryService(getCommandContext());
    }

    public static DocumentRepositoryService getDocumentRepositoryService(CommandContext commandContext) {
        return getContentEngineConfiguration(commandContext).getDocumentRepositoryService();
    }

    public static DbSqlSession getDbSqlSession() {
        return getDbSqlSession(getCommandContext());
    }

    public static DbSqlSession getDbSqlSession(CommandContext commandContext) {
        return (DbSqlSession) commandContext.getSession(DbSqlSession.class);
    }

    public static DocumentDeploymentManager getDeploymentManager() {
        return getDeploymentManager(getCommandContext());
    }

    public static DocumentDeploymentManager getDeploymentManager(CommandContext commandContext) {
        return getContentEngineConfiguration(commandContext).getDeploymentManager();
    }

    public static TableDataManager getTableDataManager() {
        return getTableDataManager(getCommandContext());
    }

    public static TableDataManager getTableDataManager(CommandContext commandContext) {
        return getContentEngineConfiguration(commandContext).getTableDataManager();
    }

    public static ContentItemEntityManager getContentItemEntityManager() {
        return getContentItemEntityManager(getCommandContext());
    }

    public static ContentItemEntityManager getContentItemEntityManager(CommandContext commandContext) {
        return getContentEngineConfiguration(commandContext).getContentItemEntityManager();
    }

    public static ContentService getContentService() {
        ContentService contentService = null;
        ContentEngineConfiguration contentEngineConfiguration = getContentEngineConfiguration();
        if (contentEngineConfiguration != null)
            contentService = contentEngineConfiguration.getContentService();
        return contentService;
    }

    public static ContentRenditionManager getContentRenditionManager() {
        ContentRenditionManager contentRenditionManager = null;
        ContentEngineConfiguration contentEngineConfiguration = getContentEngineConfiguration();
        if (contentEngineConfiguration != null)
            contentRenditionManager = contentEngineConfiguration.getContentRenditionManager();
        return contentRenditionManager;
    }

    public static ContentRenditionManager getContentRenditionManager(CommandContext commandContext) {
        return getContentEngineConfiguration(commandContext).getContentRenditionManager();
    }

    public static DocumentDeploymentEntityManager getDocumentDeploymentEntityManager() {
        return getDocumentDeploymentEntityManager(getCommandContext());
    }

    public static DocumentDeploymentEntityManager getDocumentDeploymentEntityManager(CommandContext commandContext) {
        return getContentEngineConfiguration(commandContext).getDocumentDeploymentEntityManager();
    }

    public static DocumentDefinitionEntityManager getDocumentDefinitionEntityManager() {
        return getDocumentDefinitionEntityManager(getCommandContext());
    }

    public static DocumentDefinitionEntityManager getDocumentDefinitionEntityManager(CommandContext commandContext) {
        return getContentEngineConfiguration(commandContext).getDocumentDefinitionEntityManager();
    }

    public static DocumentResourceEntityManager getDocumentResourceEntityManager() {
        return getDocumentResourceEntityManager(getCommandContext());
    }

    public static DocumentResourceEntityManager getDocumentResourceEntityManager(CommandContext commandContext) {
        return getContentEngineConfiguration(commandContext).getDocumentResourceEntityManager();
    }

    public static RenditionItemEntityManager getRenditionItemEntityManager() {
        return getRenditionItemEntityManager(getCommandContext());
    }

    public static RenditionItemEntityManager getRenditionItemEntityManager(CommandContext commandContext) {
        return getContentEngineConfiguration(commandContext).getRenditionItemEntityManager();
    }

    public static MetadataInstanceEntityManager getMetadataInstanceEntityManager() {
        return getMetadataInstanceEntityManager(getCommandContext());
    }

    public static MetadataInstanceEntityManager getMetadataInstanceEntityManager(CommandContext commandContext) {
        return getContentEngineConfiguration(commandContext).getMetadataInstanceEntityManager();
    }

    public static CommandContext getCommandContext() {
        return Context.getCommandContext();
    }
}
