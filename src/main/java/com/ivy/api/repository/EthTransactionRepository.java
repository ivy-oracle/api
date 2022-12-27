package com.ivy.api.repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ivy.api.repository.entity.EthTransactionEntity;

@Repository
@Transactional
public interface EthTransactionRepository extends JpaRepository<EthTransactionEntity, BigInteger> {
    @Query(value = "SELECT (et).* FROM eth_transaction et JOIN eth_block eb ON eb.block_number = et.block_number WHERE et.from_address = :fromAddress AND eb.block_timestamp >= :startTimestamp AND et.to_address NOT IN :excludeAddresses", nativeQuery = true)
    public List<EthTransactionEntity> getByFromAddressAndStartTimestamp(
            @Param("fromAddress") String fromAddress,
            @Param("startTimestamp") Date startTimestamp,
            @Param("excludeAddresses") List<String> excludeAddresses);
}
