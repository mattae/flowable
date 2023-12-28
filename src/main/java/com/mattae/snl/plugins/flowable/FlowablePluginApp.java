package com.mattae.snl.plugins.flowable;

import com.blazebit.persistence.spring.data.webmvc.impl.BlazePersistenceWebConfiguration;
import org.flowable.ui.common.service.idm.cache.RemoteIdmUserCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = BlazePersistenceWebConfiguration.class)
@ComponentScan(basePackageClasses = {FlowablePluginApp.class, RemoteIdmUserCache.class})
@EnableScheduling
public class FlowablePluginApp {

    public static void main(String[] args) {
        SpringApplication.run(FlowablePluginApp.class, args);
    }
}



