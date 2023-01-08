package com.ivy.api.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ivy.api.dto.projection.FTSODataProviderAccuracyProjection;
import com.ivy.api.repository.entity.PriceFinalizedEventEntity;

@Repository
public interface PriceFinalizedEventRepository extends JpaRepository<PriceFinalizedEventEntity, Long> {

	public PriceFinalizedEventEntity findByEpochIdAndSymbol(BigInteger epochId, String symbol);

	public List<PriceFinalizedEventEntity> findAllByEpochIdIn(List<BigInteger> epochIds);

	public List<PriceFinalizedEventEntity> findAllByEpochIdIn(Set<BigInteger> epochIds);

	@Query(value = "select count(*) from price_finalized_event where epoch_id > (:startEpochId - 1) and epoch_id < :endEpochId", nativeQuery = true)
	public Long getCountInEpochRange(@Param("startEpochId") BigInteger startEpochId,
			@Param("endEpochId") BigInteger endEpochId);

	@Query(value = "select epr.voter as address, sum(" +
			"case when epr.price = epf.low_reward_price then 0.5 " +
			"when epr.price = epf.high_reward_price then 0.5 " +
			"when epr.price > epf.low_reward_price and epr.price < epf.high_reward_price then 1.0 " +
			"else 0.0 end) / count(*) as accuracy " +
			"from price_revealed_event epr join price_finalized_event epf " +
			"on epf.epoch_id = epr.epoch_id and epf.symbol = epr.symbol " +
			"where epr.epoch_id > (:startEpochId - 1) and epr.epoch_id < :endEpochId group by epr.voter;", nativeQuery = true)
	public List<FTSODataProviderAccuracyProjection> getProviderAccuracies(
			@Param("startEpochId") BigInteger startEpochId,
			@Param("endEpochId") BigInteger endEpochId);

	@Query(value = "select epr.voter as address, sum(" +
			"case when epr.price = epf.low_reward_price then 0.5 " +
			"when epr.price = epf.high_reward_price then 0.5 " +
			"when epr.price > epf.low_reward_price and epr.price < epf.high_reward_price then 1.0 " +
			"else 0.0 end) / count(*) as accuracy " +
			"from price_revealed_event epr join price_finalized_event epf " +
			"on epf.epoch_id = epr.epoch_id and epf.symbol = epr.symbol " +
			"where epr.epoch_id > (:startEpochId - 1) and epr.epoch_id < :endEpochId " +
			"and epr.voter = :address group by epr.voter;", nativeQuery = true)
	public FTSODataProviderAccuracyProjection getProviderAccuracyByAddress(
			@Param("address") String address,
			@Param("startEpochId") BigInteger startEpochId,
			@Param("endEpochId") BigInteger endEpochId);
}
