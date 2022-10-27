package com.ivy.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ivy.api.dto.ValidatorDTO;
import com.ivy.api.service.ValidatorService;

@Controller
@RequestMapping("validator")
public class ValidatorController {
    private final ValidatorService validatorService;

    public ValidatorController(ValidatorService validatorService) {
        this.validatorService = validatorService;
    }

    @GetMapping()
    public ResponseEntity<List<ValidatorDTO>> index() {
        return ResponseEntity.ok(this.validatorService.getValidators());
    }
}
