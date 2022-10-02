package com.ivy.api.service;

import java.math.BigInteger;

import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Keys;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import com.ivy.api.contract.FtsoManager;
import com.ivy.api.contract.FtsoRewardManager;
import com.ivy.api.contract.PriceSubmitter;
import com.ivy.api.contract.VPContract;
import com.ivy.api.contract.VoterWhitelister;
import com.ivy.api.contract.WNat;

import lombok.Getter;

@Service
public class ContractService {
    private final Web3j web3j;

    @Getter
    final PriceSubmitter priceSubmitter;

    @Getter
    final VoterWhitelister voterWhitelister;

    @Getter
    final FtsoManager ftsoManager;

    @Getter
    final FtsoRewardManager ftsoRewardManager;

    @Getter
    final WNat wNat;

    @Getter
    final VPContract vpContract;

    public ContractService(Web3j web3j) throws Exception {
        this.web3j = web3j;

        Credentials dummyCredentials = Credentials.create(Keys.createEcKeyPair());

        this.priceSubmitter = PriceSubmitter.load(
                "0x1000000000000000000000000000000000000003",
                this.web3j,
                dummyCredentials, new DefaultGasProvider());

        this.voterWhitelister = VoterWhitelister.load(
                this.priceSubmitter.getVoterWhitelister().send(),
                this.web3j,
                dummyCredentials, new DefaultGasProvider());

        this.ftsoManager = FtsoManager.load(
                this.priceSubmitter.getFtsoManager().send(),
                this.web3j,
                dummyCredentials, new DefaultGasProvider());

        this.ftsoRewardManager = FtsoRewardManager.load(
                this.ftsoManager.rewardManager().send(),
                this.web3j,
                dummyCredentials, new DefaultGasProvider());

        this.wNat = WNat.load(
                this.ftsoRewardManager.wNat().send(),
                this.web3j,
                dummyCredentials, new DefaultGasProvider());

        this.vpContract = VPContract.load(
                this.wNat.readVotePowerContract().send(),
                this.web3j,
                dummyCredentials, new DefaultGasProvider());

    }
}
