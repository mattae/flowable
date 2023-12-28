package com.mattae.snl.plugins.flowable.content.api;

import java.util.Date;

public interface RenditionItem {
    String getId();

    String getContentItemId();

    void setContentItemId(String paramString);

    String getContentItemName();

    String getName();

    void setName(String paramString);

    String getMimeType();

    void setMimeType(String paramString);

    String getTaskId();

    void setTaskId(String paramString);

    String getProcessInstanceId();

    void setProcessInstanceId(String paramString);

    String getScopeId();

    void setScopeId(String paramString);

    String getScopeType();

    void setScopeType(String paramString);

    String getContentStoreId();

    void setContentStoreId(String paramString);

    String getContentStoreName();

    void setContentStoreName(String paramString);

    boolean isContentAvailable();

    Long getContentSize();

    String getTenantId();

    void setTenantId(String paramString);

    Date getCreated();

    Date getLastModified();

    boolean isProvisional();

    void setProvisional(boolean paramBoolean);

    String getRenditionType();

    void setRenditionType(String paramString);
}
