package com.ivy.api.dto.projection;

import java.math.BigInteger;
import java.util.Date;

public interface FundMovementProjection {

    String getTransactionHash();

    String getFromAddress();

    String getToAddress();

    BigInteger getValue();

    Date getTimestamp();

    Boolean getIsContract();
}
