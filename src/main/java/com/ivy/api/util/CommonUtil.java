package com.ivy.api.util;

import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.Date;

public class CommonUtil {
    public static Double convertTokenToMiminalToken(BigInteger token) {
        return new BigDecimal(token.toString()).divide(new BigDecimal(Math.pow(10, 18))).doubleValue();
    }

    public static Date convertTimestampToDate(BigInteger timestamp) {
        return new Date(timestamp.longValue() * 1000L);
    }
}
