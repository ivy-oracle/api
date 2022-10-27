package com.ivy.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ivy.api.apiclient.FlarejsClient;
import com.ivy.api.dto.ValidatorDTO;

@Service
public class ValidatorService {
    private final FlarejsClient flarejsClient;

    public ValidatorService(FlarejsClient flarejsClient) {
        this.flarejsClient = flarejsClient;
    }

    public List<ValidatorDTO> getValidators() {
        return this.flarejsClient.getValidators();
    }
}
