package com.ivy.api.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ivy.api.repository.DelegationRepository;
import com.ivy.api.repository.entity.DelegationEntity;

@Service
public class DelegationService {
    private final DelegationRepository delegationRepository;

    public DelegationService(DelegationRepository delegationRepository) {
        this.delegationRepository = delegationRepository;
    }

    public Page<DelegationEntity> getDelegations(
            Optional<String> fromAddress,
            Optional<String> toAddress,
            Pageable pageable) {
        if (toAddress.isPresent()) {
            return this.delegationRepository.findAllByToAddress(toAddress.get(), pageable);
        }
        if (fromAddress.isPresent()) {
            return this.delegationRepository.findAllByFromAddress(fromAddress.get(), pageable);
        }
        return this.delegationRepository.findAll(pageable);
    }
}
