package com.ivy.api.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.ivy.api.constant.TaskStatus;
import com.ivy.api.repository.entity.EthBlockEntity;
import com.ivy.api.repository.entity.EthTransactionEntity;
import com.ivy.api.service.IndexerService;
import com.ivy.api.service.IndexerTaskService;

@Controller
@RequestMapping("indexer")
public class IndexerController {
    private final IndexerService indexerService;
    private final IndexerTaskService indexerTaskService;

    public IndexerController(IndexerService indexerService, IndexerTaskService indexerTaskService) {
        this.indexerService = indexerService;
        this.indexerTaskService = indexerTaskService;
    }

    @PutMapping("blocks")
    public ResponseEntity<String> indexBlocks(@RequestParam("from") BigInteger from,
            @RequestParam("to") BigInteger to) {
        var currentTaskStatus = this.indexerTaskService.getTaskStatus();
        if (currentTaskStatus == TaskStatus.InProgress) {
            return ResponseEntity.ok("Task already in progress");
        }

        this.indexerTaskService.start(from, to);
        return ResponseEntity.ok("Task started successfully");
    }

    @GetMapping("block/{blockNumber}")
    public ResponseEntity<EthBlockEntity> getBlock(@PathVariable("blockNumber") BigInteger blockNumber) {
        EthBlockEntity block = this.indexerService.getBlock(blockNumber);
        return ResponseEntity.ok(block);
    }

    @PutMapping("block/{blockNumber}")
    public ResponseEntity<EthBlockEntity> indexBlock(@PathVariable("blockNumber") BigInteger blockNumber) {
        try {
            EthBlockEntity block = this.indexerService.indexBlock(blockNumber);
            return ResponseEntity.ok(block);
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, String.format("failed to index block %d", blockNumber), e);
        }
    }

    @GetMapping("transaction/{transactionHash}")
    public ResponseEntity<EthTransactionEntity> getTransaction(
            @PathVariable("transactionHash") String transactionHash) {
        return ResponseEntity.ok(this.indexerService.getTransaction(transactionHash));
    }
}
