package com.ivy.api.cron;

import java.io.IOException;
import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.tx.Contract;

import com.ivy.api.contract.Ftso;
import com.ivy.api.contract.Ftso.PriceFinalizedEventResponse;
import com.ivy.api.contract.Ftso.PriceRevealedEventResponse;
import com.ivy.api.service.ContractService;

@Component
public class FTSOPriceCron {
    Logger logger = LoggerFactory.getLogger(FTSOPriceCron.class);

    private final Web3j web3j;
    private final ContractService contractService;

    public FTSOPriceCron(Web3j web3j, ContractService contractService) {
        this.web3j = web3j;
        this.contractService = contractService;
    }

    @Scheduled(fixedDelay = 180 * 1000)
    public void fetchPrices() throws IOException {
        logger.info("fetching finalized price events");

        BigInteger latestBlockNumber = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false)
                .send().getBlock().getNumber();

        for (String symbol : this.contractService.getFtsos().keySet()) {
            var ftso = this.contractService.getFtso(symbol);
            BigInteger fromBlock = latestBlockNumber.subtract(BigInteger.valueOf(250));
            BigInteger toBlock = latestBlockNumber.subtract(BigInteger.ONE);

            EthFilter filter = new EthFilter(
                    DefaultBlockParameter.valueOf(fromBlock),
                    DefaultBlockParameter.valueOf(toBlock),
                    ftso.getContractAddress());
            filter.addSingleTopic(EventEncoder.encode(Ftso.PRICEFINALIZED_EVENT));
            filter.addSingleTopic(EventEncoder.encode(Ftso.PRICEREVEALED_EVENT));

            var logs = web3j.ethGetLogs(filter).send().getLogs();

            for (int logIndex = 0; logIndex < logs.size(); logIndex++) {
                Log log = (Log) logs.get(logIndex).get();
                var topics = log.getTopics();

                if (topics.contains(EventEncoder.encode(Ftso.PRICEFINALIZED_EVENT))) {
                    EventValues eventValues = Contract.staticExtractEventParameters(
                            Ftso.PRICEFINALIZED_EVENT, log);
                    PriceFinalizedEventResponse typedResponse = new PriceFinalizedEventResponse();

                    typedResponse.epochId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                    typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                    typedResponse.rewardedFtso = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
                    typedResponse.lowRewardPrice = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                    typedResponse.highRewardPrice = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                    typedResponse.finalizationType = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                    typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();

                } else if (topics.contains(EventEncoder.encode(Ftso.PRICEREVEALED_EVENT))) {
                    EventValues eventValues = Contract.staticExtractEventParameters(
                            Ftso.PRICEREVEALED_EVENT, log);
                    PriceRevealedEventResponse typedResponse = new PriceRevealedEventResponse();
                    typedResponse.voter = (String) eventValues.getIndexedValues().get(0).getValue();
                    typedResponse.epochId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                    typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                    typedResponse.random = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                    typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                    typedResponse.votePowerNat = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                    typedResponse.votePowerAsset = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                }

            }

        }
    }
}
