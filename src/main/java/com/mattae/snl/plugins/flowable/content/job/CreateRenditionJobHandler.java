package com.mattae.snl.plugins.flowable.content.job;

import com.mattae.snl.plugins.flowable.content.engine.impl.RenditionItemHelper;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.RenditionItemEntity;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.RenditionItemEntityManager;
import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.job.service.JobHandler;
import org.flowable.job.service.impl.persistence.entity.JobEntity;
import org.flowable.variable.api.delegate.VariableScope;

@Slf4j
public class CreateRenditionJobHandler implements JobHandler {

    public String getType() {
        return "create-rendition";
    }

    public void execute(JobEntity job, String configuration, VariableScope variableScope, CommandContext commandContext) {
        String scopeId = job.getScopeId();
        RenditionItemEntityManager renditionItemEntityManager = CommandContextUtil.getRenditionItemEntityManager(commandContext);
        RenditionItemEntity renditionItem = renditionItemEntityManager.findById(scopeId);
        if (renditionItem == null) {
            LOG.info("Rendition item could not be found to create renditions with id {}", scopeId);
            return;
        }
        RenditionItemHelper.createContentRenditions(renditionItem);
    }
}
