package com.ivy.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ivy.api.dto.projection.DelegationStatProjection;
import com.ivy.api.repository.entity.DelegationEntity;

@Repository
@Transactional
public interface DelegationRepository extends JpaRepository<DelegationEntity, String> {
        DelegationEntity getByFromAddressAndToAddress(String fromAddress, String toAddress);

        Page<DelegationEntity> findAllByToAddress(String toAddress, Pageable pageable);

        Page<DelegationEntity> findAllByFromAddress(String fromAddress, Pageable pageable);

        @Query(value = "select to_address as address, avg(amount) as average, "
                        + "count(*) as count, "
                        + "stddev_pop(amount) as standardDeviation from "
                        + "delegation where amount >= 1 group by to_address", nativeQuery = true)
        List<DelegationStatProjection> getStats();

        @Query(value = "select to_address as address, avg(amount) as average, "
                        + "count(*) as count, "
                        + "delegation where to_address = :address and amount >= 1 group by to_address", nativeQuery = true)
        DelegationStatProjection getStats(String address);

        @Modifying
        @Query(value = "refresh materialized view concurrently delegation;", nativeQuery = true)
        void refreshMaterializedView();
}
