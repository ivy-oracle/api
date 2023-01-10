package com.ivy.api.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@SuperBuilder
public class FundMovementNodeDTO {
    @NotNull
    private String address;

    @NotNull
    private Set<FundMovementDTO> initiatedTransactions;

    @NotNull
    private Set<FundMovementDTO> receivedTransactions;

    public FundMovementNodeDTO(String address) {
        this.address = address;
        this.initiatedTransactions = new HashSet<>();
        this.receivedTransactions = new HashSet<>();
    }

}
