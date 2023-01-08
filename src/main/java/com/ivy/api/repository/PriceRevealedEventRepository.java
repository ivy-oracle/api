package com.ivy.api.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ivy.api.dto.projection.FTSODataProviderSubmissionCountProjection;
import com.ivy.api.repository.entity.PriceRevealedEventEntity;

@Repository
public interface PriceRevealedEventRepository extends JpaRepository<PriceRevealedEventEntity, Long> {

	public PriceRevealedEventEntity findByEpochIdAndSymbolAndVoter(BigInteger epochId, String symbol, String voter);

	public List<PriceRevealedEventEntity> findAllByEpochIdIn(List<BigInteger> epochIds);

	public List<PriceRevealedEventEntity> findAllByEpochIdIn(Set<BigInteger> epochIds);

	@Query(value = "select voter as address, count(epoch_id) as submissionCount "
			+ "from price_revealed_event where epoch_id > (:startEpochId - 1) and epoch_id < :endEpochId group by voter", nativeQuery = true)
	public List<FTSODataProviderSubmissionCountProjection> getProviderSubmissionCounts(
			@Param("startEpochId") BigInteger startEpochId, @Param("endEpochId") BigInteger endEpochId);

	@Query(value = "select voter as address, count(epoch_id) as submissionCount " +
			"from price_revealed_event where epoch_id > (:startEpochId - 1) and epoch_id < :endEpochId " +
			" and voter = :address group by voter", nativeQuery = true)
	public FTSODataProviderSubmissionCountProjection getProviderSubmissionCountByAddress(
			@Param("address") String address,
			@Param("startEpochId") BigInteger startEpochId,
			@Param("endEpochId") BigInteger endEpochId);
}
