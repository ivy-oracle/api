package com.ivy.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ivy.api.repository.entity.DelegationEntity;

@Repository
public interface DelegationRepository extends JpaRepository<DelegationEntity, String> {
    DelegationEntity getByFromAddressAndToAddress(String fromAddress, String toAddress);

    Page<DelegationEntity> findAllByToAddress(String toAddress, Pageable pageable);

    Page<DelegationEntity> findAllByFromAddress(String fromAddress, Pageable pageable);
}
