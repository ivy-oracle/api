package com.ivy.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ivy.api.contract.VoterWhitelister;
import com.ivy.api.dto.FTSODataProviderDTO;

@Service
public class FTSODataProviderService {
    private final ContractService contractService;

    public FTSODataProviderService(ContractService contractService) {
        this.contractService = contractService;
    }

    @SuppressWarnings(value = "unchecked")
    public List<FTSODataProviderDTO> getAllFTSODataProviders() {
        VoterWhitelister voterWhitelister = this.contractService.getVoterWhitelister();

        Map<String, List<String>> votersAddressMap = new HashMap<String, List<String>>();
        try {
            var symbol = "ETH";
            List<String> votersAddress = voterWhitelister.getFtsoWhitelistedPriceProvidersBySymbol(symbol).send();
            for (int i = 0; i < votersAddress.size(); i++) {
                var address = votersAddress.get(i);
                if (votersAddressMap.containsKey(address)) {
                    votersAddressMap.get(address).add(symbol);
                } else {
                    votersAddressMap.put(address, List.of(symbol));
                }
            }
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get whitelisted voters", e);
        }

        return votersAddressMap.keySet().stream()
                .map(address -> new FTSODataProviderDTO(address, votersAddressMap.getOrDefault(address, List.of())))
                .collect(Collectors.toList());
    }
}
