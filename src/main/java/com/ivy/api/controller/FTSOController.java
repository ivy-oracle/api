package com.ivy.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ivy.api.dto.RewardEpochDTO;
import com.ivy.api.service.FTSOService;

@Controller
@RequestMapping("ftso")
public class FTSOController {
    private final FTSOService ftsoService;

    public FTSOController(FTSOService ftsoService) {
        this.ftsoService = ftsoService;
    }

    @GetMapping("/reward-epoch")
    ResponseEntity<RewardEpochDTO> getRewardEpoch() {
        return ResponseEntity.ok(this.ftsoService.getRewardEpoch());
    }
}
