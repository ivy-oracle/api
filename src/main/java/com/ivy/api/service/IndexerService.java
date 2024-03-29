package com.ivy.api.service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetCode;
import org.web3j.protocol.core.methods.response.Transaction;

import com.ivy.api.constant.Address;
import com.ivy.api.repository.EthAddressRepository;
import com.ivy.api.repository.EthBlockRepository;
import com.ivy.api.repository.EthTransactionRepository;
import com.ivy.api.repository.entity.EthAddressEntity;
import com.ivy.api.repository.entity.EthBlockEntity;
import com.ivy.api.repository.entity.EthTransactionEntity;

import lombok.extern.log4j.Log4j2;

@Log4j2
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

    public EthTransactionEntity getTransaction(String hash) {
        return this.ethTransactionRepository.getByTransactionHash(hash);
    }

    public List<EthTransactionEntity> getTransactions(String address) {
        return this.ethTransactionRepository.getByInvolvedAddress(address, Address.Statics);
    }

    public List<EthBlockEntity> indexBlocks(BigInteger from, BigInteger to, Boolean reverse) throws IOException {
        var unIndexedBlockNumbers = this.ethBlockRepository.getUnIndexedBlockNumbers(from, to);

        log.debug("indexing {} un-indexed blocks from {} - {}", unIndexedBlockNumbers.size(), from, to);

        if (reverse) {
            Collections.reverse(unIndexedBlockNumbers);
        }

        List<EthBlockEntity> blocks = new ArrayList<>();
        Queue<CompletableFuture<EthBlock>> ethBlockFutures = new ArrayDeque<>();
        for (int i = 0; i < unIndexedBlockNumbers.size(); i++) {
            var blockNumber = unIndexedBlockNumbers.get(i);
            var ethBlockFuture = web3j.ethGetBlockByNumber(
                    DefaultBlockParameter.valueOf(blockNumber),
                    true).sendAsync();
            ethBlockFutures.add(ethBlockFuture);

            if (ethBlockFutures.size() >= 50) {
                while (!ethBlockFutures.isEmpty()) {
                    var ethBlock = ethBlockFutures.remove().join();
                    var block = this.processBlock(ethBlock);
                    blocks.add(block);
                }
                Double total = to.subtract(from).doubleValue();
                Double completed = total - unIndexedBlockNumbers.size() + blocks.size();
                Double completion = completed / total * 100;
                if (blocks.size() % 100 == 0) {
                    log.debug(String.format("Indexed %d/%d (%f%%) blocks", completed.longValue(),
                            total.longValue(), completion, from.toString(), to.toString()));
                }
            }
        }

        while (!ethBlockFutures.isEmpty()) {
            var ethBlock = ethBlockFutures.remove().join();
            var block = this.processBlock(ethBlock);
            blocks.add(block);
        }

        if (blocks.size() > 0) {
            log.debug(String.format("Indexed %d blocks from %d to %d", blocks.size(),
                    blocks.get(0).getBlockNumber(),
                    blocks.get(blocks.size() - 1).getBlockNumber()));
        }

        return blocks;
    }

    public EthBlockEntity indexBlock(BigInteger blockNumber) throws IOException {
        // TODO: Check if block is in database already.

        var ethBlock = web3j.ethGetBlockByNumber(
                DefaultBlockParameter.valueOf(blockNumber),
                true).send();
        var ethBlockEntity = this.processBlock(ethBlock);
        return ethBlockEntity;
    }

    private EthBlockEntity processBlock(EthBlock ethBlock) {
        EthBlockEntity ethBlockEntity = EthBlockEntity.of(ethBlock);
        this.ethBlockRepository.save(ethBlockEntity);

        List<Transaction> transactions = ethBlock.getResult().getTransactions().stream()
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
        List<CompletableFuture<EthGetCode>> codeFutures = new ArrayList<>();
        for (int i = 0; i < addressArray.size(); i++) {
            String address = addressArray.get(i);
            var codeFuture = this.web3j.ethGetCode(address, DefaultBlockParameterName.LATEST).sendAsync();
            codeFutures.add(codeFuture);
        }

        for (int i = 0; i < addressArray.size(); i++) {
            String address = addressArray.get(i);
            var code = codeFutures.get(i).join();
            if (code.getResult() == null) {
                log.warn("Get address code returned NULL for {}", address);
                continue;
            }
            Boolean isContract = !code.getResult().equals("0x");
            addressEntities.add(new EthAddressEntity(address, isContract));
        }

        this.ethAddressRepository.saveAll(addressEntities);
        this.ethAddressRepository.flush();
        this.ethTransactionRepository.saveAll(transactionEntities);

        return ethBlockEntity;
    }
}
