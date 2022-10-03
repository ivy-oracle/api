package com.ivy.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ivy.api.dto.DelegationDTO;
import com.ivy.api.dto.PaginatedDTO;
import com.ivy.api.service.DelegationService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("delegation")
public class DelegationController {
    private final DelegationService delegationService;

    public DelegationController(DelegationService delegationService) {
        this.delegationService = delegationService;
    }

    @GetMapping()
    ResponseEntity<PaginatedDTO<DelegationDTO>> getDataProviders(
            @RequestParam(name = "from", required = false) Optional<String> fromAddress,
            @RequestParam(name = "to", required = false) Optional<String> toAddress,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var delegations = this.delegationService.getDelegations(fromAddress, toAddress, PageRequest.of(page, size));
        var response = new PaginatedDTO<DelegationDTO>(
                delegations.getContent().stream().map(delegation -> delegation.toDto()).collect(Collectors.toList()),
                page,
                size,
                delegations.getTotalElements(),
                delegations.hasNext());
        return ResponseEntity.ok(response);
    }
}
