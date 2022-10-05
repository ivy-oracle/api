package com.ivy.api.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ivy.api.repository.dto.FTSODataProviderSubmissionCountDTO;
import com.ivy.api.repository.entity.PriceRevealedEventEntity;

@Repository
public interface PriceRevealedEventRepository extends JpaRepository<PriceRevealedEventEntity, Long> {

    public PriceRevealedEventEntity findByEpochIdAndSymbolAndVoter(BigInteger epochId, String symbol, String voter);

    public List<PriceRevealedEventEntity> findAllByEpochIdIn(List<BigInteger> epochIds);

    @Query(value = "select voter as address, count(epoch_id) as submissionCount "
            + "from price_revealed_event where epoch_id > :startEpochId and epoch_id < (:endEpochId + 1) group by voter", nativeQuery = true)
    public List<FTSODataProviderSubmissionCountDTO> getProviderSubmissionCounts(
            @Param("startEpochId") BigInteger startEpochId, @Param("endEpochId") BigInteger endEpochId);

}
