package com.mattae.snl.plugins.flowable.content.api;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

public interface DocumentDeploymentBuilder {
    DocumentDeploymentBuilder addInputStream(String paramString, InputStream paramInputStream);

    DocumentDeploymentBuilder addClasspathResource(String paramString);

    DocumentDeploymentBuilder addString(String paramString1, String paramString2);

    DocumentDeploymentBuilder addBytes(String paramString, byte[] paramArrayOfbyte);

    DocumentDeploymentBuilder addZipInputStream(ZipInputStream paramZipInputStream);

    DocumentDeploymentBuilder name(String paramString);

    DocumentDeploymentBuilder category(String paramString);

    DocumentDeploymentBuilder key(String paramString);

    DocumentDeploymentBuilder parentDeploymentId(String paramString);

    DocumentDeploymentBuilder tenantId(String paramString);

    DocumentDeployment deploy();
}
