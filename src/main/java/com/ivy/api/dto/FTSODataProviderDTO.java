package com.ivy.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@SuperBuilder
public class FTSODataProviderDTO {
    @NotNull
    String address;

    @NotNull
    List<String> whitelistedSymbols;

    @NotNull
    Double lockedVotePower;

    @NotNull
    Double currentVotePower;

    @NotNull
    Double lockedVotePowerPercentage;

    @NotNull
    Double currentVotePowerPercentage;

    @NotNull
    Double totalRewards;

    @NotNull
    Double providerRewards;

    @NotNull
    float rewardRate;

    /**
     * Projected reward rate of next reward epoch given current vote power and
     * scheduled fee changes
     */
    @NotNull
    float projectedRewardRate;

    @NotNull
    float averageRewardRate;

    @NotNull
    float accuracy;

    @NotNull
    float availability;

    @NotNull
    float fee;

    @NotNull
    List<FTSODataProviderScheduledFeeChangeDTO> scheduledFeeChanges;
}
