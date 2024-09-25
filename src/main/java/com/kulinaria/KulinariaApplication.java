package com.kulinaria;

import com.kulinaria.config.StaticResourcesPath;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.i18n.LocaleContextHolder;

@SpringBootApplication
@EnableConfigurationProperties({StaticResourcesPath.class})
public class KulinariaApplication {

    public static void main(String[] args) {

        SpringApplication.run(KulinariaApplication.class, args);
        System.out.println(LocaleContextHolder.getLocale().getLanguage());
    }

}
