package com.mattae.snl.plugins.flowable.web.runtime;

import com.mattae.snl.plugins.flowable.services.model.RenditionItemResponse;
import com.mattae.snl.plugins.flowable.web.runtime.model.RenditionItemQueryRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.flowable.common.rest.api.DataResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RenditionItemQueryResource extends RenditionItemBaseResource {
    @PostMapping(value = {"/query/rendition-items"}, produces = {"application/json"})
    public DataResponse<RenditionItemResponse> getQueryResult(@RequestBody RenditionItemQueryRequest request, @RequestParam Map<String, String> requestParams, HttpServletRequest httpRequest) {
        return getRenditionItemsFromQueryRequest(request, requestParams);
    }
}
