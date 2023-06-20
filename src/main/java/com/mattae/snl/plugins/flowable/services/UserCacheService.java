package com.mattae.snl.plugins.flowable.services;

import org.flowable.ui.common.properties.FlowableCommonAppProperties;
import org.flowable.ui.common.service.idm.cache.BaseUserCache;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

@Service
@Import(FlowableCommonAppProperties.class)
public class UserCacheService extends BaseUserCache {
    protected UserCacheService(FlowableCommonAppProperties properties) {
        super(properties);
    }

    @Override
    protected CachedUser loadUser(String userId) {
        return null;
    }
}
