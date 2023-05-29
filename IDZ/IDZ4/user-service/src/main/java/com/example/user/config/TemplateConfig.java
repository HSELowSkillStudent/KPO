package com.example.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * TemplateConfig
 */
@Configuration
public class TemplateConfig {
    /**
     * RestTemplate
     * @return RestTemplate
     */
    @Bean
    public RestTemplate RestTemplate() {
        return new RestTemplate();
    }
}
