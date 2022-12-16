package com.ivy.api.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ivy.api.repository.entity.EthTransactionEntity;

@Repository
@Transactional
public interface EthTransactionRepository extends JpaRepository<EthTransactionEntity, BigInteger> {
}
