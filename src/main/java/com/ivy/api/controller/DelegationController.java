package com.ivy.api.controller;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
    ResponseEntity<PaginatedDTO<DelegationDTO>> getDelegations(
            @RequestParam(name = "from", required = false) Optional<String> fromAddress,
            @RequestParam(name = "to", required = false) Optional<String> toAddress,
            @RequestParam(name = "sort", defaultValue = "amount") String sortColumn,
            @RequestParam(name = "sortBy", defaultValue = "desc") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Sort sort = Sort.by(sortBy == "asc" ? Direction.ASC : Direction.DESC, sortColumn);
        var delegations = this.delegationService.getDelegations(fromAddress, toAddress,
                PageRequest.of(page, size, sort));
        var response = new PaginatedDTO<DelegationDTO>(
                delegations.getContent().stream().map(delegation -> delegation.toDto()).collect(Collectors.toList()),
                page,
                size,
                delegations.getTotalElements(),
                delegations.hasNext());
        return ResponseEntity.ok(response);
    }
}
