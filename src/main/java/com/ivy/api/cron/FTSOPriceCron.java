package com.ivy.api.cron;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.crypto.Keys;
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
	ExecutorService executor = Executors.newFixedThreadPool(100);

	Logger logger = LoggerFactory.getLogger(FTSOPriceCron.class);

	@Value("${jobs.ftso-price.enabled:true}")
	private boolean isFetchPricesEnabled;

	@Value("${jobs.ftso-price-historical.enabled:true}")
	private boolean isFetchPricesHistoricalEnabled;

	@Value("${web3.rpc.block-limit:30}")
	private int rpcBlockLimit;

	private final Web3j web3j;
	private final ContractService contractService;
	private final PriceFinalizedEventRepository priceFinalizedEventRepository;
	private final PriceRevealedEventRepository priceRevealedEventRepository;

	public FTSOPriceCron(
			Web3j web3j,
			ContractService contractService,
			PriceFinalizedEventRepository priceFinalizedEventRepository,
			PriceRevealedEventRepository priceRevealedEventRepository) {
		this.web3j = web3j;
		this.contractService = contractService;
		this.priceFinalizedEventRepository = priceFinalizedEventRepository;
		this.priceRevealedEventRepository = priceRevealedEventRepository;
	}

	@Scheduled(cron = "*/180 * * * * *")
	public void fetchPrices() throws IOException {
		if (!this.isFetchPricesEnabled) {
			return;
		}

		BigInteger latestBlockNumber = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false)
				.send().getBlock().getNumber();
		BigInteger fromBlock = latestBlockNumber.subtract(BigInteger.valueOf(Math.min(rpcBlockLimit, 100)));
		BigInteger toBlock = latestBlockNumber.subtract(BigInteger.ONE);

		List<Future<Boolean>> futures = new ArrayList<>();
		for (String symbol : this.contractService.getFtsos().keySet()) {
			var future = executor.submit(new PriceFetchCallable(symbol, fromBlock, toBlock, contractService,
					priceFinalizedEventRepository, priceRevealedEventRepository));
			futures.add(future);
		}
		for (var future : futures) {
			try {
				future.get();
			} catch (ExecutionException e) {
				throw new ResponseStatusException(
						HttpStatus.INTERNAL_SERVER_ERROR, "failed to resolve FTSO data providers", e);
			} catch (InterruptedException e) {
				throw new ResponseStatusException(
						HttpStatus.INTERNAL_SERVER_ERROR, "failed to resolve FTSO data providers", e);
			}
		}

		logger.debug(String.format(
				"fetched finalized and revealed prices from block %s to %s", fromBlock.toString(), toBlock.toString()));
	}

	public class PriceFetchCallable implements Callable<Boolean> {
		private final String symbol;
		private final BigInteger fromBlock;
		private final BigInteger toBlock;
		private final ContractService contractService;
		private final PriceFinalizedEventRepository priceFinalizedEventRepository;
		private final PriceRevealedEventRepository priceRevealedEventRepository;

		public PriceFetchCallable(
				String symbol,
				BigInteger fromBlock,
				BigInteger toBlock,
				ContractService contractService,
				PriceFinalizedEventRepository priceFinalizedEventRepository,
				PriceRevealedEventRepository priceRevealedEventRepository) {
			this.symbol = symbol;
			this.fromBlock = fromBlock;
			this.toBlock = toBlock;
			this.contractService = contractService;
			this.priceFinalizedEventRepository = priceFinalizedEventRepository;
			this.priceRevealedEventRepository = priceRevealedEventRepository;
		}

		@Override
		public Boolean call() throws IOException {
			var ftso = this.contractService.getFtso(symbol);

			EthFilter priceFinalizedFilter = new EthFilter(
					DefaultBlockParameter.valueOf(fromBlock),
					DefaultBlockParameter.valueOf(toBlock),
					ftso.getContractAddress());
			priceFinalizedFilter.addSingleTopic(EventEncoder.encode(Ftso.PRICEFINALIZED_EVENT));
			var priceFinalizedLogs = web3j.ethGetLogs(priceFinalizedFilter).send().getLogs();
			if (priceFinalizedLogs != null) {
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

					var savedPrice = this.priceFinalizedEventRepository.findByEpochIdAndSymbol(
							price.getEpochId(),
							symbol);
					if (savedPrice == null) {
						this.priceFinalizedEventRepository.save(price);
					}
				}
			}

			EthFilter priceRevealedFilter = new EthFilter(
					DefaultBlockParameter.valueOf(fromBlock),
					DefaultBlockParameter.valueOf(toBlock),
					ftso.getContractAddress());
			priceRevealedFilter.addSingleTopic(EventEncoder.encode(Ftso.PRICEREVEALED_EVENT));
			var priceRevealedLogs = web3j.ethGetLogs(priceRevealedFilter).send().getLogs();

			if (priceRevealedLogs != null) {
				for (int logIndex = 0; logIndex < priceRevealedLogs.size(); logIndex++) {
					Log log = (Log) priceRevealedLogs.get(logIndex).get();
					EventValues eventValues = Contract.staticExtractEventParameters(
							Ftso.PRICEREVEALED_EVENT, log);
					PriceRevealedEventEntity price = new PriceRevealedEventEntity();

					price.setSymbol(symbol);
					price.setVoter(Keys.toChecksumAddress((String) eventValues.getIndexedValues().get(0).getValue()));
					price.setEpochId((BigInteger) eventValues.getIndexedValues().get(1).getValue());
					price.setPrice((BigInteger) eventValues.getNonIndexedValues().get(0).getValue());
					price.setRandom((BigInteger) eventValues.getNonIndexedValues().get(1).getValue());
					price.setTimestamp((BigInteger) eventValues.getNonIndexedValues().get(2).getValue());
					price.setVotePowerNat((BigInteger) eventValues.getNonIndexedValues().get(3).getValue());
					price.setVotePowerAsset(
							(BigInteger) eventValues.getNonIndexedValues().get(4).getValue());

					var savedPrice = this.priceRevealedEventRepository
							.findByEpochIdAndSymbolAndVoter(price.getEpochId(), symbol,
									price.getVoter());
					if (savedPrice == null) {
						this.priceRevealedEventRepository.save(price);
					}
				}
			}

			return true;
		};
	}
}
