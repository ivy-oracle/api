package com.ivy.api.cron;

import java.io.IOException;
import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
import com.ivy.api.repository.PriceFinalizedEventRepository;
import com.ivy.api.repository.PriceRevealedEventRepository;
import com.ivy.api.repository.entity.PriceFinalizedEventEntity;
import com.ivy.api.repository.entity.PriceRevealedEventEntity;
import com.ivy.api.service.ContractService;

@Component
public class FTSOPriceCron {
	Logger logger = LoggerFactory.getLogger(FTSOPriceCron.class);

	@Value("${jobs.ftso-price.enabled:true}")
	private boolean isEnabled;

	private final Web3j web3j;
	private final ContractService contractService;
	private final PriceFinalizedEventRepository priceFinalizedEventEntityRepository;
	private final PriceRevealedEventRepository priceRevealedEventEntityRepository;

	public FTSOPriceCron(
			Web3j web3j,
			ContractService contractService,
			PriceFinalizedEventRepository priceFinalizedEventEntityRepository,
			PriceRevealedEventRepository priceRevealedEventEntityRepository) {
		this.web3j = web3j;
		this.contractService = contractService;
		this.priceFinalizedEventEntityRepository = priceFinalizedEventEntityRepository;
		this.priceRevealedEventEntityRepository = priceRevealedEventEntityRepository;
	}

	@Scheduled(cron = "*/180 * * * * *")
	public void fetchPrices() throws IOException {
		if (!isEnabled) {
			return;
		}

		int priceFinalizedFetchCount = 0;
		int priceRevealedFetchedCount = 0;

		BigInteger latestBlockNumber = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false)
				.send().getBlock().getNumber();

		// TODO: Do this concurrently
		for (String symbol : this.contractService.getFtsos().keySet()) {
			var ftso = this.contractService.getFtso(symbol);
			BigInteger fromBlock = latestBlockNumber.subtract(BigInteger.valueOf(250));
			BigInteger toBlock = latestBlockNumber.subtract(BigInteger.ONE);

			EthFilter priceFinalizedFilter = new EthFilter(
					DefaultBlockParameter.valueOf(fromBlock),
					DefaultBlockParameter.valueOf(toBlock),
					ftso.getContractAddress());
			priceFinalizedFilter.addSingleTopic(EventEncoder.encode(Ftso.PRICEFINALIZED_EVENT));
			var priceFinalizedLogs = web3j.ethGetLogs(priceFinalizedFilter).send().getLogs();

			for (int logIndex = 0; logIndex < priceFinalizedLogs.size(); logIndex++) {
				Log log = (Log) priceFinalizedLogs.get(logIndex).get();
				EventValues eventValues = Contract.staticExtractEventParameters(
						Ftso.PRICEFINALIZED_EVENT, log);
				PriceFinalizedEventEntity price = new PriceFinalizedEventEntity();

				price.setSymbol(symbol);
				price.setEpochId((BigInteger) eventValues.getIndexedValues().get(0).getValue());
				price.setPrice((BigInteger) eventValues.getNonIndexedValues().get(0).getValue());
				price.setRewardedFtso((Boolean) eventValues.getNonIndexedValues().get(1).getValue());
				price.setLowRewardPrice(
						(BigInteger) eventValues.getNonIndexedValues().get(2).getValue());
				price.setHighRewardPrice(
						(BigInteger) eventValues.getNonIndexedValues().get(3).getValue());
				price.setFinalizationType(
						(BigInteger) eventValues.getNonIndexedValues().get(4).getValue());
				price.setTimestamp((BigInteger) eventValues.getNonIndexedValues().get(5).getValue());

				var savedPrice = this.priceFinalizedEventEntityRepository.findByEpochIdAndSymbol(
						price.getEpochId(),
						symbol);
				if (savedPrice == null) {
					this.priceFinalizedEventEntityRepository.save(price);
					priceFinalizedFetchCount++;
				}
			}

			EthFilter priceRevealedFilter = new EthFilter(
					DefaultBlockParameter.valueOf(fromBlock),
					DefaultBlockParameter.valueOf(toBlock),
					ftso.getContractAddress());
			priceRevealedFilter.addSingleTopic(EventEncoder.encode(Ftso.PRICEREVEALED_EVENT));
			var priceRevealedLogs = web3j.ethGetLogs(priceRevealedFilter).send().getLogs();

			for (int logIndex = 0; logIndex < priceRevealedLogs.size(); logIndex++) {
				Log log = (Log) priceRevealedLogs.get(logIndex).get();
				EventValues eventValues = Contract.staticExtractEventParameters(
						Ftso.PRICEREVEALED_EVENT, log);
				PriceRevealedEventEntity price = new PriceRevealedEventEntity();

				price.setSymbol(symbol);
				price.setVoter((String) eventValues.getIndexedValues().get(0).getValue());
				price.setEpochId((BigInteger) eventValues.getIndexedValues().get(1).getValue());
				price.setPrice((BigInteger) eventValues.getNonIndexedValues().get(0).getValue());
				price.setRandom((BigInteger) eventValues.getNonIndexedValues().get(1).getValue());
				price.setTimestamp((BigInteger) eventValues.getNonIndexedValues().get(2).getValue());
				price.setVotePowerNat((BigInteger) eventValues.getNonIndexedValues().get(3).getValue());
				price.setVotePowerAsset(
						(BigInteger) eventValues.getNonIndexedValues().get(4).getValue());

				var savedPrice = this.priceRevealedEventEntityRepository
						.findByEpochIdAndSymbolAndVoter(price.getEpochId(), symbol,
								price.getVoter());
				if (savedPrice == null) {
					this.priceRevealedEventEntityRepository.save(price);
					priceRevealedFetchedCount++;
				}
			}
		}
		logger.info(String.format(
				"fetched %d finalized and %d revealed prices",
				priceFinalizedFetchCount,
				priceRevealedFetchedCount));
	}
}
