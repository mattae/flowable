package com.mattae.snl.plugins.flowable.services;

import com.mattae.snl.plugins.flowable.content.engine.impl.ContentItemQueryImpl;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.content.api.ContentService;
import org.flowable.engine.IdentityService;
import org.flowable.idm.api.User;
import org.flowable.ui.common.service.idm.cache.UserCache;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCacheService implements UserCache {
    private final IdentityService identityService;
    private final ContentService contentService;

    @Override
    @Transactional
    public CachedUser getUser(String userId) {
        User user = identityService.createUserQuery().userId(userId).singleResult();
        if (user == null) {
            throw new UsernameNotFoundException("User " + userId + " was not found in the database");
        }

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        return new CachedUser(user, grantedAuthorities);
    }

    @Override
    public CachedUser getUser(String userId, boolean throwExceptionOnNotFound, boolean throwExceptionOnInactive, boolean checkValidity) {
        return null;
    }

    @Override
    public void putUser(String userId, CachedUser cachedUser) {

    }

    @Override
    public void invalidate(String userId) {

    }

    @PostConstruct
    public void init() {
        LOG.info("ContentService: {}", contentService);
        LOG.info("Content: {}",((ContentItemQueryImpl) contentService.createContentItemQuery()).type("application/json").list());
    }
}
