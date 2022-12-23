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

import com.ivy.api.repository.entity.EthBlockEntity;
import com.ivy.api.service.IndexerService;

@Controller
@RequestMapping("indexer")
public class IndexerController {
    IndexerService indexerService;

    public IndexerController(IndexerService indexerService) {
        this.indexerService = indexerService;
    }

    @PutMapping("blocks")
    public ResponseEntity<List<EthBlockEntity>> indexBlocks(@RequestParam("from") BigInteger from,
            @RequestParam("to") BigInteger to) {
        try {
            this.indexerService.indexBlocks(from, to);
            var blocks = this.indexerService.getBlocks(from, to);
            return ResponseEntity.ok(blocks);
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, String.format("failed to index blocks %d to %d", from, to), e);
        }
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
}
