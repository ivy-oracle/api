package com.ivy.api.service;

import java.math.BigInteger;

import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Keys;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import com.ivy.api.contract.PriceSubmitter;
import com.ivy.api.contract.VoterWhitelister;

import lombok.Getter;

@Service
public class ContractService {
    private final Web3j web3j;

    @Getter
    final PriceSubmitter priceSubmitter;

    @Getter
    final VoterWhitelister voterWhitelister;

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
    }
}
