package com.mattae.snl.plugins.flowable.content.api;

import java.io.InputStream;

public interface RenditionService {
    RenditionItem newRenditionItem();

    void saveRenditionItem(RenditionItem paramRenditionItem);

    void saveRenditionItem(RenditionItem paramRenditionItem, InputStream paramInputStream);

    InputStream getRenditionItemData(String paramString);

    void deleteRenditionItem(String paramString);

    void deleteRenditionItemsByProcessInstanceId(String paramString);

    void deleteRenditionItemsByTaskId(String paramString);

    void deleteRenditionItemsByScopeIdAndScopeType(String paramString1, String paramString2);

    RenditionItemQuery createRenditionItemQuery();
}
