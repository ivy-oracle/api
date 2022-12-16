package com.ivy.api.service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthTransaction;

import com.ivy.api.repository.EthBlockRepository;
import com.ivy.api.repository.EthTransactionRepository;
import com.ivy.api.repository.entity.EthBlockEntity;
import com.ivy.api.repository.entity.EthTransactionEntity;
import com.ivy.api.util.CommonUtil;

@Service
public class IndicerService {
    ExecutorService executor = Executors.newFixedThreadPool(100);

    private final Web3j web3j;
    private final EthBlockRepository ethBlockRepository;
    private final EthTransactionRepository ethTransactionRepository;

    public IndicerService(Web3j web3j, EthBlockRepository ethBlockRepository,
            EthTransactionRepository ethTransactionRepository) {
        this.web3j = web3j;
        this.ethBlockRepository = ethBlockRepository;
        this.ethTransactionRepository = ethTransactionRepository;
    }

    public EthBlockEntity indexBlock(Long blockNumber) throws IOException {
        // TODO: Check if block is in database already.

        var ethBlock = web3j.ethGetBlockByNumber(
                DefaultBlockParameter.valueOf(BigInteger.valueOf(blockNumber)),
                false).send();

        var ethBlockResult = ethBlock.getResult();
        EthBlockEntity ethBlockEntity = new EthBlockEntity(
                ethBlockResult.getNumber(),
                ethBlockResult.getHash(),
                CommonUtil.convertTimestampToDate(ethBlockResult.getTimestamp()));
        this.ethBlockRepository.save(ethBlockEntity);

        List<EthTransaction> transactions = this.fetchTransactionsFromBlock(ethBlock);
        List<EthTransactionEntity> transactionEntities = new ArrayList<>();
        transactions.stream().forEach(transaction -> {
            var transactionResult = transaction.getResult();
            EthTransactionEntity transactionEntity = new EthTransactionEntity(
                    transactionResult.getHash(),
                    transactionResult.getFrom(),
                    transactionResult.getTo(),
                    transactionResult.getValue(),
                    transactionResult.getGas(),
                    transactionResult.getGasPrice(),
                    transactionResult.getNonce(),
                    transactionResult.getTransactionIndex(),
                    ethBlockEntity);
            transactionEntities.add(transactionEntity);
        });
        this.ethTransactionRepository.saveAll(transactionEntities);

        return ethBlockEntity;
    }

    private List<EthTransaction> fetchTransactionsFromBlock(EthBlock ethBlock) {
        var transactions = ethBlock.getResult().getTransactions();
        List<String> transactionHashes = transactions.stream().map(transactionHash -> (String) transactionHash.get())
                .collect(Collectors.toList());

        List<Future<EthTransaction>> tasks = new ArrayList<>();
        transactionHashes.forEach(hash -> {
            try {
                var task = this.executor.submit(new TransactionResolver(hash));
                tasks.add(task);
            } catch (Exception e) {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get transactions", e);
            }
        });

        List<EthTransaction> results = new ArrayList<>();
        for (var task : tasks) {
            try {
                results.add(task.get());
            } catch (ExecutionException e) {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get transactions", e);
            } catch (InterruptedException e) {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get transactions", e);
            }
        }

        return results;
    }

    private class TransactionResolver implements Callable<EthTransaction> {
        private String hash;

        public TransactionResolver(String hash) {
            this.hash = hash;
        }

        @Override
        public EthTransaction call() throws Exception {
            return web3j.ethGetTransactionByHash(this.hash).send();
        }
    }
}
