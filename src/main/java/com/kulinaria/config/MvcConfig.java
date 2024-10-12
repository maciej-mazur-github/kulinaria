package com.kulinaria.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    private final StaticResourcesPath staticResourcesPath;

    public MvcConfig(StaticResourcesPath staticResourcesPath) {
        this.staticResourcesPath = staticResourcesPath;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String pathPrefix = staticResourcesPath.getPathPrefix();

        registry
                .addResourceHandler("/recipes/**")
                .addResourceLocations(pathPrefix + "/images/recipes/");
        registry
                .addResourceHandler("/categories/**")
                .addResourceLocations(pathPrefix + "/images/categories/");
    }

}