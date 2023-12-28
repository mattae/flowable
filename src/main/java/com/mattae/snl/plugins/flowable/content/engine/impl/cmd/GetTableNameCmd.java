package com.mattae.snl.plugins.flowable.content.engine.impl.cmd;

import com.mattae.snl.plugins.flowable.content.engine.impl.db.EntityToTableMap;
import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;

import java.io.Serializable;

public class GetTableNameCmd implements Command<String>, Serializable {
    private static final long serialVersionUID = 1L;

    private Class<?> entityClass;

    public GetTableNameCmd(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public String execute(CommandContext commandContext) {
        if (this.entityClass == null)
            throw new FlowableIllegalArgumentException("entityClass is null");
        String databaseTablePrefix = CommandContextUtil.getDbSqlSession(commandContext).getDbSqlSessionFactory().getDatabaseTablePrefix();
        String tableName = EntityToTableMap.getTableName(this.entityClass);
        if (tableName == null)
            return null;
        return databaseTablePrefix + tableName;
    }
}
