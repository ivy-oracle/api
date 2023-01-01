package com.ivy.api.service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetCode;
import org.web3j.protocol.core.methods.response.Transaction;

import com.ivy.api.repository.EthAddressRepository;
import com.ivy.api.repository.EthBlockRepository;
import com.ivy.api.repository.EthTransactionRepository;
import com.ivy.api.repository.entity.EthAddressEntity;
import com.ivy.api.repository.entity.EthBlockEntity;
import com.ivy.api.repository.entity.EthTransactionEntity;
import com.ivy.api.util.CommonUtil;

@Service
public class IndexerService {
    ExecutorService executor = Executors.newFixedThreadPool(100);

    private final Web3j web3j;
    private final EthBlockRepository ethBlockRepository;
    private final EthTransactionRepository ethTransactionRepository;
    private final EthAddressRepository ethAddressRepository;

    public IndexerService(Web3j web3j, EthBlockRepository ethBlockRepository,
            EthTransactionRepository ethTransactionRepository, EthAddressRepository ethAddressRepository) {
        this.web3j = web3j;
        this.ethBlockRepository = ethBlockRepository;
        this.ethTransactionRepository = ethTransactionRepository;
        this.ethAddressRepository = ethAddressRepository;
    }

    public EthBlockEntity getBlock(BigInteger blockNumber) {
        return this.ethBlockRepository.getByBlockNumber(blockNumber);
    }

    public List<EthBlockEntity> getBlocks(BigInteger from, BigInteger to) {
        return this.ethBlockRepository.getByFromBlockNumberAndToBlockNumber(from, to);
    }

    public List<EthBlockEntity> indexBlocks(BigInteger from, BigInteger to) throws IOException {
        var unIndexedBlockNumbers = this.ethBlockRepository.getUnIndexedBlockNumbers(from, to);

        List<EthBlockEntity> blocks = new ArrayList<>();
        for (int i = 0; i < unIndexedBlockNumbers.size(); i++) {
            var blockNumber = unIndexedBlockNumbers.get(i);
            var block = this.indexBlock(blockNumber);
            blocks.add(block);
        }

        return blocks;
    }

    public EthBlockEntity indexBlock(BigInteger blockNumber) throws IOException {
        // TODO: Check if block is in database already.

        var ethBlock = web3j.ethGetBlockByNumber(
                DefaultBlockParameter.valueOf(blockNumber),
                true).send();

        var ethBlockResult = ethBlock.getResult();
        EthBlockEntity ethBlockEntity = new EthBlockEntity(
                ethBlockResult.getNumber(),
                ethBlockResult.getHash(),
                CommonUtil.convertTimestampToDate(ethBlockResult.getTimestamp()));
        this.ethBlockRepository.save(ethBlockEntity);

        List<Transaction> transactions = ethBlockResult.getTransactions().stream()
                .map(transaction -> (Transaction) transaction.get()).collect(Collectors.toList());
        List<EthTransactionEntity> transactionEntities = new ArrayList<>();
        Set<String> addresses = new HashSet<>();
        transactions.stream().forEach(transactionResult -> {
            String fromAddress = transactionResult.getFrom();
            String toAddress = transactionResult.getTo();
            EthTransactionEntity transactionEntity = new EthTransactionEntity(
                    transactionResult.getHash(),
                    fromAddress,
                    toAddress != null ? toAddress : "",
                    transactionResult.getValue(),
                    transactionResult.getGas(),
                    transactionResult.getGasPrice(),
                    transactionResult.getNonce(),
                    transactionResult.getTransactionIndex(),
                    ethBlockEntity);
            transactionEntities.add(transactionEntity);
            addresses.add(toAddress);
            addresses.add(fromAddress);
        });

        List<String> addressArray = new ArrayList<>(addresses);
        List<EthAddressEntity> addressEntities = new ArrayList<>();
        for (int i = 0; i < addressArray.size(); i++) {
            String address = addressArray.get(i);
            EthGetCode code = this.web3j.ethGetCode(address, DefaultBlockParameterName.LATEST).send();
            Boolean isContract = !code.getResult().equals("0x");
            addressEntities.add(new EthAddressEntity(address, isContract));
        }

        this.ethAddressRepository.saveAll(addressEntities);
        this.ethTransactionRepository.saveAll(transactionEntities);

        return ethBlockEntity;
    }
}
