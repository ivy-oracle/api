package com.ivy.api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Keys;

import com.ivy.api.constant.Address;
import com.ivy.api.dto.AccountDetailDTO;
import com.ivy.api.dto.FundMovementNodeDTO;
import com.ivy.api.dto.FundMovementTransactionDTO;
import com.ivy.api.dto.PaginatedDTO;
import com.ivy.api.repository.DelegationEventRepository;
import com.ivy.api.repository.EthTransactionRepository;
import com.ivy.api.repository.entity.DelegationEventEntity;
import com.ivy.api.util.CommonUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AccountService {

	private final EthTransactionRepository ethTransactionRepository;
	private final DelegationEventRepository delegationEventRepository;

	AccountService(EthTransactionRepository ethTransactionRepository,
			DelegationEventRepository delegationEventRepository) {
		this.ethTransactionRepository = ethTransactionRepository;
		this.delegationEventRepository = delegationEventRepository;
	}

	public AccountDetailDTO getAccountDetail(String address, Boolean excludeStatic, Pageable transactionsPageable,
			Pageable delegationPageable) {
		List<String> excludeAddresses = excludeStatic ? Address.Statics : List.of();
		var transactions = ethTransactionRepository.getByInvolvedAddress(address, excludeAddresses,
				transactionsPageable);
		var transactionsPaginated = new PaginatedDTO<>(transactions);

		var delegationHistory = new PaginatedDTO<DelegationEventEntity>(
				delegationEventRepository.findByFrom(Keys.toChecksumAddress(address),
						delegationPageable));
		var accountDetail = new AccountDetailDTO(address, transactionsPaginated, delegationHistory);
		return accountDetail;
	}

	public Map<String, FundMovementNodeDTO> getFundMovements(String fromAddress, Date fromDate, Date toDate,
			Integer levels,
			List<String> excludeAddresses) {
		Integer transactionLimit = 10;
		Integer nodeLimit = 5;

		Map<String, FundMovementNodeDTO> nodesMap = new HashMap<>();
		Queue<FundMovementNodeDTO> nodesQueue = new LinkedList<>();
		nodesQueue.add(new FundMovementNodeDTO(fromAddress, 0));

		var currentExcludeAddresses = new ArrayList<>(excludeAddresses);

		while (!nodesQueue.isEmpty()) {
			var node = nodesQueue.poll();
			nodesMap.put(node.getAddress(), node);
			currentExcludeAddresses.add(node.getAddress());

			List<String> toAddresses = ethTransactionRepository.getDistinctToAddressByFromAddressAndTimestamps(
					node.getAddress(), fromDate, toDate, currentExcludeAddresses, nodeLimit);
			Integer toAddressesCount = ethTransactionRepository.getDistinctToAddressCountByFromAddressAndTimestamps(
					node.getAddress(), fromDate, toDate, currentExcludeAddresses);
			node.setChildNodesCount(toAddressesCount);
			node.setHasMoreChildNodes(toAddressesCount > toAddresses.size());

			List<CompletableFuture<Void>> futures = new ArrayList<>();
			for (var toAddress : toAddresses) {
				var future = CompletableFuture.runAsync(() -> {
					var transactions = ethTransactionRepository.getByFromAddressAndToAddressAndTimestamps(
							node.getAddress(),
							toAddress,
							fromDate, toDate, transactionLimit);
					var transactionCount = ethTransactionRepository.getCountByFromAddressAndToAddressAndTimestamps(
							node.getAddress(),
							toAddress,
							fromDate, toDate);
					var transactionSum = ethTransactionRepository.getSumByFromAddressAndToAddressAndTimestamps(
							node.getAddress(),
							toAddress,
							fromDate, toDate);
					var fundMovementTransactions = transactions.stream().map(tx -> FundMovementTransactionDTO.of(tx))
							.toList();
					node.getInitiatedTransactions().getData()
							.addAll(fundMovementTransactions);
					node.getInitiatedTransactions().setTotalCount(transactionCount);
					node.getInitiatedTransactions().setLimit(transactionLimit);
					node.getInitiatedTransactions().setHasMore(transactionCount > transactionLimit);
					node.getInitiatedTransactions().setPage(1);
					node.getInitiatedTransactions().setAmount(CommonUtil.convertTokenToMiminalToken(transactionSum));

					var toNode = new FundMovementNodeDTO(toAddress, node.getLevel() + 1);
					toNode.setReceivedTransactions(node.getInitiatedTransactions());
					nodesQueue.add(toNode);
				});
				futures.add(future);
			}
			futures.stream().forEach(future -> future.join());
			if (node.getLevel() + 1 > levels) {
				break;
			}
		}

		while (!nodesQueue.isEmpty()) {
			var node = nodesQueue.poll();
			node.setInitiatedTransactions(null);
			nodesMap.put(node.getAddress(), node);
		}

		return nodesMap;
	}
}
