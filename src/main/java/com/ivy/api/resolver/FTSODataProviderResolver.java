package com.ivy.api.resolver;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.Callable;

import com.ivy.api.dto.FTSODataProviderDTO;
import com.ivy.api.dto.RewardEpochDTO;
import com.ivy.api.service.ContractService;
import com.ivy.api.util.CommonUtil;

public class FTSODataProviderResolver implements Callable<FTSODataProviderDTO> {
    private FTSODataProviderDTO ftsoDataProviderDTO;
    private RewardEpochDTO rewardEpochDTO;

    private final ContractService contractService;

    public FTSODataProviderResolver(
            FTSODataProviderDTO ftsoDataProviderDTO,
            RewardEpochDTO rewardEpochDTO,
            ContractService contractService) {
        this.ftsoDataProviderDTO = ftsoDataProviderDTO;
        this.rewardEpochDTO = rewardEpochDTO;
        this.contractService = contractService;
    }

    @Override
    public FTSODataProviderDTO call() throws Exception {
        String address = this.ftsoDataProviderDTO.getAddress();
        BigInteger latestBlockNumber = this.contractService.getLatestBlockNumber();

        var fee = this.contractService.getFtsoRewardManager()
                .getDataProviderCurrentFeePercentage(address).send();
        ftsoDataProviderDTO.setFee(fee.floatValue() / 10000);

        var lockedVotePower = CommonUtil.convertTokenToMiminalToken(this.contractService.getVpContract()
                .votePowerOfAt(address, this.rewardEpochDTO.getVotePowerLockBlockNumber()).send());
        ftsoDataProviderDTO.setLockedVotePower(lockedVotePower);
        var currentVotePower = CommonUtil.convertTokenToMiminalToken(this.contractService.getVpContract()
                .votePowerOfAt(address, latestBlockNumber).send());
        ftsoDataProviderDTO.setCurrentVotePower(currentVotePower);

        var providerRewards = CommonUtil.convertTokenToMiminalToken(this.contractService.getFtsoRewardManager()
                .getStateOfRewardsFromDataProviders(address, this.rewardEpochDTO.getEpochId(), List.of(address)).send()
                .component1().get(0));
        ftsoDataProviderDTO.setProviderRewards(providerRewards);

        var totalRewards = CommonUtil.convertTokenToMiminalToken(
                this.contractService.getFtsoRewardManager().getUnclaimedReward(
                        this.rewardEpochDTO.getEpochId(), address).send().component1());
        ftsoDataProviderDTO.setTotalRewards(totalRewards);

        var rewardRate = totalRewards / lockedVotePower;
        ftsoDataProviderDTO.setRewardRate((float) rewardRate);

        return ftsoDataProviderDTO;
    }
}
