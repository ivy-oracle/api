package com.ivy.api.util;

import java.math.BigInteger;
import java.util.Date;

public class CommonUtil {
    public static Double convertTokenToMiminalToken(BigInteger token) {
        return Double.valueOf(token.doubleValue() / Math.pow(10, 18));
    }

    public static Date convertTimestampToDate(BigInteger timestamp) {
        return new Date(timestamp.longValue() * 1000L);
    }
}
