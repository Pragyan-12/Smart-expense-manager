package com.expensetracker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("https://smartexpensemanager-b3991.web.app") // Use allowedOriginPatterns instead of allowedOrigins
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
