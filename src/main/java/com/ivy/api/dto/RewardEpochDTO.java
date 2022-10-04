package com.ivy.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.util.Date;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@SuperBuilder
public class RewardEpochDTO {
    @NotNull
    BigInteger epochId;

    @NotNull
    BigInteger votePowerLockBlockNumber;

    @NotNull
    Date votePowerLockBlockDate;
}
