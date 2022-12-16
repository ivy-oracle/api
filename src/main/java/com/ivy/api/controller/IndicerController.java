package com.ivy.api.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.ivy.api.repository.entity.EthBlockEntity;
import com.ivy.api.service.IndicerService;

@Controller
@RequestMapping("indicer")
public class IndicerController {
    IndicerService indicerService;

    public IndicerController(IndicerService indicerService) {
        this.indicerService = indicerService;
    }

    @PutMapping("block/{blockNumber}")
    public ResponseEntity<EthBlockEntity> indexBlock(@PathVariable("blockNumber") Long blockNumber) {
        try {
            EthBlockEntity block = this.indicerService.indexBlock(blockNumber);
            return ResponseEntity.ok(block);
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, String.format("failed to index block %d", blockNumber), e);
        }
    }
}
