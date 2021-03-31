package com.example.practice.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import io.netty.handler.codec.http.HttpMethod;

@Configuration
public class WebConfig implements WebFluxConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
        .allowedOrigins("http://localhost:9528")
        .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name())
        .allowedHeaders("*")
        .exposedHeaders("*")
        .allowCredentials(true).maxAge(3600);
	}
}
   