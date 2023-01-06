package com.ivy.api.service;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock.Block;

import com.ivy.api.dto.PriceEpochDTO;
import com.ivy.api.dto.RewardEpochDTO;
import com.ivy.api.util.CommonUtil;

@Service
public class FTSOService {
    private final Web3j web3j;
    private final ContractService contractService;

    public FTSOService(Web3j web3j, ContractService contractService) {
        this.web3j = web3j;
        this.contractService = contractService;
    }

    public RewardEpochDTO getRewardEpoch() {
        var ftsoManager = this.contractService.getFtsoManager();

        RewardEpochDTO rewardEpochDTO;
        try {
            var epochIdFuture = ftsoManager.getCurrentRewardEpoch().sendAsync();
            var rewardEpochDurationSecondsFuture = ftsoManager.rewardEpochDurationSeconds().sendAsync();
            var rewardEpochsStartTsFuture = ftsoManager.rewardEpochsStartTs().sendAsync();

            var epochId = epochIdFuture.join();
            var votePowerLockBlockNumberFuture = ftsoManager.getRewardEpochVotePowerBlock(epochId).sendAsync();

            var votePowerLockBlockNumber = votePowerLockBlockNumberFuture.join();
            Block votePowerLockBlock = web3j
                    .ethGetBlockByNumber(DefaultBlockParameter.valueOf(votePowerLockBlockNumber), false)
                    .send().getBlock();

            var votePowerLockBlockTimestamp = votePowerLockBlock.getTimestamp();
            Date votePowerLockBlockDate = CommonUtil.convertTimestampToDate(votePowerLockBlockTimestamp);

            var rewardEpochDurationSeconds = rewardEpochDurationSecondsFuture.join();
            var rewardEpochsStartTs = rewardEpochsStartTsFuture.join();

            var currentRewardEpochStartTimestamp = rewardEpochsStartTs
                    .add(rewardEpochDurationSeconds.multiply(epochId));
            var currentRewardEpochStartDate = CommonUtil.convertTimestampToDate(currentRewardEpochStartTimestamp);

            var currentRewardEpochEndTimestamp = currentRewardEpochStartTimestamp.add(rewardEpochDurationSeconds);
            var currentRewardEpochEndDate = CommonUtil.convertTimestampToDate(currentRewardEpochEndTimestamp);

            rewardEpochDTO = new RewardEpochDTO(
                    epochId,
                    votePowerLockBlockNumber,
                    votePowerLockBlockDate,
                    currentRewardEpochStartDate,
                    currentRewardEpochEndDate);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get reward epoch data", e);
        }
        return rewardEpochDTO;
    }

    public PriceEpochDTO getPriceEpoch() {
        PriceEpochDTO priceEpochDTO;
        try {
            var ftsoManager = this.contractService.getFtsoManager();
            var rawPriceEpoch = ftsoManager.getCurrentPriceEpochData().send();
            // TODO: populate other price epoch information
            priceEpochDTO = PriceEpochDTO.builder()
                    .epochId(rawPriceEpoch.component1())
                    .build();
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get reward epoch data", e);
        }
        return priceEpochDTO;
    }
}
