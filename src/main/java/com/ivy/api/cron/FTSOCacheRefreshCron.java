package com.ivy.api.cron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ivy.api.service.FTSODataProviderService;

@Component
public class FTSOCacheRefreshCron {
    Logger logger = LoggerFactory.getLogger(FTSOPriceCron.class);

    private final FTSODataProviderService ftsoDataProviderService;

    public FTSOCacheRefreshCron(FTSODataProviderService ftsoDataProviderService) {
        this.ftsoDataProviderService = ftsoDataProviderService;
    }

    @Scheduled(cron = "*/180 * * * * *")
    @Caching(evict = {
            @CacheEvict(value = "FTSODataProviderService.getAllFTSODataProviders", allEntries = true),
            @CacheEvict(value = "FTSODataProviderService.getFTSODataProvider", allEntries = true),
            @CacheEvict(value = "FTSODataProviderService.fetchAllWhitelistedVoters", allEntries = true),
            @CacheEvict(value = "FTSODataProviderService.getProviderLockedVotePower", allEntries = true),
            @CacheEvict(value = "FTSODataProviderService.getProviderCurrentVotePower", allEntries = true),
            @CacheEvict(value = "FTSODataProviderService.getProviderRewards", allEntries = true),
            @CacheEvict(value = "FTSODataProviderService.getTotalRewards", allEntries = true),
            @CacheEvict(value = "FTSODataProviderService.getScheduledFeeChanges", allEntries = true),
    })
    public void refreshCacheAtInterval() {
        logger.debug("refreshing ftso related caches");
        this.ftsoDataProviderService.getAllFTSODataProviders();
    }
}
