package com.ivy.api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ivy.api.dto.FundMovementDTO;
import com.ivy.api.repository.EthAddressRepository;
import com.ivy.api.repository.EthTransactionRepository;
import com.ivy.api.repository.entity.EthAddressEntity;

@Service
public class FundTraceService {

    private final EthTransactionRepository ethTransactionRepository;
    private final EthAddressRepository ethAddressRepository;

    FundTraceService(EthTransactionRepository ethTransactionRepository, EthAddressRepository ethAddressRepository) {
        this.ethTransactionRepository = ethTransactionRepository;
        this.ethAddressRepository = ethAddressRepository;
    }

    public List<FundMovementDTO> getFundMovements(String fromAddress, Date fromDate, Date toDate) {
        var transactions = this.ethTransactionRepository.getByFromAddressAndStartTimestamp(fromAddress, fromDate,
                toDate,
                List.of("0x1000000000000000000000000000000000000001",
                        "0x1000000000000000000000000000000000000002",
                        "0x1000000000000000000000000000000000000003"));

        Set<String> addresses = transactions.stream().map(transaction -> transaction.getToAddress())
                .collect(Collectors.toSet());
        List<EthAddressEntity> addressEntities = this.ethAddressRepository
                .getByAddresses(new ArrayList<String>(addresses));
        Map<String, Boolean> isContractMap = new HashMap<>();
        addressEntities.forEach(addressEntity -> {
            isContractMap.put(addressEntity.getAddress(), addressEntity.getIsContract());
        });

        var fundMovements = transactions.stream().map(transaction -> new FundMovementDTO(
                transaction.getTransactionHash(),
                transaction.getFromAddress(),
                transaction.getToAddress(),
                transaction.getValue(),
                isContractMap.get(transaction.getToAddress()))).collect(Collectors.toList());

        return fundMovements;
    }
}
