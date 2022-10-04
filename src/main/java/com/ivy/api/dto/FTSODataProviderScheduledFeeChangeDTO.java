package com.ivy.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@SuperBuilder
public class FTSODataProviderScheduledFeeChangeDTO {
    @NotNull
    float fee;

    @NotNull
    int validFromEpoch;
}
