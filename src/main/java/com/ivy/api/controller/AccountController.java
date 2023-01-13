package com.ivy.api.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.web3j.crypto.Keys;

import com.ivy.api.constant.Address;
import com.ivy.api.dto.AccountDetailDTO;
import com.ivy.api.dto.FundMovementNodeDTO;
import com.ivy.api.service.AccountService;
import com.ivy.api.service.DelegationService;
import com.ivy.api.util.CommonUtil;

@Controller
@RequestMapping("account")
public class AccountController {

    private final AccountService accountService;
    private final DelegationService delegationService;

    AccountController(AccountService accountService, DelegationService delegationService) {
        this.accountService = accountService;
        this.delegationService = delegationService;
    }

    @GetMapping("{address}")
    public ResponseEntity<AccountDetailDTO> getAccountDetails(
            @PathVariable("address") String address,
            @RequestParam(name = "excludeStatic", defaultValue = "true") Boolean excludeStatic,
            @RequestParam(name = "sort", defaultValue = "block_number") String sortColumn,
            @RequestParam(name = "sortBy", defaultValue = "desc") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Sort sort = Sort.by(sortBy == "asc" ? Direction.ASC : Direction.DESC, sortColumn);
        var accountDetail = this.accountService.getAccountDetail(address.toLowerCase(), excludeStatic,
                PageRequest.of(page, size, sort));
        return ResponseEntity.ok(accountDetail);
    }

    @GetMapping("fund-tracing")
    public ResponseEntity<Map<String, FundMovementNodeDTO>> getFundMovements(
            @RequestParam("fromAddress") String fromAddress,
            @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date fromDate,
            @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date toDate,
            @RequestParam Integer levels) {
        return ResponseEntity.ok(this.accountService.getFundMovements(fromAddress.toLowerCase(), fromDate, toDate,
                Math.min(levels, 3), Address.Statics));
    }
}
