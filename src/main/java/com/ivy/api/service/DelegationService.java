package com.ivy.api.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;

import com.ivy.api.dto.DelegationStatDTO;
import com.ivy.api.repository.DelegationRepository;
import com.ivy.api.repository.EthBlockRepository;
import com.ivy.api.repository.entity.DelegationEntity;
import com.ivy.api.util.CommonUtil;

@Service
public class DelegationService {

    private final DelegationRepository delegationRepository;
    private final ContractService contractService;
    private final EthBlockRepository ethBlockRepository;

    public DelegationService(DelegationRepository delegationRepository, ContractService contractService,
            EthBlockRepository ethBlockRepository) {
        this.delegationRepository = delegationRepository;
        this.contractService = contractService;
        this.ethBlockRepository = ethBlockRepository;
    }

    public Page<DelegationEntity> getDelegations(
            Optional<String> fromAddress,
            Optional<String> toAddress,
            Pageable pageable) {
        if (toAddress.isPresent()) {
            return this.delegationRepository.findAllByToAddress(toAddress.get(), pageable);
        }
        if (fromAddress.isPresent()) {
            return this.delegationRepository.findAllByFromAddress(fromAddress.get(), pageable);
        }
        return this.delegationRepository.findAll(pageable);
    }

    public List<DelegationStatDTO> getDelegationStats() {
        var delegationStats = this.delegationRepository.getStats().stream().map(d -> DelegationStatDTO.of(d)).toList();

        var blockNumber24HourPrev = this.ethBlockRepository.get24HourBlockNumber();

        var votePowerFutures = delegationStats.stream()
                .map(d -> contractService.getWNat().votePowerOf(d.getAddress()).sendAsync()).toList();
        var votePowerPrevFutures = delegationStats.stream().map(
                d -> contractService.getWNat().votePowerOfAt(d.getAddress(), blockNumber24HourPrev).sendAsync())
                .toList();
        var votePowers = votePowerFutures.stream().map(d -> d.join()).toList();
        var votePowersPrev = votePowerPrevFutures.stream().map(d -> d.join()).toList();

        for (int i = 0; i < delegationStats.size(); i++) {
            var delegationStat = delegationStats.get(i);
            var currentVotePower = CommonUtil.convertTokenToMiminalToken(votePowers.get(i));
            var prevVotePower = CommonUtil.convertTokenToMiminalToken(votePowersPrev.get(i));
            Double change = prevVotePower > 0 ? (currentVotePower - prevVotePower) / prevVotePower : null;
            delegationStat.setPercentageChange24Hour(change);
        }
        return delegationStats;
    }
}
