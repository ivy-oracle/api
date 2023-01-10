package com.ivy.api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ivy.api.dto.FundMovementDTO;
import com.ivy.api.dto.FundMovementNodeDTO;
import com.ivy.api.repository.EthTransactionRepository;

@Service
public class FundTraceService {

    private final EthTransactionRepository ethTransactionRepository;

    FundTraceService(EthTransactionRepository ethTransactionRepository) {
        this.ethTransactionRepository = ethTransactionRepository;
    }

    public List<FundMovementDTO> getFundMovementTransactions(String fromAddress, Date fromDate, Date toDate,
            Integer levels,
            List<String> excludeAddresses) {
        var transactions = this.ethTransactionRepository.getByFromAddressAndStartTimestamp(fromAddress, fromDate,
                toDate, excludeAddresses);

        List<FundMovementDTO> fundMovements = transactions.stream().map(transaction -> FundMovementDTO.of(transaction))
                .toList();
        List<FundMovementDTO> outputFundMovements = new ArrayList<>(fundMovements);

        if (levels > 1 && fundMovements.size() > 0) {
            for (var fundMovement : fundMovements) {
                var excludeAddressesCopy = new ArrayList<>(excludeAddresses);
                excludeAddressesCopy.add(fundMovement.getFromAccount());
                var moreFundMovements = getFundMovementTransactions(fundMovement.getToAccount(),
                        fundMovement.getTimestamp(),
                        toDate, levels - 1,
                        excludeAddressesCopy);
                if (moreFundMovements.size() > 0) {
                    outputFundMovements.addAll(moreFundMovements);
                }
            }
        }

        return outputFundMovements;
    }

    public Map<String, FundMovementNodeDTO> getFundMovementNodes(String fromAddress, Date fromDate, Date toDate,
            Integer levels,
            List<String> excludeAddresses) {
        var transactions = this.getFundMovementTransactions(fromAddress, fromDate, toDate, levels, excludeAddresses);
        Map<String, FundMovementNodeDTO> addressToNodeMap = new HashMap<>();
        for (var transaction : transactions) {
            if (!addressToNodeMap.containsKey(transaction.getFromAccount())) {
                addressToNodeMap.put(transaction.getFromAccount(),
                        new FundMovementNodeDTO(transaction.getFromAccount()));
            }
            if (!addressToNodeMap.containsKey(transaction.getToAccount())) {
                addressToNodeMap.put(transaction.getToAccount(), new FundMovementNodeDTO(transaction.getToAccount()));
            }
        }
        for (var transaction : transactions) {
            addressToNodeMap.get(transaction.getFromAccount()).getInitiatedTransactions().add(transaction);
            addressToNodeMap.get(transaction.getToAccount()).getReceivedTransactions().add(transaction);
        }
        return addressToNodeMap;
    }
}
