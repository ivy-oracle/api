package com.ivy.api.repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
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

	@Query(value = "SELECT et.* FROM eth_transaction et WHERE (et.from_address = :address OR et.to_address = :address) AND et.to_address NOT IN :excludeAddresses", nativeQuery = true)
	public Page<EthTransactionEntity> getByInvolvedAddress(@Param("address") String address,
			@Param("excludeAddresses") List<String> excludeAddresses, Pageable pageable);

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
			"et.to_address = :toAddress AND " +
			"eb.block_timestamp >= :startTimestamp AND " +
			"eb.block_timestamp <= :endTimestamp AND " +
			"et.value > 0 LIMIT :limit", nativeQuery = true)
	public List<FundMovementProjection> getByFromAddressAndToAddressAndTimestamps(
			@Param("fromAddress") String fromAddress,
			@Param("toAddress") String toAddress,
			@Param("startTimestamp") Date startTimestamp,
			@Param("endTimestamp") Date endTimestamp,
			@Param("limit") Integer limit);

	@Query(value = "SELECT SUM(et.value) " +
			"FROM eth_transaction et " +
			"JOIN eth_block eb ON eb.block_number = et.block_number " +
			"JOIN eth_address ea ON ea.eth_address = et.to_address " +
			"WHERE et.from_address = :fromAddress AND " +
			"et.to_address = :toAddress AND " +
			"eb.block_timestamp >= :startTimestamp AND " +
			"eb.block_timestamp <= :endTimestamp AND " +
			"et.value > 0", nativeQuery = true)
	public BigInteger getSumByFromAddressAndToAddressAndTimestamps(
			@Param("fromAddress") String fromAddress,
			@Param("toAddress") String toAddress,
			@Param("startTimestamp") Date startTimestamp,
			@Param("endTimestamp") Date endTimestamp);

	@Query(value = "SELECT COUNT(*) " +
			"FROM eth_transaction et " +
			"JOIN eth_block eb ON eb.block_number = et.block_number " +
			"JOIN eth_address ea ON ea.eth_address = et.to_address " +
			"WHERE et.from_address = :fromAddress AND " +
			"et.to_address = :toAddress AND " +
			"eb.block_timestamp >= :startTimestamp AND " +
			"eb.block_timestamp <= :endTimestamp AND " +
			"et.value > 0", nativeQuery = true)
	public Integer getCountByFromAddressAndToAddressAndTimestamps(
			@Param("fromAddress") String fromAddress,
			@Param("toAddress") String toAddress,
			@Param("startTimestamp") Date startTimestamp,
			@Param("endTimestamp") Date endTimestamp);

	@Query(value = "SELECT DISTINCT to_address " +
			"FROM eth_transaction et " +
			"JOIN eth_block eb ON eb.block_number = et.block_number " +
			"WHERE et.from_address = :fromAddress AND " +
			"eb.block_timestamp >= :startTimestamp AND " +
			"eb.block_timestamp <= :endTimestamp AND " +
			"et.value > 0 AND " +
			"et.to_address NOT IN :excludeAddresses LIMIT :limit", nativeQuery = true)
	public List<String> getDistinctToAddressByFromAddressAndTimestamps(
			@Param("fromAddress") String fromAddress,
			@Param("startTimestamp") Date startTimestamp,
			@Param("endTimestamp") Date endTimestamp,
			@Param("excludeAddresses") List<String> excludeAddresses,
			@Param("limit") Integer limit);

	@Query(value = "SELECT COUNT(DISTINCT to_address) " +
			"FROM eth_transaction et " +
			"JOIN eth_block eb ON eb.block_number = et.block_number " +
			"WHERE et.from_address = :fromAddress AND " +
			"eb.block_timestamp >= :startTimestamp AND " +
			"eb.block_timestamp <= :endTimestamp AND " +
			"et.value > 0 AND " +
			"et.to_address NOT IN :excludeAddresses", nativeQuery = true)
	public Integer getDistinctToAddressCountByFromAddressAndTimestamps(
			@Param("fromAddress") String fromAddress,
			@Param("startTimestamp") Date startTimestamp,
			@Param("endTimestamp") Date endTimestamp,
			@Param("excludeAddresses") List<String> excludeAddresses);
}
