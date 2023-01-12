package com.ivy.api.dto;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@SuperBuilder
public class FundMovementDTO extends PaginatedDTO<FundMovementTransactionDTO> {

    @NotNull
    private Double amount;

    public FundMovementDTO() {
        this.setData(new ArrayList<>());
    }

}
