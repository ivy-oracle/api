package com.ivy.api.service;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock.Block;

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
            var epochId = ftsoManager.getCurrentRewardEpoch().send();
            var votePowerLockBlockNumber = ftsoManager.getRewardEpochVotePowerBlock(epochId).send();
            Block votePowerLockBlock = web3j
                    .ethGetBlockByNumber(DefaultBlockParameter.valueOf(votePowerLockBlockNumber), false)
                    .send().getBlock();

            var votePowerLockBlockTimestamp = votePowerLockBlock.getTimestamp();
            Date votePowerLockBlockDate = CommonUtil.convertTimestampToDate(votePowerLockBlockTimestamp);

            rewardEpochDTO = RewardEpochDTO.builder()
                    .epochId(epochId)
                    .votePowerLockBlockNumber(votePowerLockBlockNumber)
                    .votePowerLockBlockDate(votePowerLockBlockDate)
                    .build();
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get reward epoch data", e);
        }
        return rewardEpochDTO;
    }
}
