package com.mattae.snl.plugins.flowable;

import com.blazebit.persistence.spring.data.webmvc.impl.BlazePersistenceWebConfiguration;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.flowable.ui.common.service.idm.cache.RemoteIdmUserCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication(exclude = BlazePersistenceWebConfiguration.class)
@ComponentScan(basePackageClasses = {FlowablePluginApp.class, RemoteIdmUserCache.class})
@EnableScheduling
@Slf4j
public class FlowablePluginApp {
    private final DataSource dataSource;
    @Value("${flowable.database-schema:public}")
    private String schema;

    public FlowablePluginApp(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static void main(String[] args) {
        SpringApplication.run(FlowablePluginApp.class, args);
    }

    @PostConstruct
    public void init() {
        if (!schema.equals("public")) {
            try (Connection connection = dataSource.getConnection()) {
                connection.createStatement().execute("CREATE SCHEMA IF NOT EXISTS " + schema);
            } catch (SQLException e) {
                LOG.error("Error creating schema {}", schema, e);
            }
        }
    }
}



