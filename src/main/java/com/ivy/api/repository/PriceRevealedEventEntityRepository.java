package com.ivy.api.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ivy.api.repository.entity.PriceRevealedEventEntity;

@Repository
public interface PriceRevealedEventEntityRepository extends JpaRepository<PriceRevealedEventEntity, Long> {

    public PriceRevealedEventEntity findByEpochIdAndSymbolAndVoter(BigInteger epochId, String symbol, String voter);

    public List<PriceRevealedEventEntity> findAllByEpochIdIn(List<BigInteger> epochIds);

}
