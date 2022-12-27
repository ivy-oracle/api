package com.ivy.api.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ivy.api.dto.FundMovementDTO;
import com.ivy.api.repository.EthTransactionRepository;

@Service
public class FundTraceService {

    private final EthTransactionRepository ethTransactionRepository;

    FundTraceService(EthTransactionRepository ethTransactionRepository) {
        this.ethTransactionRepository = ethTransactionRepository;
    }

    public List<FundMovementDTO> getFundMovements(String fromAddress, Date fromDate) {
        // TODO: Exclude all contract interactions on database level.
        var transactions = this.ethTransactionRepository.getByFromAddressAndStartTimestamp(fromAddress, fromDate,
                List.of("0x1000000000000000000000000000000000000003"));

        var fundMovements = transactions.stream().map(transaction -> new FundMovementDTO(
                transaction.getTransactionHash(),
                transaction.getFromAddress(),
                transaction.getToAddress(),
                transaction.getValue())).collect(Collectors.toList());

        return fundMovements;
    }
}
