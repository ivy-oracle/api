package com.ivy.api.cron;

import java.io.IOException;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock.Block;

import com.ivy.api.repository.EthBlockRepository;
import com.ivy.api.service.IndexerService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class IndexerCron {
    private final IndexerService indexerService;
    private final EthBlockRepository ethBlockRepository;
    private final Web3j web3j;

    @Value("${web3.rpc.block-limit:30}")
    private int rpcBlockLimit;

    public IndexerCron(IndexerService indexerService, EthBlockRepository ethBlockRepository, Web3j web3j) {
        this.indexerService = indexerService;
        this.ethBlockRepository = ethBlockRepository;
        this.web3j = web3j;
    }

    @Scheduled(fixedDelay = 5 * 1000)
    public void indexLatestBlocks() {
        BigInteger latestBlockNumber;
        try {
            latestBlockNumber = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false)
                    .send().getBlock().getNumber();
        } catch (IOException e) {
            log.error("failed to fetch latest block number", e);
            return;
        }

        try {
            this.indexerService
                    .indexBlocks(latestBlockNumber.subtract(BigInteger.valueOf(rpcBlockLimit)), latestBlockNumber);
        } catch (IOException e) {
            IndexerCron.log.error("Failed to index blocks", e);
        }
    }

    // @Scheduled(fixedDelay = 60 * 1000)
    public void indexBlockchain() {
        IndexerCron.log.debug("Indexing blockchain");
        BigInteger fetchBlockSize = BigInteger.valueOf(rpcBlockLimit);

        try {
            Block block = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false)
                    .send().getBlock();
            BigInteger endBlock = block.getNumber();
            for (BigInteger i = BigInteger.ZERO; i.compareTo(endBlock) < 0; i = i
                    .add(fetchBlockSize.min(endBlock.subtract(i)))) {
                this.indexerService.indexBlocks(i, i.add(fetchBlockSize));
                IndexerCron.log
                        .debug(String.format("Indexed block %s to %s", i.toString(), i.add(fetchBlockSize).toString()));
            }
        } catch (IOException e) {
            IndexerCron.log.error("Failed to index blocks", e);
        }

    }
}
