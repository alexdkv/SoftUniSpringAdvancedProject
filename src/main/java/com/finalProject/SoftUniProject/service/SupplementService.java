package com.finalProject.SoftUniProject.service;

import com.finalProject.SoftUniProject.model.dto.PageResponse;
import com.finalProject.SoftUniProject.model.dto.SupplementAddDTO;
import com.finalProject.SoftUniProject.model.dto.SupplementDTO;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class SupplementService {

    private final RestClient supplementRestClient;

    public SupplementService(@Qualifier("supplementsRestClient") RestClient supplementRestClient) {
        this.supplementRestClient = supplementRestClient;
    }

    public void createSupplement(SupplementAddDTO supplementAddDTO){
        supplementRestClient
                .post()
                .uri("http://localhost:8081/supplement")
                .body(supplementAddDTO)
                .retrieve();
    }

    public Page<SupplementDTO> getAllSupplements(Pageable pageable){

        PageResponse<SupplementDTO> supplements = supplementRestClient
                .get()
                .uri("http://localhost:8081/supplements?page={page}&size={size}&sort=id",
                        pageable.getPageNumber(),
                        pageable.getPageSize()
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<PageResponse<SupplementDTO>>() {});

        return new PageImpl<>(supplements.getContent(),pageable, supplements.getPage().totalElements());


    }

    public SupplementDTO getSupplementById(Long id) {
        return supplementRestClient
                .get()
                .uri("http://localhost:8081/supplement/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(SupplementDTO.class);
    }

    public void deleteSupplement(Long id) {
        supplementRestClient
                .delete()
                .uri("http://localhost:8081/supplement/{id}", id)
                .retrieve();
    }
}
