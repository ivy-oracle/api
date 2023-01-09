package com.ivy.api.dto.projection;

import java.math.BigInteger;

public interface FundMovementProjection {

    String getTransactionHash();

    String getFromAddress();

    String getToAddress();

    BigInteger getValue();

    Boolean getIsContract();
}
