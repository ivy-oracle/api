package com.ivy.api.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.web3j.crypto.Keys;
import org.web3j.tuples.generated.Tuple2;

import com.ivy.api.contract.VoterWhitelister;
import com.ivy.api.dto.FTSODataProviderDTO;
import com.ivy.api.dto.FTSODataProviderScheduledFeeChangeDTO;
import com.ivy.api.dto.PriceEpochDTO;
import com.ivy.api.dto.RewardEpochDTO;
import com.ivy.api.repository.PriceFinalizedEventRepository;
import com.ivy.api.repository.PriceRevealedEventRepository;
import com.ivy.api.util.CommonUtil;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FTSODataProviderService {
	ExecutorService executor = Executors.newFixedThreadPool(120);

	/**
	 * Call internal methods using `self` to use caching
	 */
	FTSODataProviderService self;

	private final ContractService contractService;
	private final FTSOService ftsoService;
	private final VoterWhitelister voterWhitelister;
	private final PriceRevealedEventRepository priceRevealedEventRepository;
	private final PriceFinalizedEventRepository priceFinalizedEventRepository;

	public FTSODataProviderService(
			FTSODataProviderService self,
			ContractService contractService,
			FTSOService ftsoService,
			PriceRevealedEventRepository priceRevealedEventRepository,
			PriceFinalizedEventRepository priceFinalizedEventRepository) {
		this.self = self;
		this.contractService = contractService;
		this.ftsoService = ftsoService;
		this.voterWhitelister = this.contractService.getVoterWhitelister();
		this.priceRevealedEventRepository = priceRevealedEventRepository;
		this.priceFinalizedEventRepository = priceFinalizedEventRepository;
	}

	public List<String> getAllFTSODataProviderAddresses() {
		var votersAddressMap = self.fetchAllWhitelistedVoters();
		return new ArrayList<>(votersAddressMap.keySet());
	}

	@Cacheable(value = "FTSODataProviderService.getAllFTSODataProviders")
	public List<FTSODataProviderDTO> getAllFTSODataProviders() {
		var totalVotePowerFuture = this.contractService.wNat.totalVotePower().sendAsync();

		var votersAddressMap = self.fetchAllWhitelistedVoters();

		RewardEpochDTO rewardEpochDTO = this.ftsoService.getRewardEpoch();
		PriceEpochDTO priceEpochDTO = this.ftsoService.getPriceEpoch();
		BigInteger priceEpochId = priceEpochDTO.getEpochId().subtract(BigInteger.ONE);

		Map<String, Long> providerSubmissionCountMap = new HashMap<>();
		var providerSubmissionCounts = this.priceRevealedEventRepository.getProviderSubmissionCounts(
				priceEpochId.subtract(BigInteger.valueOf(120)),
				priceEpochId);
		providerSubmissionCounts.stream().forEach(psc -> {
			providerSubmissionCountMap.put(Keys.toChecksumAddress(psc.getAddress()), psc.getSubmissionCount());
		});
		var totalSubmissionCount = this.priceFinalizedEventRepository.getCountInEpochRange(
				priceEpochId.subtract(BigInteger.valueOf(120)),
				priceEpochId);

		Map<String, Float> providerAccuracyMap = new HashMap<>();
		var providerAccuracies = this.priceFinalizedEventRepository.getProviderAccuracies(
				priceEpochId.subtract(BigInteger.valueOf(120)),
				priceEpochId);
		providerAccuracies.forEach(providerAccuracy -> providerAccuracyMap.put(providerAccuracy.getAddress(),
				providerAccuracy.getAccuracy()));

		List<Future<FTSODataProviderDTO>> tasks = new ArrayList<>();
		votersAddressMap.forEach((address, whitelistedSymbols) -> {
			FTSODataProviderDTO ftsoDataProviderDTO = FTSODataProviderDTO.builder()
					.address(address)
					.availability(
							totalSubmissionCount.floatValue() != 0f
									? providerSubmissionCountMap.getOrDefault(address, 0L).floatValue()
											/ totalSubmissionCount.floatValue()
									: 0f)
					.accuracy(providerAccuracyMap.getOrDefault(address, 0f))
					.whitelistedSymbols(votersAddressMap.getOrDefault(address, List.of()))
					.build();
			var task = this.executor.submit(
					new FTSODataProviderWeb3Resolver(ftsoDataProviderDTO, rewardEpochDTO, this.contractService));
			tasks.add(task);
		});

		var totalVotePower = CommonUtil.convertTokenToMiminalToken(totalVotePowerFuture.join());

		List<FTSODataProviderDTO> results = new ArrayList<>();
		for (var task : tasks) {
			try {
				var result = task.get();
				result.setCurrentVotePowerPercentage(result.getCurrentVotePower() / totalVotePower);
				result.setLockedVotePowerPercentage(result.getLockedVotePower() / totalVotePower);
				results.add(result);
			} catch (ExecutionException e) {
				throw new ResponseStatusException(
						HttpStatus.INTERNAL_SERVER_ERROR, "failed to resolve FTSO data providers", e);
			} catch (InterruptedException e) {
				throw new ResponseStatusException(
						HttpStatus.INTERNAL_SERVER_ERROR, "failed to resolve FTSO data providers", e);
			}
		}

		return results;
	}

	@Cacheable(value = "FTSODataProviderService.getFTSODataProvider")
	public FTSODataProviderDTO getFTSODataProvider(String address) {
		var totalVotePowerFuture = this.contractService.wNat.totalVotePower().sendAsync();

		var checkedSumAddress = Keys.toChecksumAddress(address);
		var votersAddressMap = self.fetchAllWhitelistedVoters();

		RewardEpochDTO rewardEpochDTO = this.ftsoService.getRewardEpoch();
		PriceEpochDTO priceEpochDTO = this.ftsoService.getPriceEpoch();
		BigInteger priceEpochId = priceEpochDTO.getEpochId().subtract(BigInteger.ONE);

		var submissionCountDTO = this.priceRevealedEventRepository
				.getProviderSubmissionCountByAddress(checkedSumAddress, priceEpochId.subtract(BigInteger.valueOf(120)),
						priceEpochId);
		Long submissionCount = submissionCountDTO != null ? submissionCountDTO.getSubmissionCount() : 0L;

		var totalSubmissionCount = this.priceFinalizedEventRepository.getCountInEpochRange(
				priceEpochId.subtract(BigInteger.valueOf(120)),
				priceEpochId);

		var accuracyDTO = this.priceFinalizedEventRepository.getProviderAccuracyByAddress(
				checkedSumAddress,
				priceEpochId.subtract(BigInteger.valueOf(120)),
				priceEpochId);
		var accuracy = accuracyDTO != null ? accuracyDTO.getAccuracy() : 0f;

		FTSODataProviderDTO ftsoDataProviderDTO = FTSODataProviderDTO.builder()
				.address(checkedSumAddress)
				.availability(
						totalSubmissionCount.floatValue() != 0f ? submissionCount.floatValue()
								/ totalSubmissionCount.floatValue() : 0f)
				.accuracy(accuracy)
				.whitelistedSymbols(votersAddressMap.getOrDefault(checkedSumAddress, List.of()))
				.build();

		try {
			new FTSODataProviderWeb3Resolver(ftsoDataProviderDTO, rewardEpochDTO, this.contractService).call();
		} catch (Exception e) {
			throw new ResponseStatusException(
					HttpStatus.INTERNAL_SERVER_ERROR, "failed to resolve FTSO data provider", e);
		}

		var totalVotePower = CommonUtil.convertTokenToMiminalToken(totalVotePowerFuture.join());

		ftsoDataProviderDTO.setCurrentVotePowerPercentage(ftsoDataProviderDTO.getCurrentVotePower() / totalVotePower);
		ftsoDataProviderDTO.setLockedVotePowerPercentage(ftsoDataProviderDTO.getLockedVotePower() / totalVotePower);
		return ftsoDataProviderDTO;
	}

	@Cacheable(value = "FTSODataProviderService.fetchAllWhitelistedVoters")
	public Map<String, List<String>> fetchAllWhitelistedVoters() {
		Tuple2<List<BigInteger>, List<String>> supportedIndicesAndSymbols;
		try {
			supportedIndicesAndSymbols = this.contractService.getFtsoRegistry().getSupportedIndicesAndSymbols().send();
		} catch (Exception e) {
			throw new ResponseStatusException(
					HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get supported FTSO indices and symbols", e);
		}

		Map<String, List<String>> votersAddressMap = new HashMap<String, List<String>>();

		List<Future<List<String>>> futures = new ArrayList<>();
		for (String symbol : supportedIndicesAndSymbols.component2()) {
			var future = this.executor.submit(new Callable<List<String>>() {
				public List<String> call() {
					return fetchAllWhitelistedVotersBySymbol(symbol);
				}
			});
			futures.add(future);
		}
		for (int i = 0; i < futures.size(); i++) {
			try {
				var votersAddress = futures.get(i).get();
				var symbol = supportedIndicesAndSymbols.component2().get(i);

				voterWhitelister.getFtsoWhitelistedPriceProvidersBySymbol(symbol).send();
				for (int j = 0; j < votersAddress.size(); j++) {
					var address = Keys.toChecksumAddress((String) votersAddress.get(j));
					if (votersAddressMap.containsKey(address)) {
						var updatedWhitelistedSymbols = votersAddressMap.get(address).stream()
								.collect(Collectors.toList());
						updatedWhitelistedSymbols.add(symbol);
						votersAddressMap.put((String) address, updatedWhitelistedSymbols);
					} else {
						votersAddressMap.put((String) address, List.of(symbol));
					}
				}
			} catch (Exception e) {
				throw new ResponseStatusException(
						HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get whitelisted voters", e);
			}
		}
		return votersAddressMap;
	}

	private List<String> fetchAllWhitelistedVotersBySymbol(String symbol) {
		try {
			List<String> votersAddress = voterWhitelister.getFtsoWhitelistedPriceProvidersBySymbol(symbol).send();
			return votersAddress;
		} catch (Exception e) {
			throw new ResponseStatusException(
					HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get supported FTSO indices and symbols", e);
		}
	}

	@Cacheable(value = "FTSODataProviderService.getProviderLockedVotePower")
	public Double getProviderLockedVotePower(String address, BigInteger blockNumber) throws Exception {
		return CommonUtil.convertTokenToMiminalToken(
				this.contractService.getVpContract().votePowerOfAt(address, blockNumber).send());
	}

	@Cacheable(value = "FTSODataProviderService.getProviderCurrentVotePower")
	public Double getProviderCurrentVotePower(String address, BigInteger blockNumber) throws Exception {
		return CommonUtil.convertTokenToMiminalToken(this.contractService.getVpContract()
				.votePowerOfAt(address, blockNumber).send());
	}

	@Cacheable(value = "FTSODataProviderService.getProviderRewards")
	public Double getProviderRewards(String address, BigInteger epochId) throws Exception {
		var stateOfRewards = this.contractService.getFtsoRewardManager()
				.getStateOfRewardsFromDataProviders(address, epochId, List.of(address))
				.send()
				.component1();
		if (stateOfRewards.size() == 0) {
			return 0d;
		}
		return CommonUtil.convertTokenToMiminalToken(stateOfRewards.get(0));
	}

	@Cacheable(value = "FTSODataProviderService.getTotalRewards")
	public Double getTotalRewards(String address, BigInteger epochId) throws Exception {
		return CommonUtil.convertTokenToMiminalToken(
				this.contractService.getFtsoRewardManager().getUnclaimedReward(
						epochId, address).send().component1());
	}

	@Cacheable(value = "FTSODataProviderService.getScheduledFeeChanges")
	public List<FTSODataProviderScheduledFeeChangeDTO> getScheduledFeeChanges(String address) throws Exception {
		var rawFeeChanges = this.contractService.getFtsoRewardManager()
				.getDataProviderScheduledFeePercentageChanges(address).send();
		List<FTSODataProviderScheduledFeeChangeDTO> feeChanges = new ArrayList<>();
		for (int i = 0; i < rawFeeChanges.component1().size(); i++) {
			feeChanges.add(new FTSODataProviderScheduledFeeChangeDTO(
					rawFeeChanges.component1().get(i).floatValue() / 10000,
					rawFeeChanges.component2().get(i).intValue()));
		}
		return feeChanges;
	}

	private class FTSODataProviderWeb3Resolver implements Callable<FTSODataProviderDTO> {

		private FTSODataProviderDTO ftsoDataProviderDTO;
		private RewardEpochDTO rewardEpochDTO;

		private final ContractService contractService;

		public FTSODataProviderWeb3Resolver(
				FTSODataProviderDTO ftsoDataProviderDTO,
				RewardEpochDTO rewardEpochDTO,
				ContractService contractService) {
			this.ftsoDataProviderDTO = ftsoDataProviderDTO;
			this.rewardEpochDTO = rewardEpochDTO;
			this.contractService = contractService;
		}

		@Override
		public FTSODataProviderDTO call() throws Exception {
			String address = this.ftsoDataProviderDTO.getAddress();
			BigInteger latestBlockNumber = this.contractService.getLatestBlockNumber();

			var fee = this.contractService.getFtsoRewardManager()
					.getDataProviderCurrentFeePercentage(address).send();
			ftsoDataProviderDTO.setFee(fee.floatValue() / 10000);

			// TODO: Use VPContract batchVotePowerOfAt instead
			var lockedVotePower = FTSODataProviderService.this.self.getProviderLockedVotePower(address,
					this.rewardEpochDTO.getVotePowerLockBlockNumber());
			ftsoDataProviderDTO.setLockedVotePower(lockedVotePower);
			var currentVotePower = FTSODataProviderService.this.self.getProviderCurrentVotePower(address,
					latestBlockNumber);
			ftsoDataProviderDTO.setCurrentVotePower(currentVotePower);

			var providerRewards = FTSODataProviderService.this.self.getProviderRewards(address,
					this.rewardEpochDTO.getEpochId());
			ftsoDataProviderDTO.setProviderRewards(providerRewards);

			var totalRewards = FTSODataProviderService.this.self.getTotalRewards(address,
					this.rewardEpochDTO.getEpochId());
			ftsoDataProviderDTO.setTotalRewards(totalRewards);

			var rewardRate = totalRewards / lockedVotePower * (1 - ftsoDataProviderDTO.getFee());
			ftsoDataProviderDTO.setRewardRate((float) rewardRate);

			var feeChanges = FTSODataProviderService.this.self.getScheduledFeeChanges(address);
			ftsoDataProviderDTO.setScheduledFeeChanges(feeChanges);

			return ftsoDataProviderDTO;
		}
	}
}
