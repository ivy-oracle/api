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
import java.util.stream.Collectors;

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

	@Value("${web3.chain:songbird}")
	private String chain;

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

		logger.debug("fetch ftso price revealed and finalized");

		BigInteger latestBlockNumber;
		try {
			latestBlockNumber = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false)
					.send().getBlock().getNumber();
		} catch (IOException e) {
			logger.error("failed to fetch latest block number", e);
			return;
		}

		BigInteger fromBlock = latestBlockNumber.subtract(BigInteger.valueOf(Math.min(rpcBlockLimit, 100)));
		BigInteger toBlock = latestBlockNumber.subtract(BigInteger.ONE);

		List<Future<List<PriceRevealedEventEntity>>> priceRevealedFutures = new ArrayList<>();
		for (String symbol : this.contractService.getFtsos().keySet()) {
			var priceRevealedFuture = executor
					.submit(new PriceRevealedFetchCallable(symbol, fromBlock, toBlock, contractService,
							priceFinalizedEventRepository, priceRevealedEventRepository));
			priceRevealedFutures.add(priceRevealedFuture);
		}

		List<PriceRevealedEventEntity> priceRevealedEventEntities = new ArrayList<>();
		for (var future : priceRevealedFutures) {
			try {
				priceRevealedEventEntities.addAll(future.get());
			} catch (ExecutionException e) {
				throw new ResponseStatusException(
						HttpStatus.INTERNAL_SERVER_ERROR, "failed to resolve FTSO data providers", e);
			} catch (InterruptedException e) {
				throw new ResponseStatusException(
						HttpStatus.INTERNAL_SERVER_ERROR, "failed to resolve FTSO data providers", e);
			}
		}
		var fetchedRevealedPriceEpochIds = priceRevealedEventEntities.stream().map(price -> price.getEpochId())
				.collect(Collectors.toSet());
		var existingRevealedPriceKeys = this.priceRevealedEventRepository
				.findAllByEpochIdIn(fetchedRevealedPriceEpochIds).stream()
				.map(price -> price.getEpochId().toString() + price.getSymbol() + price.getVoter())
				.collect(Collectors.toList());
		List<PriceRevealedEventEntity> priceRevealedEventEntitiesToPersist = new ArrayList<>();
		for (var price : priceRevealedEventEntities) {
			if (!existingRevealedPriceKeys
					.contains(price.getEpochId().toString() + price.getSymbol() + price.getVoter())) {
				priceRevealedEventEntitiesToPersist.add(price);
			}
		}
		if (priceRevealedEventEntitiesToPersist.size() > 0) {
			this.priceRevealedEventRepository.saveAll(priceRevealedEventEntitiesToPersist);
		}

		List<Future<List<PriceFinalizedEventEntity>>> priceFinalizedFutures = new ArrayList<>();
		for (String symbol : this.contractService.getFtsos().keySet()) {
			var priceFinalizedFuture = executor
					.submit(new PriceFinalizedFetchCallable(symbol, fromBlock, toBlock, contractService,
							priceFinalizedEventRepository, priceRevealedEventRepository));
			priceFinalizedFutures.add(priceFinalizedFuture);
		}

		List<PriceFinalizedEventEntity> priceFinalizedEventEntities = new ArrayList<>();
		for (var future : priceFinalizedFutures) {
			try {
				priceFinalizedEventEntities.addAll(future.get());
			} catch (ExecutionException e) {
				throw new ResponseStatusException(
						HttpStatus.INTERNAL_SERVER_ERROR, "failed to resolve FTSO data providers", e);
			} catch (InterruptedException e) {
				throw new ResponseStatusException(
						HttpStatus.INTERNAL_SERVER_ERROR, "failed to resolve FTSO data providers", e);
			}
		}
		var fetchedEpochIds = priceFinalizedEventEntities.stream().map(price -> price.getEpochId())
				.collect(Collectors.toSet());
		var existingFinalizedPrices = this.priceFinalizedEventRepository.findAllByEpochIdIn(fetchedEpochIds);
		var existingFinalizedPricesKeys = existingFinalizedPrices.stream()
				.map(price -> price.getEpochId().toString() + price.getSymbol()).collect(Collectors.toList());
		List<PriceFinalizedEventEntity> priceFinalizedEventEntitiesToPersist = new ArrayList<>();
		for (var price : priceFinalizedEventEntities) {
			if (!existingFinalizedPricesKeys.contains(price.getEpochId().toString() + price.getSymbol())) {
				priceFinalizedEventEntitiesToPersist.add(price);
			}
		}
		if (priceFinalizedEventEntitiesToPersist.size() > 0) {
			this.priceFinalizedEventRepository.saveAll(priceFinalizedEventEntitiesToPersist);
		}

		logger.debug(String.format(
				"fetched %d finalized and %d revealed prices from block %s to %s",
				priceFinalizedEventEntitiesToPersist.size(),
				priceRevealedEventEntitiesToPersist.size(),
				fromBlock.toString(), toBlock.toString()));
	}

	public class PriceFinalizedFetchCallable implements Callable<List<PriceFinalizedEventEntity>> {
		private final String symbol;
		private final BigInteger fromBlock;
		private final BigInteger toBlock;
		private final ContractService contractService;

		public PriceFinalizedFetchCallable(
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
		}

		@Override
		public List<PriceFinalizedEventEntity> call() throws IOException {
			var ftso = this.contractService.getFtso(symbol);
			List<PriceFinalizedEventEntity> prices = new ArrayList<>();

			EthFilter priceFinalizedFilter = new EthFilter(
					DefaultBlockParameter.valueOf(fromBlock),
					DefaultBlockParameter.valueOf(toBlock),
					ftso.getContractAddress());

			var priceFinalizedEvent = chain == "songbird" ? com.ivy.api.contract.songbird.Ftso.PRICEFINALIZED_EVENT
					: com.ivy.api.contract.flare.Ftso.PRICEFINALIZED_EVENT;

			priceFinalizedFilter.addSingleTopic(EventEncoder.encode(priceFinalizedEvent));
			var priceFinalizedLogs = web3j.ethGetLogs(priceFinalizedFilter).send().getLogs();
			if (priceFinalizedLogs != null) {
				for (int logIndex = 0; logIndex < priceFinalizedLogs.size(); logIndex++) {
					Log log = (Log) priceFinalizedLogs.get(logIndex).get();
					EventValues eventValues = Contract.staticExtractEventParameters(
							priceFinalizedEvent, log);
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

					prices.add(price);
				}
			}
			return prices;
		};
	}

	public class PriceRevealedFetchCallable implements Callable<List<PriceRevealedEventEntity>> {
		private final String symbol;
		private final BigInteger fromBlock;
		private final BigInteger toBlock;
		private final ContractService contractService;

		public PriceRevealedFetchCallable(
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
		}

		@Override
		public List<PriceRevealedEventEntity> call() throws IOException {
			var ftso = this.contractService.getFtso(symbol);
			List<PriceRevealedEventEntity> prices = new ArrayList<>();

			EthFilter priceRevealedFilter = new EthFilter(
					DefaultBlockParameter.valueOf(fromBlock),
					DefaultBlockParameter.valueOf(toBlock),
					ftso.getContractAddress());

			var priceRevealedEvent = chain == "songbird" ? com.ivy.api.contract.songbird.Ftso.PRICEREVEALED_EVENT
					: com.ivy.api.contract.flare.Ftso.PRICEREVEALED_EVENT;
			priceRevealedFilter.addSingleTopic(EventEncoder.encode(priceRevealedEvent));
			var priceRevealedLogs = web3j.ethGetLogs(priceRevealedFilter).send().getLogs();

			if (priceRevealedLogs != null) {
				for (int logIndex = 0; logIndex < priceRevealedLogs.size(); logIndex++) {
					Log log = (Log) priceRevealedLogs.get(logIndex).get();
					EventValues eventValues = Contract.staticExtractEventParameters(
							priceRevealedEvent, log);
					PriceRevealedEventEntity price = new PriceRevealedEventEntity();

					price.setSymbol(symbol);
					price.setVoter(Keys.toChecksumAddress((String) eventValues.getIndexedValues().get(0).getValue()));
					price.setEpochId((BigInteger) eventValues.getIndexedValues().get(1).getValue());
					price.setPrice((BigInteger) eventValues.getNonIndexedValues().get(0).getValue());
					if (chain == "songbird") {
						price.setRandom((BigInteger) eventValues.getNonIndexedValues().get(1).getValue());
						price.setTimestamp((BigInteger) eventValues.getNonIndexedValues().get(2).getValue());
						price.setVotePowerNat((BigInteger) eventValues.getNonIndexedValues().get(3).getValue());
						price.setVotePowerAsset(
								(BigInteger) eventValues.getNonIndexedValues().get(4).getValue());
					} else {
						// flare
						price.setRandom(BigInteger.ZERO);
						price.setTimestamp((BigInteger) eventValues.getNonIndexedValues().get(1).getValue());
						price.setVotePowerNat((BigInteger) eventValues.getNonIndexedValues().get(2).getValue());
						price.setVotePowerAsset(
								(BigInteger) eventValues.getNonIndexedValues().get(3).getValue());
					}

					prices.add(price);
				}
			}

			return prices;
		};
	}
}
