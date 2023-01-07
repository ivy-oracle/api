package com.ivy.api.service;

import java.math.BigInteger;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.stereotype.Service;

import com.ivy.api.constant.TaskStatus;

import lombok.Getter;

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
            indexerService.indexBlocks(fromBlock, toBlock, true);
            return true;
        }

    }
}
