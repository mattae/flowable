package com.mattae.snl.plugins.flowable.content.api;

import java.io.InputStream;
import java.util.List;

public interface DocumentRepositoryService {
    DocumentDeploymentBuilder createDeployment();

    List<String> getDeploymentResourceNames(String paramString);

    InputStream getResourceAsStream(String paramString1, String paramString2);

    DocumentDefinitionModel getDocumentDefinitionModel(String paramString);

    String convertDocumentDefinitionModelToJson(String paramString);

    DocumentDefinition getDocumentDefinition(String paramString);

    DocumentDefinition getDocumentDefinitionByKey(String paramString);

    DocumentDefinition getDocumentDefinitionByKeyAndTenantId(String paramString1, String paramString2);

    void setDocumentDefinitionCategory(String paramString1, String paramString2);

    void deleteDeployment(String paramString);

    DocumentDeploymentQuery createDeploymentQuery();

    DocumentDefinitionQuery createDocumentDefinitionQuery();
}
