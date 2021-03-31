package com.example.practice.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class JacksonConfig {
	@Bean
	public ObjectMapper objectMapper() {
		return Jackson2ObjectMapperBuilder
				.json()
				.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.indentOutput(true)
				.modules(new JavaTimeModule())
				.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
				.build();
	}
}
