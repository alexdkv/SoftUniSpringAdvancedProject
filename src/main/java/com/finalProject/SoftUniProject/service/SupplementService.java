package com.finalProject.SoftUniProject.service;

import com.finalProject.SoftUniProject.model.dto.SupplementAddDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class SupplementService {

    private final RestClient supplementRestClient;

    public SupplementService(@Qualifier("supplementsRestClient") RestClient supplementRestClient) {
        this.supplementRestClient = supplementRestClient;
    }

    public void createSupplement(SupplementAddDTO supplementAddDTO){
        supplementRestClient.post()
                .uri("http://localhost:8081/supplement")
                .body(supplementAddDTO)
                .retrieve();
    }
}
