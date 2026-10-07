package com.comunidad.comunidad_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Profile("dev")
public class WebConfig implements WebMvcConfigurer {
    @Value("${app.upload.dir}")
    private String uploadDir;

    @Value("${app.upload.images.dir}")
    private String imagesDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/documents/**")
                .addResourceLocations("file:" + uploadDir);

        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + imagesDir); 
        
    }
}
