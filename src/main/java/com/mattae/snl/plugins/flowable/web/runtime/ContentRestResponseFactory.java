package com.mattae.snl.plugins.flowable.web.runtime;

import com.mattae.snl.plugins.flowable.content.api.DocumentDefinition;
import com.mattae.snl.plugins.flowable.content.api.DocumentDeployment;
import com.mattae.snl.plugins.flowable.content.api.RenditionItem;
import com.mattae.snl.plugins.flowable.services.model.DeploymentResourceResponse;
import com.mattae.snl.plugins.flowable.services.model.DocumentDefinitionResponse;
import com.mattae.snl.plugins.flowable.services.model.DocumentDeploymentResponse;
import com.mattae.snl.plugins.flowable.services.model.RenditionItemResponse;
import com.mattae.snl.plugins.flowable.web.runtime.model.ContentRestUrlBuilder;
import com.mattae.snl.plugins.flowable.web.runtime.model.ContentRestUrls;
import com.mattae.snl.plugins.flowable.web.runtime.model.QueryMetadata;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.rest.resolver.ContentTypeResolver;
import org.flowable.common.rest.variable.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ContentRestResponseFactory {
    protected List<RestVariableConverter> variableConverters = new ArrayList<>();

    public ContentRestResponseFactory() {
        initializeVariableConverters();
    }

    public List<DocumentDeploymentResponse> createDeploymentResponseList(List<DocumentDeployment> deployments) {
        ContentRestUrlBuilder urlBuilder = createUrlBuilder();
        List<DocumentDeploymentResponse> responseList = new ArrayList<>(deployments.size());
        for (DocumentDeployment instance : deployments)
            responseList.add(createDeploymentResponse(instance, urlBuilder));
        return responseList;
    }

    public DocumentDeploymentResponse createDeploymentResponse(DocumentDeployment deployment) {
        return createDeploymentResponse(deployment, createUrlBuilder());
    }

    public DocumentDeploymentResponse createDeploymentResponse(DocumentDeployment deployment, ContentRestUrlBuilder urlBuilder) {
        return new DocumentDeploymentResponse(deployment, urlBuilder.buildUrl(ContentRestUrls.URL_DEPLOYMENT, new Object[]{deployment.getId()}));
    }

    public List<DeploymentResourceResponse> createDeploymentResourceResponseList(String deploymentId, List<String> resourceList, ContentTypeResolver contentTypeResolver) {
        ContentRestUrlBuilder urlBuilder = createUrlBuilder();
        List<DeploymentResourceResponse> responseList = new ArrayList<>(resourceList.size());
        for (String resourceId : resourceList) {
            String contentType = null;
            if (resourceId.toLowerCase().endsWith(".document")) {
                contentType = "application/json";
            } else {
                contentType = contentTypeResolver.resolveContentType(resourceId);
            }
            responseList.add(createDeploymentResourceResponse(deploymentId, resourceId, contentType, urlBuilder));
        }
        return responseList;
    }

    public DeploymentResourceResponse createDeploymentResourceResponse(String deploymentId, String resourceId, String contentType) {
        return createDeploymentResourceResponse(deploymentId, resourceId, contentType, createUrlBuilder());
    }

    public DeploymentResourceResponse createDeploymentResourceResponse(String deploymentId, String resourceId, String contentType, ContentRestUrlBuilder urlBuilder) {
        String resourceUrl = urlBuilder.buildUrl(ContentRestUrls.URL_DEPLOYMENT_RESOURCE, new Object[]{deploymentId, resourceId});
        String resourceContentUrl = urlBuilder.buildUrl(ContentRestUrls.URL_DEPLOYMENT_RESOURCE_CONTENT, new Object[]{deploymentId, resourceId});
        String type = "resource";
        if (resourceId.endsWith(".document"))
            type = "documentDefinition";
        return new DeploymentResourceResponse(resourceId, resourceUrl, resourceContentUrl, contentType, type);
    }

    public List<DocumentDefinitionResponse> createDocumentDefinitionResponseList(List<DocumentDefinition> documentDefinitions) {
        ContentRestUrlBuilder urlBuilder = createUrlBuilder();
        List<DocumentDefinitionResponse> responseList = new ArrayList<>(documentDefinitions.size());
        for (DocumentDefinition definition : documentDefinitions)
            responseList.add(createDocumentDefinitionResponse(definition, urlBuilder));
        return responseList;
    }

    public DocumentDefinitionResponse createDocumentDefinitionResponse(DocumentDefinition documentDefinition) {
        return createDocumentDefinitionResponse(documentDefinition, createUrlBuilder());
    }

    public DocumentDefinitionResponse createDocumentDefinitionResponse(DocumentDefinition documentDefinition, ContentRestUrlBuilder urlBuilder) {
        DocumentDefinitionResponse response = new DocumentDefinitionResponse();
        response.setUrl(urlBuilder.buildUrl(ContentRestUrls.URL_DOCUMENT_DEFINITION, documentDefinition.getId()));
        response.setId(documentDefinition.getId());
        response.setKey(documentDefinition.getKey());
        response.setVersion(documentDefinition.getVersion());
        response.setName(documentDefinition.getName());
        response.setTenantId(documentDefinition.getTenantId());
        response.setDeploymentId(documentDefinition.getDeploymentId());
        response.setDeploymentUrl(urlBuilder.buildUrl(ContentRestUrls.URL_DEPLOYMENT, new Object[]{documentDefinition.getDeploymentId()}));
        response.setResource(urlBuilder.buildUrl(ContentRestUrls.URL_DEPLOYMENT_RESOURCE, new Object[]{documentDefinition.getDeploymentId(), documentDefinition.getResourceName()}));
        return response;
    }

    public RenditionItemResponse createRenditionItemResponse(RenditionItem renditionItem) {
        return createRenditionItemResponse(renditionItem, createUrlBuilder());
    }

    public RenditionItemResponse createRenditionItemResponse(RenditionItem renditionItem, ContentRestUrlBuilder urlBuilder) {
        return new RenditionItemResponse(renditionItem, urlBuilder
            .buildUrl(ContentRestUrls.URL_RENDITION_ITEM, new Object[]{renditionItem.getId()}));
    }

    public List<RenditionItemResponse> createRenditionItemResponseList(List<RenditionItem> renditionItems) {
        ContentRestUrlBuilder urlBuilder = createUrlBuilder();
        List<RenditionItemResponse> responseList = new ArrayList<>(renditionItems.size());
        for (RenditionItem renditionItem : renditionItems)
            responseList.add(createRenditionItemResponse(renditionItem, urlBuilder));
        return responseList;
    }

    public List<EngineRestVariable> createRestMetadataValues(Map<String, Object> variables, String id) {
        ContentRestUrlBuilder urlBuilder = createUrlBuilder();
        List<EngineRestVariable> result = new ArrayList<>(variables.size());
        for (Map.Entry<String, Object> pair : variables.entrySet())
            result.add(createRestMetadata(pair.getKey(), pair.getValue(), id, urlBuilder));
        return result;
    }

    public EngineRestVariable createRestMetadata(String name, Object value, String id) {
        return createRestMetadata(name, value, id, createUrlBuilder());
    }

    public EngineRestVariable createRestMetadata(String name, Object value, String id, ContentRestUrlBuilder urlBuilder) {
        RestVariableConverter converter = null;
        EngineRestVariable restVar = new EngineRestVariable();
        restVar.setName(name);
        if (value != null) {
            for (RestVariableConverter c : this.variableConverters) {
                if (c.getVariableType().isAssignableFrom(value.getClass())) {
                    converter = c;
                    break;
                }
            }
            if (converter != null) {
                converter.convertVariableValue(value, restVar);
                restVar.setType(converter.getRestTypeName());
            } else {
                restVar.setValue(value);
            }
        }
        return restVar;
    }

    public Object getMetadataValue(QueryMetadata metadata) {
        Object value = null;
        if (metadata.getType() != null) {
            RestVariableConverter converter = null;
            for (RestVariableConverter conv : this.variableConverters) {
                if (conv.getRestTypeName().equals(metadata.getType())) {
                    converter = conv;
                    break;
                }
            }
            if (converter == null)
                throw new FlowableIllegalArgumentException("Metadata '" + metadata.getName() + "' has unsupported type: '" + metadata.getType() + "'.");
            value = converter.getVariableValue(convertMetaDataToRestVariable(metadata));
        } else {
            value = metadata.getValue();
        }
        return value;
    }

    protected EngineRestVariable convertMetaDataToRestVariable(QueryMetadata metadata) {
        EngineRestVariable result = new EngineRestVariable();
        result.setName(metadata.getName());
        result.setType(metadata.getType());
        result.setValue(metadata.getValue());
        return result;
    }

    public Object getMetadataValue(EngineRestVariable restVariable) {
        Object value = null;
        if (restVariable.getType() != null) {
            RestVariableConverter converter = null;
            for (RestVariableConverter conv : this.variableConverters) {
                if (conv.getRestTypeName().equals(restVariable.getType())) {
                    converter = conv;
                    break;
                }
            }
            if (converter == null)
                throw new FlowableIllegalArgumentException("Variable '" + restVariable.getName() + "' has unsupported type: '" + restVariable.getType() + "'.");
            value = converter.getVariableValue(restVariable);
        } else {
            value = restVariable.getValue();
        }
        return value;
    }

    public List<RestVariableConverter> getVariableConverters() {
        return this.variableConverters;
    }

    protected void initializeVariableConverters() {
        this.variableConverters.add(new StringRestVariableConverter());
        this.variableConverters.add(new IntegerRestVariableConverter());
        this.variableConverters.add(new LongRestVariableConverter());
        this.variableConverters.add(new ShortRestVariableConverter());
        this.variableConverters.add(new DoubleRestVariableConverter());
        this.variableConverters.add(new BooleanRestVariableConverter());
        this.variableConverters.add(new DateRestVariableConverter());
        this.variableConverters.add(new InstantRestVariableConverter());
        this.variableConverters.add(new LocalDateRestVariableConverter());
        this.variableConverters.add(new LocalDateTimeRestVariableConverter());
    }

    protected ContentRestUrlBuilder createUrlBuilder() {
        return ContentRestUrlBuilder.fromCurrentRequest();
    }
}
