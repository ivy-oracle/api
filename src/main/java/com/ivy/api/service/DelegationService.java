package com.ivy.api.service;

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

    public Page<DelegationEntity> getDelegations(String toAddress, Pageable pageable) {
        var delegations = this.delegationRepository.getAllByToAddress(toAddress, pageable);
        return delegations;
    }
}
