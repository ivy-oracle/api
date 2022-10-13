package com.ivy.api.util;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FTSOUtil {
    private static Logger logger = LoggerFactory.getLogger(FTSOUtil.class);

    // TODO: Update this value dynamically
    private static long priceEpochInitTimestamp = 1631824801;

    private static int priceEpochDuration = 180;

    public static Long getPriceEpochId() {
        var currentTimestamp = new Date().getTime() / 1000;
        var secondSinceInit = currentTimestamp - FTSOUtil.priceEpochInitTimestamp;
        Long epochId = (long) Math.floor((double) secondSinceInit / (double) FTSOUtil.priceEpochDuration);

        return epochId;
    }

    public static int getSecondPassedInCurrentPriceEpoch() {
        var currentTimestamp = new Date().getTime() / 1000;
        var epochId = FTSOUtil.getPriceEpochId();
        int secondPassedInCurrentEpoch = (int) (currentTimestamp
                - (FTSOUtil.priceEpochInitTimestamp + epochId * FTSOUtil.priceEpochDuration));
        return secondPassedInCurrentEpoch;
    }

    public static void sleepUntilPriceEpochSecond(int triggeringSecond) throws InterruptedException {
        var secondPassedInCurrentEpoch = FTSOUtil.getSecondPassedInCurrentPriceEpoch();
        int sleepDuration;
        if (secondPassedInCurrentEpoch > triggeringSecond) {
            sleepDuration = triggeringSecond - secondPassedInCurrentEpoch + FTSOUtil.priceEpochDuration;
        } else {
            sleepDuration = triggeringSecond - secondPassedInCurrentEpoch;
        }
        logger.debug(String.format("sleeping for %ds", sleepDuration));
        Thread.sleep(sleepDuration * 1000);
    }
}
