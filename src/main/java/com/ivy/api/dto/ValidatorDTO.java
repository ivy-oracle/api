package com.ivy.api.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidatorDTO {
    @NotNull
    private String nodeID;

    @NotNull
    private ValidatorRewardOwnerDTO rewardOwner;

    @NotNull
    private Long stakeAmount;

    @NotNull
    private Long potentialReward;

    @NotNull
    private Double delegationFee;

    @NotNull
    private Double uptime;

    @NotNull
    private Boolean connected;

}
