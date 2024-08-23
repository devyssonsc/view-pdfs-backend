package com.devyssonsc.view_pdfs_backend.configs;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
    
    public void addCorsMappings(CorsRegistry registry)
    {
        registry.addMapping("/**");
    }

}
