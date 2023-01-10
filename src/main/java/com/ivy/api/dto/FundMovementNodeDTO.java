package com.ivy.api.dto;

import java.util.ArrayList;
import java.util.List;

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
    private List<FundMovementDTO> initiatedTransactions;

    @NotNull
    private List<FundMovementDTO> receivedTransactions;

    public FundMovementNodeDTO(String address) {
        this.address = address;
        this.initiatedTransactions = new ArrayList<>();
        this.receivedTransactions = new ArrayList<>();
    }

}
