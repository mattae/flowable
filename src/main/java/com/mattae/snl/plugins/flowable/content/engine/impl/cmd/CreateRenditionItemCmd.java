package com.mattae.snl.plugins.flowable.content.engine.impl.cmd;

import com.mattae.snl.plugins.flowable.content.api.RenditionItem;
import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;

import java.io.Serializable;

public class CreateRenditionItemCmd implements Command<RenditionItem>, Serializable {
    private static final long serialVersionUID = 1L;

    public RenditionItem execute(CommandContext commandContext) {
        return CommandContextUtil.getRenditionItemEntityManager().create();
    }
}
