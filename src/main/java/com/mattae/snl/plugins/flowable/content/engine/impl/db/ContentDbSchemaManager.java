package com.mattae.snl.plugins.flowable.content.engine.impl.db;

import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.impl.db.EngineDatabaseConfiguration;
import org.flowable.common.engine.impl.db.LiquibaseBasedSchemaManager;
import org.flowable.common.engine.impl.db.LiquibaseDatabaseConfiguration;
import org.flowable.common.engine.impl.db.SchemaManager;

public class ContentDbSchemaManager extends LiquibaseBasedSchemaManager {
    public static final String LIQUIBASE_CHANGELOG = "installers/flowable/content/db/liquibase/content-db-changelog.xml";

    public ContentDbSchemaManager() {
        super("content", LIQUIBASE_CHANGELOG, "ACT_CO_");
    }

    protected LiquibaseDatabaseConfiguration getDatabaseConfiguration() {
        return new EngineDatabaseConfiguration(CommandContextUtil.getContentEngineConfiguration());
    }

    public void initSchema() {
        initSchema(CommandContextUtil.getContentEngineConfiguration().getDatabaseSchemaUpdate());
    }

    public void schemaCreate() {
        this.getCommonSchemaManager().schemaCreate();
        super.schemaCreate();
    }

    public void schemaDrop() {
        try {
            super.schemaDrop();
        } catch (Exception var3) {
            this.logger.info("Error dropping content engine tables", var3);
        }

        try {
            this.getCommonSchemaManager().schemaDrop();
        } catch (Exception var2) {
            this.logger.info("Error dropping common tables", var2);
        }

    }

    public String schemaUpdate() {
        this.getCommonSchemaManager().schemaUpdate();
        return super.schemaUpdate();
    }

    protected SchemaManager getCommonSchemaManager() {
        return org.flowable.content.engine.impl.util.CommandContextUtil.getContentEngineConfiguration().getCommonSchemaManager();
    }
}
