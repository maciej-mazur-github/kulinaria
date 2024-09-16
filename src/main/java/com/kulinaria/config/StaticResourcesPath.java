package com.kulinaria.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("kulinaria")
public class StaticResourcesPath {
    private String pathPrefix;

    public String getPathPrefix() {
        return pathPrefix;
    }

    public void setPathPrefix(String pathPrefix) {
        this.pathPrefix = pathPrefix;
    }
}
