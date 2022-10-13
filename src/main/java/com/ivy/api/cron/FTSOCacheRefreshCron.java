package com.ivy.api.cron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FTSOCacheRefreshCron {
    Logger logger = LoggerFactory.getLogger(FTSOPriceCron.class);

    @Scheduled(cron = "*/180 * * * * *")
    public void refreshCacheAtInterval() {
        logger.debug("refreshing ftso related caches");
        this.refreshCache();
    }

    @Caching(evict = {
            @CacheEvict("FTSODataProviderService.fetchAllWhitelistedVoters"),
            @CacheEvict("FTSODataProviderService.getProviderLockedVotePower"),
            @CacheEvict("FTSODataProviderService.getProviderCurrentVotePower"),
            @CacheEvict("FTSODataProviderService.getProviderRewards"),
            @CacheEvict("FTSODataProviderService.getTotalRewards"),
            @CacheEvict("FTSODataProviderService.getScheduledFeeChanges"),
    })
    private void refreshCache() {

    }
}
