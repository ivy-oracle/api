package com.ivy.api.repository;

import java.math.BigInteger;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ivy.api.repository.entity.DelegationEventEntity;

@Repository
public interface DelegationEventRepository extends JpaRepository<DelegationEventEntity, Long> {

    public DelegationEventEntity findByBlockNumberAndTransactionIndexAndLogIndex(
            @NotNull BigInteger blockNumber,
            @NotNull BigInteger transactionIndex,
            @NotNull BigInteger LogIndex);

    public List<DelegationEventEntity> findAllByBlockNumberIn(List<BigInteger> blockNumbers);

    @Query("select MAX(de.blockNumber) from DelegationEventEntity de")
    public BigInteger getLastBlockNumber();

    public Page<DelegationEventEntity> findByFrom(String from, Pageable pageable);

}
