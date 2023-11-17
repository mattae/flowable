//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mattae.snl.plugins.flowable.content.engine.impl;

import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.common.engine.impl.interceptor.CommandExecutor;

public class ContentItemQueryImpl extends org.flowable.content.engine.impl.ContentItemQueryImpl {
    private static final long serialVersionUID = 1L;
    protected String definitionId;
    protected String definitionName;
    protected String type;

    public ContentItemQueryImpl(CommandContext commandContext) {
        super(commandContext);
    }

    public ContentItemQueryImpl(CommandExecutor commandExecutor) {
        super(commandExecutor);
    }

    public ContentItemQueryImpl definitionId(String definitionId) {
        this.definitionId = definitionId;
        return this;
    }

    public ContentItemQueryImpl definitionName(String definitionName) {
        this.definitionName = definitionName;
        return this;
    }

    public ContentItemQueryImpl type(String type) {
        this.type = type;
        return this;
    }
}
