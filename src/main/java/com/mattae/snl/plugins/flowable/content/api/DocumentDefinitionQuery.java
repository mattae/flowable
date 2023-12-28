package com.mattae.snl.plugins.flowable.content.api;

import org.flowable.common.engine.api.query.Query;

import java.util.Collection;
import java.util.Collections;

public interface DocumentDefinitionQuery extends Query<DocumentDefinitionQuery, DocumentDefinition> {
    DocumentDefinitionQuery id(String paramString);

    DocumentDefinitionQuery ids(Collection<String> paramCollection);

    DocumentDefinitionQuery key(String paramString);

    DocumentDefinitionQuery keyLike(String paramString);

    DocumentDefinitionQuery keys(Collection<String> paramCollection);

    DocumentDefinitionQuery name(String paramString);

    DocumentDefinitionQuery nameLike(String paramString);

    DocumentDefinitionQuery nameLikeIgnoreCase(String paramString);

    DocumentDefinitionQuery category(String paramString);

    DocumentDefinitionQuery categoryLike(String paramString);

    DocumentDefinitionQuery version(Integer paramInteger);

    DocumentDefinitionQuery latestVersion();

    DocumentDefinitionQuery anyVersion();

    DocumentDefinitionQuery deploymentId(String paramString);

    default DocumentDefinitionQuery accessibleByUser(String userId) {
        return accessibleByUserOrGroups(userId, Collections.emptySet());
    }

    DocumentDefinitionQuery accessibleByUserOrGroups(String paramString, Collection<String> paramCollection);

    DocumentDefinitionQuery tenantId(String paramString);

    DocumentDefinitionQuery tenantIdLike(String paramString);
}
