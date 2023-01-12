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
    private Integer level;

    @NotNull
    private Integer childNodesCount;

    @NotNull
    private Boolean hasMoreChildNodes;

    @NotNull
    private FundMovementDTO initiatedTransactions;

    @NotNull
    private FundMovementDTO receivedTransactions;

    public FundMovementNodeDTO(String address, Integer level) {
        this.address = address;
        this.level = level;
        this.initiatedTransactions = new FundMovementDTO();
        this.receivedTransactions = new FundMovementDTO();
    }

}
