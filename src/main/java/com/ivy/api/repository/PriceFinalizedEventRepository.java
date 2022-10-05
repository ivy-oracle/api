package com.ivy.api.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ivy.api.repository.entity.PriceFinalizedEventEntity;

@Repository
public interface PriceFinalizedEventRepository extends JpaRepository<PriceFinalizedEventEntity, Long> {

    public PriceFinalizedEventEntity findByEpochIdAndSymbol(BigInteger epochId, String symbol);

    public List<PriceFinalizedEventEntity> findAllByEpochIdIn(List<BigInteger> epochIds);

    public List<PriceFinalizedEventEntity> findAllByEpochIdIn(Set<BigInteger> epochIds);

    @Query(value = "select count(*) from price_finalized_event where epoch_id > :startEpochId and epoch_id < (:endEpochId + 1)", nativeQuery = true)
    public Long getCountInEpochRange(@Param("startEpochId") BigInteger startEpochId,
            @Param("endEpochId") BigInteger endEpochId);
}
