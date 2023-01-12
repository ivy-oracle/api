package com.ivy.api.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ivy.api.dto.FundMovementNodeDTO;
import com.ivy.api.service.FundTraceService;

@Controller
@RequestMapping("fund-trace")
public class FundTraceController {

    private final FundTraceService fundTraceService;

    FundTraceController(FundTraceService fundTraceService) {
        this.fundTraceService = fundTraceService;
    }

    @GetMapping()
    public ResponseEntity<Map<String, FundMovementNodeDTO>> getFundMovements(
            @RequestParam("fromAddress") String fromAddress,
            @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date fromDate,
            @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date toDate,
            @RequestParam Integer levels) {
        var excludeAddresses = List.of("0x1000000000000000000000000000000000000001",
                "0x1000000000000000000000000000000000000002",
                "0x1000000000000000000000000000000000000003");
        return ResponseEntity
                .ok(this.fundTraceService.getFundMovements(fromAddress.toLowerCase(), fromDate, toDate, levels,
                        excludeAddresses));
    }
}
