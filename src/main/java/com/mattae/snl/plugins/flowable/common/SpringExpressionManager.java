package com.mattae.snl.plugins.flowable.common;

import org.flowable.common.engine.impl.el.DefaultExpressionManager;
import org.flowable.common.engine.impl.el.ReadOnlyMapELResolver;
import org.flowable.common.engine.impl.javax.el.*;
import org.springframework.context.ApplicationContext;

import java.util.Map;

public class SpringExpressionManager extends DefaultExpressionManager {

    protected ApplicationContext applicationContext;

    /**
     * @param applicationContext the applicationContext to use. Ignored when 'beans' parameter is not null.
     * @param beans              a map of custom beans to expose. If null, all beans in the application-context will be exposed.
     */

    public SpringExpressionManager(ApplicationContext applicationContext, Map<Object, Object> beans) {
        super(beans);
        this.applicationContext = applicationContext;
    }

    @Override
    protected ELResolver createVariableElResolver() {
        CompositeELResolver compositeElResolver = new CompositeELResolver();

        if (beans != null) {
            // Only expose limited set of beans in expressions
            compositeElResolver.add(new ReadOnlyMapELResolver(beans));
        } else {
            // Expose full application-context in expressions
            compositeElResolver.add(new ApplicationContextElResolver(applicationContext));
        }

        compositeElResolver.add(new ArrayELResolver());
        compositeElResolver.add(new ListELResolver());
        compositeElResolver.add(new MapELResolver());
        compositeElResolver.add(new BeanELResolver());
        return compositeElResolver;
    }

}
