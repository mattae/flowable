package com.mattae.snl.plugins.flowable.common;

import org.apache.commons.lang3.StringUtils;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.impl.javax.el.ELContext;
import org.flowable.common.engine.impl.javax.el.ELResolver;
import org.laxture.spring.util.ApplicationContextProvider;
import org.pf4j.PluginManager;
import org.springframework.context.ApplicationContext;

import java.beans.FeatureDescriptor;
import java.util.Iterator;

/**
 * @author Matthew Edor
 */
public class ApplicationContextElResolver extends ELResolver {

    protected ApplicationContext applicationContext;

    public ApplicationContextElResolver(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object getValue(ELContext context, Object base, Object property) {
        if (base == null) {
            ApplicationContext applicationContext;
            // according to javadoc, can only be a String
            String key = (String) property;

            PluginManager pluginManager = ApplicationContextProvider.getBean(PluginManager.class);
            if (StringUtils.contains(key, ":")) {
                String pluginId = key.substring(0, key.indexOf(':'));
                var bean = key.substring(key.indexOf(':', key.length() - 1));
                applicationContext = ApplicationContextProvider
                    .getApplicationContext(pluginManager.getPlugin(pluginId).getPluginClassLoader());

                if (applicationContext.containsBean(bean)) {
                    context.setPropertyResolved(true);
                    return applicationContext.getBean(bean);
                }
            }
            for (var wrapper : pluginManager.getPlugins()) {
                applicationContext = ApplicationContextProvider.getApplicationContext(wrapper.getPluginClassLoader());
                if (applicationContext.containsBean(key)) {
                    context.setPropertyResolved(true);
                    return applicationContext.getBean(key);
                }
            }
        }

        return null;
    }

    @Override
    public boolean isReadOnly(ELContext context, Object base, Object property) {
        return true;
    }

    @Override
    public void setValue(ELContext context, Object base, Object property, Object value) {
        if (base == null) {
            String key = (String) property;
            if (applicationContext.containsBean(key)) {
                throw new FlowableException("Cannot set value of '" + property + "', it resolves to a bean defined in the Spring application-context.");
            }
        }
    }

    @Override
    public Class<?> getCommonPropertyType(ELContext context, Object arg) {
        return Object.class;
    }

    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object arg) {
        return null;
    }

    @Override
    public Class<?> getType(ELContext context, Object arg1, Object arg2) {
        return Object.class;
    }
}
