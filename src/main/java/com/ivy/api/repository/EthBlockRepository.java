package com.ivy.api.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ivy.api.repository.entity.EthBlockEntity;

@Repository
@Transactional
public interface EthBlockRepository extends JpaRepository<EthBlockEntity, BigInteger> {
    EthBlockEntity getByBlockNumber(BigInteger blockNumber);

    @Query(value = "select b from EthBlockEntity b where b.blockNumber >= :from and b.blockNumber <= :to")
    List<EthBlockEntity> getByFromBlockNumberAndToBlockNumber(BigInteger from, BigInteger to);

    @Query(value = "select series.i " +
            "from generate_series(:from, :to) series(i) " +
            "left join eth_block eb on eb.block_number = series.i " +
            "where eb.block_number is NULL;", nativeQuery = true)
    List<BigInteger> getUnIndexedBlockNumbers(BigInteger from, BigInteger to);
}
