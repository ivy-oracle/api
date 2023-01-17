package com.ivy.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ivy.api.interceptor.AuthInterceptor;
import com.ivy.api.interceptor.RateLimitInterceptor;

@Configuration
public class AppConfiguration implements WebMvcConfigurer {

    @Autowired
    private RateLimitInterceptor rateLimitInterceptor;

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000",
                        // Legacy
                        "https://ivyoracle.xyz",
                        "https://develop.ivyoracle.xyz",
                        // Flare
                        "https://flare.ivyoracle.xyz",
                        "https://flare-dev.ivyoracle.xyz",
                        // Songbird
                        "https://songbird.ivyoracle.xyz",
                        "https://songbird-dev.ivyoracle.xyz")
                .allowedMethods("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rateLimitInterceptor);
        registry.addInterceptor(authInterceptor);
    }
}
