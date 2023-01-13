package com.ivy.api.dto;

import javax.validation.constraints.NotNull;

import com.ivy.api.repository.entity.DelegationEventEntity;
import com.ivy.api.repository.entity.EthTransactionEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDetailDTO {
    @NotNull
    private String address;

    @NotNull
    private PaginatedDTO<EthTransactionEntity> transactions;

    @NotNull
    private PaginatedDTO<DelegationEventEntity> delegationHistory;

}
