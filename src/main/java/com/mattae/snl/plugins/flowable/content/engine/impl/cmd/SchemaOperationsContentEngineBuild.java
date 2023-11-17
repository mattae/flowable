package com.mattae.snl.plugins.flowable.content.engine.impl.cmd;

import com.mattae.snl.plugins.flowable.content.engine.impl.db.ContentDbSchemaManager;
import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.content.engine.ContentEngineConfiguration;

public class SchemaOperationsContentEngineBuild implements Command<Void> {
    public SchemaOperationsContentEngineBuild() {
    }

    public Void execute(CommandContext commandContext) {
        ContentEngineConfiguration configuration = CommandContextUtil.getContentEngineConfiguration(commandContext);
        ((ContentDbSchemaManager) configuration.getSchemaManager()).initSchema();
        return null;
    }
}
