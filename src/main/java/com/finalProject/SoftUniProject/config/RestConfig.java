package com.finalProject.SoftUniProject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class RestConfig {

    @Bean("supplementsRestClient")
    public RestClient supplementsRestClient(SupplementApiConfig supplementApiConfig){
        return RestClient.builder()
                .baseUrl(supplementApiConfig.getBaseUrl())
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
