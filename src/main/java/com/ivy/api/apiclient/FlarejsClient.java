package com.ivy.api.apiclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.ivy.api.dto.ValidatorDTO;

@FeignClient(url = "http://localhost:9652", name = "flarejs")
public interface FlarejsClient {
    @GetMapping("validator")
    List<ValidatorDTO> getValidators();
}
