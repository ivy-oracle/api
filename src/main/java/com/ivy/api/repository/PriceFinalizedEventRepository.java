package com.ivy.api.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ivy.api.repository.entity.PriceFinalizedEventEntity;

@Repository
public interface PriceFinalizedEventRepository extends JpaRepository<PriceFinalizedEventEntity, Long> {

    public PriceFinalizedEventEntity findByEpochIdAndSymbol(BigInteger epochId, String symbol);

    public List<PriceFinalizedEventEntity> findAllByEpochIdIn(List<BigInteger> epochIds);

    public List<PriceFinalizedEventEntity> findAllByEpochIdIn(Set<BigInteger> epochIds);

}
