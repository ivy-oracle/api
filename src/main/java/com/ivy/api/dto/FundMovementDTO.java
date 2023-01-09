package com.ivy.api.dto;

import java.math.BigInteger;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.ivy.api.dto.projection.FundMovementProjection;

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

    @NotNull
    private List<FundMovementDTO> transactions;

    public static FundMovementDTO of(FundMovementProjection p) {
        return FundMovementDTO.builder()
                .transactionHash(p.getTransactionHash())
                .fromAccount(p.getFromAddress())
                .toAccount(p.getToAddress())
                .amount(p.getValue())
                .isContractInteraction(p.getIsContract()).build();
    }
}
