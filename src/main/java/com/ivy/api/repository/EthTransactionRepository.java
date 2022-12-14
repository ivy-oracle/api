package com.ivy.api.repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ivy.api.dto.projection.FundMovementProjection;
import com.ivy.api.repository.entity.EthTransactionEntity;

@Repository
@Transactional
public interface EthTransactionRepository extends JpaRepository<EthTransactionEntity, BigInteger> {

	public EthTransactionEntity getByTransactionHash(String hash);

	@Query(value = "SELECT " +
			"et.transaction_hash as transactionHash, " +
			"et.from_address as fromAddress, " +
			"et.to_address as toAddress, " +
			"et.value, " +
			"eb.block_timestamp as timestamp, " +
			"ea.is_contract as isContract " +
			"FROM eth_transaction et " +
			"JOIN eth_block eb ON eb.block_number = et.block_number " +
			"JOIN eth_address ea ON ea.eth_address = et.to_address " +
			"WHERE et.from_address = :fromAddress AND " +
			"eb.block_timestamp >= :startTimestamp AND " +
			"eb.block_timestamp <= :endTimestamp AND " +
			"et.value > 0 AND " +
			"et.to_address NOT IN :excludeAddresses", nativeQuery = true)
	public List<FundMovementProjection> getByFromAddressAndStartTimestamp(
			@Param("fromAddress") String fromAddress,
			@Param("startTimestamp") Date startTimestamp,
			@Param("endTimestamp") Date endTimestamp,
			@Param("excludeAddresses") List<String> excludeAddresses);
}
