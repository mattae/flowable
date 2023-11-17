//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mattae.snl.plugins.flowable.config;

import org.flowable.spring.boot.FlowableServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(
    prefix = "flowable.content"
)
public class FlowableContentProperties {
    @NestedConfigurationProperty
    private final FlowableServlet servlet = new FlowableServlet("/content-api", "Flowable Content Rest API");
    @NestedConfigurationProperty
    private final Storage storage = new Storage();
    private boolean enabled = true;

    public FlowableContentProperties() {
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public FlowableServlet getServlet() {
        return this.servlet;
    }

    public Storage getStorage() {
        return this.storage;
    }

    public static class Storage {
        private String rootFolder;
        private boolean createRoot = true;

        public Storage() {
        }

        public String getRootFolder() {
            return this.rootFolder;
        }

        public void setRootFolder(String rootFolder) {
            this.rootFolder = rootFolder;
        }

        public boolean getCreateRoot() {
            return this.createRoot;
        }

        public void setCreateRoot(Boolean createRoot) {
            this.createRoot = createRoot;
        }
    }
}
