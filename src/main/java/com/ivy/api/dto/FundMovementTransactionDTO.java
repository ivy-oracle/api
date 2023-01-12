package com.ivy.api.dto;

import java.math.BigInteger;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.ivy.api.dto.projection.FundMovementProjection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@SuperBuilder
public class FundMovementTransactionDTO {
    @NotNull
    private String transactionHash;

    @NotNull
    private String fromAccount;

    @NotNull
    private String toAccount;

    @NotNull
    private BigInteger amount;

    @NotNull
    private Date timestamp;

    @NotNull
    private Boolean isContractInteraction;

    public static FundMovementTransactionDTO of(FundMovementProjection p) {
        return FundMovementTransactionDTO.builder()
                .transactionHash(p.getTransactionHash())
                .fromAccount(p.getFromAddress())
                .toAccount(p.getToAddress())
                .amount(p.getValue())
                .timestamp(p.getTimestamp())
                .isContractInteraction(p.getIsContract()).build();
    }
}
