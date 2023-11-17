package com.mattae.snl.plugins.flowable.content.rendition;

import org.apache.commons.lang3.StringUtils;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;

import java.io.IOException;
import java.io.InputStream;

public class MetaDataUtil {

    private static TikaConfig tikaConfig;

    public static MediaType determineMetaDataType(String name, InputStream stream) throws IOException {
        TikaConfig config = getTikaConfig();
        Detector detector = config.getDetector();
        Metadata metadata = new Metadata();
        if (StringUtils.isNotEmpty(name))
            metadata.add("resourceName", name);
        return detector.detect(stream, metadata);
    }

    private static TikaConfig getTikaConfig() {
        if (tikaConfig != null)
            return tikaConfig;
        tikaConfig = TikaConfig.getDefaultConfig();
        return tikaConfig;
    }
}
