package com.mattae.snl.plugins.flowable.web.runtime;

import com.mattae.snl.plugins.flowable.content.api.RenditionItem;
import com.mattae.snl.plugins.flowable.services.model.RenditionItemResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.InputStream;

@RequiredArgsConstructor
public class RenditionItemDataResource extends RenditionItemBaseResource {
    protected final ContentRestResponseFactory contentRestResponseFactory;

    protected final ResponseEntityHelper responseEntityHelper;

    @GetMapping({"/renditions/rendition-items/{renditionItemId}/data"})
    public ResponseEntity<Resource> getRenditionItemData(@PathVariable("renditionItemId") String renditionItemId, @RequestParam(required = false) Boolean download) {
        RenditionItem renditionItem = getRenditionItemFromRequest(renditionItemId);
        if (!renditionItem.isContentAvailable())
            throw new FlowableObjectNotFoundException("No data available for content item " + renditionItemId);
        if (this.restApiInterceptor != null)
            this.restApiInterceptor.accessRenditionItemInfoById(renditionItem);
        InputStream dataStream = this.renditionService.getRenditionItemData(renditionItemId);
        if (dataStream == null)
            throw new FlowableObjectNotFoundException("Rendition item with id '" + renditionItemId + "' doesn't have content associated with it.");
        return this.responseEntityHelper.asByteArrayResponseEntity(dataStream, renditionItem.getMimeType(), renditionItem.getName(), download);
    }

    @PostMapping(value = {"/content-service/rendition-items/{renditionItemId}/data"}, produces = {"application/json"}, consumes = {"multipart/form-data"})
    public ResponseEntity<RenditionItemResponse> saveContentItemData(@PathVariable("renditionItemId") String renditionItemId, HttpServletRequest request) {
        if (!(request instanceof MultipartHttpServletRequest multipartRequest))
            throw new FlowableIllegalArgumentException("Multipart request required to save rendition item data");
        RenditionItem renditionItem = getRenditionItemFromRequest(renditionItemId);
        MultipartFile file = multipartRequest.getFileMap().values().iterator().next();
        if (file == null)
            throw new FlowableIllegalArgumentException("Rendition item file is required.");
        try {
            this.renditionService.saveRenditionItem(renditionItem, file.getInputStream());
            return new ResponseEntity<>(this.contentRestResponseFactory.createRenditionItemResponse(renditionItem), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new FlowableException("Error creating rendition item response", e);
        }
    }
}
