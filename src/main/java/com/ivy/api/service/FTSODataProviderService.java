package com.ivy.api.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.web3j.crypto.Keys;
import org.web3j.tuples.generated.Tuple2;

import com.ivy.api.contract.VoterWhitelister;
import com.ivy.api.dto.FTSODataProviderDTO;
import com.ivy.api.dto.RewardEpochDTO;
import com.ivy.api.util.CommonUtil;

@Service
public class FTSODataProviderService {
    ExecutorService executor = Executors.newFixedThreadPool(100);

    private final ContractService contractService;
    private final FTSOService ftsoService;
    private final VoterWhitelister voterWhitelister;

    public FTSODataProviderService(ContractService contractService, FTSOService ftsoService) {
        this.contractService = contractService;
        this.ftsoService = ftsoService;
        this.voterWhitelister = this.contractService.getVoterWhitelister();
    }

    public List<FTSODataProviderDTO> getAllFTSODataProviders() {
        var votersAddressMap = this.fetchAllWhitelistedVoters();

        RewardEpochDTO rewardEpochDTO = this.ftsoService.getRewardEpoch();

        List<Future<FTSODataProviderDTO>> tasks = new ArrayList<>();
        votersAddressMap.forEach((address, whitelistedSymbols) -> {
            FTSODataProviderDTO ftsoDataProviderDTO = FTSODataProviderDTO.builder()
                    .address(address)
                    .whitelistedSymbols(votersAddressMap.getOrDefault(address, List.of()))
                    .build();
            var task = this.executor.submit(
                    new FTSODataProviderWeb3Resolver(ftsoDataProviderDTO, rewardEpochDTO, this.contractService));
            tasks.add(task);
        });

        List<FTSODataProviderDTO> results = new ArrayList<>();
        for (var task : tasks) {
            try {
                results.add(this.resolveFromDatabase(task.get()));
            } catch (ExecutionException e) {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR, "failed to resolve FTSO data providers", e);
            } catch (InterruptedException e) {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR, "failed to resolve FTSO data providers", e);
            }
        }

        return results;
    }

    public FTSODataProviderDTO getFTSODataProvider(String address) {
        var checkedSumAddress = Keys.toChecksumAddress(address);
        var votersAddressMap = this.fetchAllWhitelistedVoters();

        RewardEpochDTO rewardEpochDTO = this.ftsoService.getRewardEpoch();

        FTSODataProviderDTO ftsoDataProviderDTO = FTSODataProviderDTO.builder()
                .address(checkedSumAddress)
                .whitelistedSymbols(votersAddressMap.getOrDefault(checkedSumAddress, List.of()))
                .build();

        try {
            new FTSODataProviderWeb3Resolver(ftsoDataProviderDTO, rewardEpochDTO, this.contractService).call();
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "failed to resolve FTSO data provider", e);
        }
        return this.resolveFromDatabase(ftsoDataProviderDTO);
    }

    private Map<String, List<String>> fetchAllWhitelistedVoters() {
        Tuple2<List<BigInteger>, List<String>> supportedIndicesAndSymbols;
        try {
            supportedIndicesAndSymbols = this.contractService.getFtsoRegistry().getSupportedIndicesAndSymbols().send();
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get supported FTSO indices and symbols", e);
        }

        Map<String, List<String>> votersAddressMap = new HashMap<String, List<String>>();

        for (String symbol : supportedIndicesAndSymbols.component2()) {
            try {
                var votersAddress = voterWhitelister.getFtsoWhitelistedPriceProvidersBySymbol(symbol).send();
                for (int i = 0; i < votersAddress.size(); i++) {
                    var address = Keys.toChecksumAddress((String) votersAddress.get(i));
                    if (votersAddressMap.containsKey(address)) {
                        var updatedWhitelistedSymbols = votersAddressMap.get(address).stream()
                                .collect(Collectors.toList());
                        updatedWhitelistedSymbols.add(symbol);
                        votersAddressMap.put((String) address, updatedWhitelistedSymbols);
                    } else {
                        votersAddressMap.put((String) address, List.of(symbol));
                    }
                }
            } catch (Exception e) {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get whitelisted voters", e);
            }
        }
        return votersAddressMap;
    }

    private FTSODataProviderDTO resolveFromDatabase(FTSODataProviderDTO ftsoDataProviderDTO) {
        return ftsoDataProviderDTO;
    }

    private class FTSODataProviderWeb3Resolver implements Callable<FTSODataProviderDTO> {

        private FTSODataProviderDTO ftsoDataProviderDTO;
        private RewardEpochDTO rewardEpochDTO;

        private final ContractService contractService;

        public FTSODataProviderWeb3Resolver(
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

            // TODO: Use VPContract batchVotePowerOfAt instead
            var lockedVotePower = CommonUtil.convertTokenToMiminalToken(this.contractService.getVpContract()
                    .votePowerOfAt(address, this.rewardEpochDTO.getVotePowerLockBlockNumber()).send());
            ftsoDataProviderDTO.setLockedVotePower(lockedVotePower);
            var currentVotePower = CommonUtil.convertTokenToMiminalToken(this.contractService.getVpContract()
                    .votePowerOfAt(address, latestBlockNumber).send());
            ftsoDataProviderDTO.setCurrentVotePower(currentVotePower);

            var providerRewards = CommonUtil.convertTokenToMiminalToken(this.contractService.getFtsoRewardManager()
                    .getStateOfRewardsFromDataProviders(address, this.rewardEpochDTO.getEpochId(), List.of(address))
                    .send()
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
}
