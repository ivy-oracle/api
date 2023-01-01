package com.ivy.api.dto;

import java.math.BigInteger;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@SuperBuilder
public class FundMovementDTO {
    @NotNull
    private String transactionHash;

    @NotNull
    private String fromAccount;

    @NotNull
    private String toAccount;

    @NotNull
    private BigInteger amount;

    @NotNull
    private Boolean isContractInteraction;
}
