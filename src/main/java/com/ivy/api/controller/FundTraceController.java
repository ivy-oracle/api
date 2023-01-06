package com.ivy.api.controller;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ivy.api.dto.FundMovementDTO;
import com.ivy.api.service.FundTraceService;

@Controller
@RequestMapping("fund-trace")
public class FundTraceController {

    private final FundTraceService fundTraceService;

    FundTraceController(FundTraceService fundTraceService) {
        this.fundTraceService = fundTraceService;
    }

    @GetMapping()
    public ResponseEntity<List<FundMovementDTO>> getFundMovements(
            @RequestParam("fromAddress") String fromAddress,
            @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss") Date fromDate,
            @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss") Date toDate) {
        return ResponseEntity.ok(this.fundTraceService.getFundMovements(fromAddress.toLowerCase(), fromDate, toDate));
    }
}
