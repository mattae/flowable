package com.mattae.snl.plugins.flowable.content.api;

import org.flowable.common.engine.api.query.Query;

import java.util.Date;
import java.util.Set;

public interface RenditionItemQuery extends Query<RenditionItemQuery, RenditionItem> {
    RenditionItemQuery id(String paramString);

    RenditionItemQuery ids(Set<String> paramSet);

    RenditionItemQuery contentItemId(String paramString);

    RenditionItemQuery contentItemIdLike(String paramString);

    RenditionItemQuery contentItemName(String paramString);

    RenditionItemQuery contentItemNameLike(String paramString);

    RenditionItemQuery name(String paramString);

    RenditionItemQuery nameLike(String paramString);

    RenditionItemQuery mimeType(String paramString);

    RenditionItemQuery mimeTypeLike(String paramString);

    RenditionItemQuery taskId(String paramString);

    RenditionItemQuery taskIdLike(String paramString);

    RenditionItemQuery scopeType(String paramString);

    RenditionItemQuery scopeTypeLike(String paramString);

    RenditionItemQuery scopeId(String paramString);

    RenditionItemQuery scopeIdLike(String paramString);

    RenditionItemQuery processInstanceId(String paramString);

    RenditionItemQuery processInstanceIdLike(String paramString);

    RenditionItemQuery contentStoreId(String paramString);

    RenditionItemQuery contentStoreIdLike(String paramString);

    RenditionItemQuery contentStoreName(String paramString);

    RenditionItemQuery contentStoreNameLike(String paramString);

    RenditionItemQuery contentAvailable(Boolean paramBoolean);

    RenditionItemQuery contentSize(Long paramLong);

    RenditionItemQuery minContentSize(Long paramLong);

    RenditionItemQuery maxContentSize(Long paramLong);

    RenditionItemQuery createdDate(Date paramDate);

    RenditionItemQuery createdDateBefore(Date paramDate);

    RenditionItemQuery createdDateAfter(Date paramDate);

    RenditionItemQuery lastModifiedDate(Date paramDate);

    RenditionItemQuery lastModifiedDateBefore(Date paramDate);

    RenditionItemQuery lastModifiedDateAfter(Date paramDate);

    RenditionItemQuery tenantId(String paramString);

    RenditionItemQuery tenantIdLike(String paramString);

    RenditionItemQuery withoutTenantId();

    RenditionItemQuery orderByCreatedDate();

    RenditionItemQuery orderByTenantId();

    RenditionItemQuery renditionType(String paramString);
}
