package com.ivy.api.cron;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.crypto.Keys;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.EthBlock.Block;
import org.web3j.tx.Contract;

import com.ivy.api.contract.VPContract;
import com.ivy.api.contract.VPContract.DelegateEventResponse;
import com.ivy.api.repository.DelegationEventRepository;
import com.ivy.api.repository.DelegationRepository;
import com.ivy.api.repository.entity.DelegationEventEntity;
import com.ivy.api.service.ContractService;

@Component
public class DelegationEventCron {
	Logger logger = LoggerFactory.getLogger(DelegationEventCron.class);

	@Value("${jobs.delegation-event.enabled:true}")
	private boolean isEnabled;

	@Value("${web3.rpc.block-limit:30}")
	private int rpcBlockLimit;

	private final Web3j web3j;
	private final ContractService contractService;
	private final DelegationEventRepository delegationEventRepository;
	private final DelegationRepository delegationRepository;

	public DelegationEventCron(
			Web3j web3j, ContractService contractService,
			DelegationEventRepository delegationEventRepository,
			DelegationRepository delegationRepository) {
		this.web3j = web3j;
		this.contractService = contractService;
		this.delegationEventRepository = delegationEventRepository;
		this.delegationRepository = delegationRepository;
	}

	@Scheduled(fixedDelay = 60 * 60 * 1000)
	public void fetchDelegateEvents() throws IOException {
		if (!isEnabled) {
			return;
		}

		VPContract vpContract = this.contractService.getVpContract();

		BigInteger fetchBlockSize = BigInteger.valueOf(rpcBlockLimit);

		// TODO: Set start block value by chain
		// coston: 43369, songbird: 458, flare: 40
		BigInteger startBlock = BigInteger.valueOf(458);
		BigInteger lastFetchedBlockNumber = this.delegationEventRepository.getLastBlockNumber();
		if (lastFetchedBlockNumber != null) {
			startBlock = startBlock
					.max(lastFetchedBlockNumber.subtract(fetchBlockSize.multiply(BigInteger.TWO)));
		}

		Block block = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false)
				.send().getBlock();
		BigInteger endBlock = block.getNumber();

		logger.info(String.format(
				"fetching delegate event from block %s to block %s",
				startBlock.toString(0),
				endBlock.toString(0)));

		String lastCompletionString = "";
		for (BigInteger i = startBlock; i.compareTo(endBlock) < 0; i = i
				.add(fetchBlockSize.min(endBlock.subtract(i)))) {
			BigInteger fromBlock = new BigInteger(String.valueOf(i));
			BigInteger toBlock = new BigInteger(
					String.valueOf(i.add(fetchBlockSize.subtract(BigInteger.ONE))));

			EthFilter filter = new EthFilter(
					DefaultBlockParameter.valueOf(fromBlock),
					DefaultBlockParameter.valueOf(toBlock),
					vpContract.getContractAddress());
			filter.addSingleTopic(EventEncoder.encode(VPContract.DELEGATE_EVENT));

			var logs = web3j.ethGetLogs(filter).send().getLogs();
			if (logs == null) {
				continue;
			}

			var delegationEntities = new ArrayList<DelegationEventEntity>();
			for (int logIndex = 0; logIndex < logs.size(); logIndex++) {
				Log log = (Log) logs.get(logIndex).get();
				EventValues eventValues = Contract.staticExtractEventParameters(
						VPContract.DELEGATE_EVENT, log);
				DelegateEventResponse typedResponse = new DelegateEventResponse();
				typedResponse.log = log;
				typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
				typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
				typedResponse.priorVotePower = (BigInteger) eventValues.getNonIndexedValues().get(0)
						.getValue();
				typedResponse.newVotePower = (BigInteger) eventValues.getNonIndexedValues().get(1)
						.getValue();

				logger.debug(typedResponse.from);

				var entity = new DelegationEventEntity();
				entity.setFrom(Keys.toChecksumAddress(typedResponse.from));
				entity.setTo(Keys.toChecksumAddress(typedResponse.to));
				entity.setPriorVotePower(typedResponse.priorVotePower);
				entity.setNewVotePower(typedResponse.newVotePower);
				entity.setBlockNumber(typedResponse.log.getBlockNumber());
				entity.setTransactionIndex(typedResponse.log.getTransactionIndex());
				entity.setTransactionHash(typedResponse.log.getTransactionHash());
				entity.setLogIndex(typedResponse.log.getLogIndex());

				delegationEntities.add(entity);
			}

			var blockNumbersSet = delegationEntities.stream().map(entity -> entity.getBlockNumber())
					.collect(Collectors.toSet());
			var fetchedDelegationEvents = this.delegationEventRepository
					.findAllByBlockNumberIn(new ArrayList<BigInteger>(blockNumbersSet));

			var fetchedDelegationEventsMap = new HashMap<String, DelegationEventEntity>();
			fetchedDelegationEvents.forEach(event -> fetchedDelegationEventsMap.put(
					event.getBlockNumber().toString() + "-" + event.getTransactionIndex().toString()
							+ "-"
							+ event.getLogIndex(),
					event));
			delegationEntities.removeIf(event -> fetchedDelegationEventsMap
					.containsKey(event.getBlockNumber().toString() + "-"
							+ event.getTransactionIndex().toString() + "-"
							+ event.getLogIndex()));
			this.delegationEventRepository.saveAll(delegationEntities);

			Double completion = toBlock.doubleValue() / endBlock.doubleValue() * 100;
			String completionString = String.valueOf(Math.round(completion)) + "%";

			if (!lastCompletionString.equals(completionString)) {
				lastCompletionString = completionString;
				logger.info(String.format(
						"fetch delegate event completion: %s (%s/%s)",
						completionString,
						toBlock.toString(),
						endBlock.toString()));
			}

		}

		logger.info("fetch delegate events completed, refreshing view...");

		this.delegationRepository.refreshMaterializedView();

		logger.info("delegation materialized view refreshed");
	}

}
