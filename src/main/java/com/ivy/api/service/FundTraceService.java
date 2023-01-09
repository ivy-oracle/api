package com.ivy.api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ivy.api.dto.FundMovementDTO;
import com.ivy.api.repository.EthTransactionRepository;

@Service
public class FundTraceService {

    private final EthTransactionRepository ethTransactionRepository;

    FundTraceService(EthTransactionRepository ethTransactionRepository) {
        this.ethTransactionRepository = ethTransactionRepository;
    }

    public List<FundMovementDTO> getFundMovements(String fromAddress, Date fromDate, Date toDate, Integer levels,
            List<String> excludeAddresses) {
        var transactions = this.ethTransactionRepository.getByFromAddressAndStartTimestamp(fromAddress, fromDate,
                toDate, excludeAddresses);

        var fundMovements = transactions.stream().map(transaction -> FundMovementDTO.of(transaction)).toList();

        if (levels > 1) {
            for (var fundMovement : fundMovements) {
                var excludeAddressesCopy = new ArrayList<>(excludeAddresses);
                excludeAddresses.add(fundMovement.getFromAccount());
                fundMovement
                        .setTransactions(getFundMovements(fundMovement.getToAccount(), fromDate, toDate, levels - 1,
                                excludeAddressesCopy));
            }
        }

        return fundMovements;
    }
}
