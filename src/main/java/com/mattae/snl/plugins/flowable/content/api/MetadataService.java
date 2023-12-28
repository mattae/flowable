package com.mattae.snl.plugins.flowable.content.api;

import java.util.Collection;
import java.util.Map;

public interface MetadataService {
    Object getMetadataValue(String paramString1, String paramString2);

    Map<String, Object> getMetadataValues(String paramString);

    MetadataInstance getMetadataInstance(String paramString1, String paramString2);

    Map<String, MetadataInstance> getMetadataInstances(String paramString);

    Map<String, MetadataInstance> getMetadataInstances(String paramString, Collection<String> paramCollection);

    void setMetadataValue(String paramString1, String paramString2, Object paramObject);

    void setMetadataValues(String paramString, Map<String, Object> paramMap);

    void removeMetadataValue(String paramString1, String paramString2);

    void removeMetadataValues(String paramString, Collection<String> paramCollection);
}
