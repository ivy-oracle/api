package com.ivy.api.service;

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
public class FTSODataProviderService {
	ExecutorService executor = Executors.newFixedThreadPool(120);

	private final ContractService contractService;
	private final FTSOService ftsoService;
	private final VoterWhitelister voterWhitelister;
	private final PriceRevealedEventRepository priceRevealedEventRepository;
	private final PriceFinalizedEventRepository priceFinalizedEventRepository;

	public FTSODataProviderService(
			ContractService contractService,
			FTSOService ftsoService,
			PriceRevealedEventRepository priceRevealedEventRepository,
			PriceFinalizedEventRepository priceFinalizedEventRepository) {
		this.contractService = contractService;
		this.ftsoService = ftsoService;
		this.voterWhitelister = this.contractService.getVoterWhitelister();
		this.priceRevealedEventRepository = priceRevealedEventRepository;
		this.priceFinalizedEventRepository = priceFinalizedEventRepository;
	}

	public List<String> getAllFTSODataProviderAddresses() {
		var votersAddressMap = this.fetchAllWhitelistedVoters();
		return new ArrayList<>(votersAddressMap.keySet());
	}

	public List<FTSODataProviderDTO> getAllFTSODataProviders() {
		var votersAddressMap = this.fetchAllWhitelistedVoters();

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
							providerSubmissionCountMap.getOrDefault(address, 0L).floatValue()
									/ totalSubmissionCount.floatValue())
					.accuracy(providerAccuracyMap.getOrDefault(address, 0f))
					.whitelistedSymbols(votersAddressMap.getOrDefault(address, List.of()))
					.build();
			var task = this.executor.submit(
					new FTSODataProviderWeb3Resolver(ftsoDataProviderDTO, rewardEpochDTO, this.contractService));
			tasks.add(task);
		});

		List<FTSODataProviderDTO> results = new ArrayList<>();
		for (var task : tasks) {
			try {
				results.add(task.get());
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

	public FTSODataProviderDTO getFTSODataProvider(String address) {
		var checkedSumAddress = Keys.toChecksumAddress(address);
		var votersAddressMap = this.fetchAllWhitelistedVoters();

		RewardEpochDTO rewardEpochDTO = this.ftsoService.getRewardEpoch();
		PriceEpochDTO priceEpochDTO = this.ftsoService.getPriceEpoch();
		BigInteger priceEpochId = priceEpochDTO.getEpochId().subtract(BigInteger.ONE);

		var submissionCount = this.priceRevealedEventRepository
				.getProviderSubmissionCountByAddress(checkedSumAddress, priceEpochId.subtract(BigInteger.valueOf(120)),
						priceEpochId)
				.getSubmissionCount();
		var totalSubmissionCount = this.priceFinalizedEventRepository.getCountInEpochRange(
				priceEpochId.subtract(BigInteger.valueOf(120)),
				priceEpochId);

		var accuracyDTO = this.priceFinalizedEventRepository.getProviderAccuracyByAddress(
				checkedSumAddress,
				priceEpochId.subtract(BigInteger.valueOf(120)),
				priceEpochId);

		FTSODataProviderDTO ftsoDataProviderDTO = FTSODataProviderDTO.builder()
				.address(checkedSumAddress)
				.availability(
						submissionCount.floatValue()
								/ totalSubmissionCount.floatValue())
				.accuracy(accuracyDTO.getAccuracy())
				.whitelistedSymbols(votersAddressMap.getOrDefault(checkedSumAddress, List.of()))
				.build();

		try {
			new FTSODataProviderWeb3Resolver(ftsoDataProviderDTO, rewardEpochDTO, this.contractService).call();
		} catch (Exception e) {
			throw new ResponseStatusException(
					HttpStatus.INTERNAL_SERVER_ERROR, "failed to resolve FTSO data provider", e);
		}
		return ftsoDataProviderDTO;
	}

	@Cacheable(value = "FTSODataProviderService.fetchAllWhitelistedVoters")
	private Map<String, List<String>> fetchAllWhitelistedVoters() {
		Tuple2<List<BigInteger>, List<String>> supportedIndicesAndSymbols;
		try {
			supportedIndicesAndSymbols = this.contractService.getFtsoRegistry().getSupportedIndicesAndSymbols().send();
		} catch (Exception e) {
			throw new ResponseStatusException(
					HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get supported FTSO indices and symbols", e);
		}

		Map<String, List<String>> votersAddressMap = new HashMap<String, List<String>>();

		for (String symbol : supportedIndicesAndSymbols.component2()) {
			try {
				var votersAddress = voterWhitelister.getFtsoWhitelistedPriceProvidersBySymbol(symbol).send();
				for (int i = 0; i < votersAddress.size(); i++) {
					var address = Keys.toChecksumAddress((String) votersAddress.get(i));
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

	@Cacheable(value = "FTSODataProviderService.getProviderLockedVotePower")
	private Double getProviderLockedVotePower(String address, BigInteger blockNumber) throws Exception {
		return CommonUtil.convertTokenToMiminalToken(
				this.contractService.getVpContract().votePowerOfAt(address, blockNumber).send());
	}

	@Cacheable(value = "FTSODataProviderService.getProviderCurrentVotePower")
	private Double getProviderCurrentVotePower(String address, BigInteger blockNumber) throws Exception {
		return CommonUtil.convertTokenToMiminalToken(this.contractService.getVpContract()
				.votePowerOfAt(address, blockNumber).send());
	}

	@Cacheable(value = "FTSODataProviderService.getProviderRewards")
	private Double getProviderRewards(String address, BigInteger epochId) throws Exception {
		return CommonUtil.convertTokenToMiminalToken(this.contractService.getFtsoRewardManager()
				.getStateOfRewardsFromDataProviders(address, epochId, List.of(address))
				.send()
				.component1().get(0));
	}

	@Cacheable(value = "FTSODataProviderService.getTotalRewards")
	private Double getTotalRewards(String address, BigInteger epochId) throws Exception {
		return CommonUtil.convertTokenToMiminalToken(
				this.contractService.getFtsoRewardManager().getUnclaimedReward(
						epochId, address).send().component1());
	}

	@Cacheable(value = "FTSODataProviderService.getScheduledFeeChanges")
	private List<FTSODataProviderScheduledFeeChangeDTO> getScheduledFeeChanges(String address) throws Exception {
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
			var lockedVotePower = FTSODataProviderService.this.getProviderLockedVotePower(address,
					this.rewardEpochDTO.getVotePowerLockBlockNumber());
			ftsoDataProviderDTO.setLockedVotePower(lockedVotePower);
			var currentVotePower = FTSODataProviderService.this.getProviderCurrentVotePower(address, latestBlockNumber);
			ftsoDataProviderDTO.setCurrentVotePower(currentVotePower);

			var providerRewards = FTSODataProviderService.this.getProviderRewards(address,
					this.rewardEpochDTO.getEpochId());
			ftsoDataProviderDTO.setProviderRewards(providerRewards);

			var totalRewards = FTSODataProviderService.this.getTotalRewards(address, this.rewardEpochDTO.getEpochId());
			ftsoDataProviderDTO.setTotalRewards(totalRewards);

			var rewardRate = totalRewards / lockedVotePower * (1 - ftsoDataProviderDTO.getFee());
			ftsoDataProviderDTO.setRewardRate((float) rewardRate);

			var feeChanges = FTSODataProviderService.this.getScheduledFeeChanges(address);
			ftsoDataProviderDTO.setScheduledFeeChanges(feeChanges);

			return ftsoDataProviderDTO;
		}
	}
}
