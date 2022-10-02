package com.ivy.api.cron;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EventScheduler {
    Logger logger = LoggerFactory.getLogger(EventScheduler.class);

    @PostConstruct
    public void onStartup() {
        fetchDelegateEvents();
    }

    @Scheduled(cron = "0 0 * * * *")
    public void fetchDelegateEvents() {
        logger.info("Fetching delegate events");

        // TODO: Implement me
    }
}
