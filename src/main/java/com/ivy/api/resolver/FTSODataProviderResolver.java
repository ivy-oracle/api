package com.ivy.api.resolver;

import java.math.BigInteger;
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

        var lockedVotePower = this.contractService.getVpContract()
                .votePowerOfAt(address, this.rewardEpochDTO.getVotePowerLockBlockNumber()).send();
        ftsoDataProviderDTO.setLockedVotePower(CommonUtil.convertTokenToMiminalToken(lockedVotePower));
        var currentVotePower = this.contractService.getVpContract()
                .votePowerOfAt(address, latestBlockNumber).send();
        ftsoDataProviderDTO.setCurrentVotePower(CommonUtil.convertTokenToMiminalToken(currentVotePower));

        return ftsoDataProviderDTO;
    }
}