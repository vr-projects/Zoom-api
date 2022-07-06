package com.userOnboard.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

@Configuration
public class WebConfig 
{
    @Bean
    public ObjectMapper customJson() {

        return new Jackson2ObjectMapperBuilder()
            .indentOutput(true)
            .propertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE)
            .build();
    }
    
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizeJson() {
        return builder -> {

            builder.indentOutput(true);
            builder.propertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE);
        };
    }
}
