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
//        String pathPrefix = "";
//        String[] activeProfiles = environment.getActiveProfiles();
//        String activeProfile = activeProfiles.length > 0 ? activeProfiles[0] : "default";
//        if (activeProfile.equals(DEV_PROFILE)) {
//            pathPrefix = "file:C:\\Users\\Maciek\\Javastart\\Portfolio\\Kulinaria";
//        } else if (activeProfile.equals(DOCKER_DEV_PROFILE)) {
//            pathPrefix = "classpath:";
//        }

        String pathPrefix = staticResourcesPath.getPathPrefix();

        registry
                .addResourceHandler("/recipes/**")
                .addResourceLocations(pathPrefix + "/images/recipes/");
        System.out.println(pathPrefix + "/images/recipes/");
        registry
                .addResourceHandler("/categories/**")
                .addResourceLocations(pathPrefix + "/images/categories/");
    }

}