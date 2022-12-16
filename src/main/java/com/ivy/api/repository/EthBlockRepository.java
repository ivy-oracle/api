package com.ivy.api.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ivy.api.repository.entity.EthBlockEntity;

@Repository
@Transactional
public interface EthBlockRepository extends JpaRepository<EthBlockEntity, BigInteger> {
    EthBlockEntity getByBlockNumber(BigInteger blockNumber);
}
