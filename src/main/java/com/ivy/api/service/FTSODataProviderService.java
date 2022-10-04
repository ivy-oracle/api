package com.ivy.api.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.web3j.protocol.Web3j;
import org.web3j.tuples.generated.Tuple2;

import com.ivy.api.contract.VoterWhitelister;
import com.ivy.api.dto.FTSODataProviderDTO;
import com.ivy.api.dto.RewardEpochDTO;
import com.ivy.api.resolver.FTSODataProviderResolver;

@Service
public class FTSODataProviderService {
    ExecutorService executor = Executors.newFixedThreadPool(100);

    private final Web3j web3j;
    private final ContractService contractService;
    private final FTSOService ftsoService;

    public FTSODataProviderService(Web3j web3j, ContractService contractService, FTSOService ftsoService) {
        this.web3j = web3j;
        this.contractService = contractService;
        this.ftsoService = ftsoService;
    }

    public List<FTSODataProviderDTO> getAllFTSODataProviders() {
        VoterWhitelister voterWhitelister = this.contractService.getVoterWhitelister();

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
                    var address = votersAddress.get(i);
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

        RewardEpochDTO rewardEpochDTO = this.ftsoService.getRewardEpoch();

        List<Future<FTSODataProviderDTO>> tasks = new ArrayList<>();
        votersAddressMap.forEach((address, whitelistedSymbols) -> {
            FTSODataProviderDTO ftsoDataProviderDTO = FTSODataProviderDTO.builder()
                    .address(address)
                    .whitelistedSymbols(votersAddressMap.getOrDefault(address, List.of()))
                    .build();
            var task = this.executor.submit(
                    new FTSODataProviderResolver(ftsoDataProviderDTO, rewardEpochDTO, this.contractService));
            tasks.add(task);
        });

        List<FTSODataProviderDTO> results = new ArrayList<>();
        for (var task : tasks) {
            try {
                results.add(task.get());
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
        VoterWhitelister voterWhitelister = this.contractService.getVoterWhitelister();

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
                    var voterAddress = votersAddress.get(i);
                    if (votersAddressMap.containsKey(voterAddress)) {
                        var updatedWhitelistedSymbols = votersAddressMap.get(voterAddress).stream()
                                .collect(Collectors.toList());
                        updatedWhitelistedSymbols.add(symbol);
                        votersAddressMap.put((String) voterAddress, updatedWhitelistedSymbols);
                    } else {
                        votersAddressMap.put((String) voterAddress, List.of(symbol));
                    }
                }
            } catch (Exception e) {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get whitelisted voters", e);
            }
        }

        RewardEpochDTO rewardEpochDTO = this.ftsoService.getRewardEpoch();

        FTSODataProviderDTO ftsoDataProviderDTO = FTSODataProviderDTO.builder()
                .address(address)
                .whitelistedSymbols(votersAddressMap.getOrDefault(address, List.of()))
                .build();
        var task = this.executor.submit(
                new FTSODataProviderResolver(ftsoDataProviderDTO, rewardEpochDTO, this.contractService));

        FTSODataProviderDTO result;
        try {
            result = task.get();
        } catch (ExecutionException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "failed to resolve FTSO data providers", e);
        } catch (InterruptedException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "failed to resolve FTSO data providers", e);
        }

        return result;
    }
}
