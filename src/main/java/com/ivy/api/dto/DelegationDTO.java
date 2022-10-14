package com.ivy.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@SuperBuilder
public class DelegationDTO {
    @NotNull
    private String fromAddress;

    @NotNull
    private String toAddress;

    @NotNull
    private Double amount;

    @NotNull
    private BigInteger updatedAtBlock;

    @NotNull
    private BigInteger createdAtBlock;
}
