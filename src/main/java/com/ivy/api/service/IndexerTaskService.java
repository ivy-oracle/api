package com.ivy.api.service;

import java.math.BigInteger;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ivy.api.constant.TaskStatus;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class IndexerTaskService {
    ExecutorService executor = Executors.newFixedThreadPool(100);

    private final IndexerService indexerService;

    @Getter
    private Future<Boolean> currentTask;

    IndexerTaskService(IndexerService indexerService) {
        this.indexerService = indexerService;
    }

    public void start(BigInteger fromBlock, BigInteger toBlock) {
        Future<Boolean> task = executor.submit(new IndexingTask(fromBlock, toBlock));
        currentTask = task;
    }

    public TaskStatus getTaskStatus() {
        if (currentTask == null) {
            return TaskStatus.Idle;
        }
        return currentTask.isDone() ? TaskStatus.Completed : TaskStatus.InProgress;
    }

    class IndexingTask implements Callable<Boolean> {
        private BigInteger fromBlock;
        private BigInteger toBlock;

        IndexingTask(BigInteger fromBlock, BigInteger toBlock) {
            this.fromBlock = fromBlock;
            this.toBlock = toBlock;
        }

        @Override
        public Boolean call() throws Exception {
            log.info("Starting block indexing task for block {} to {}", fromBlock, toBlock);

            for (BigInteger i = toBlock; i.compareTo(fromBlock) > 0; i = i.subtract(BigInteger.valueOf(100_000))) {
                var completed = false;
                var retries = 0;
                while (!completed && retries < 10) {
                    try {
                        var from = i.subtract(BigInteger.valueOf(100_000)).compareTo(fromBlock) < 0 ? fromBlock
                                : i.subtract(BigInteger.valueOf(100_000));
                        Double completion = (toBlock.doubleValue() - i.doubleValue())
                                / (toBlock.doubleValue() - fromBlock.doubleValue()) * 100;
                        completion = Math.round(completion * 100.0) / 100.0;
                        log.info("indexing blocks from {} - {} (of {} - {}) {}% completed", from, i, fromBlock, toBlock,
                                completion);
                        indexerService.indexBlocks(from, i, true);
                        completed = true;
                    } catch (Exception e) {
                        if (e instanceof DataIntegrityViolationException) {
                            log.warn("Data integrity violation encountered when indexing blocks.");
                        } else {
                            log.error("Error encountered when indexing blocks, retrying...", e);
                            retries += 1;
                        }
                    }
                }
            }

            return true;
        }
    }
}
