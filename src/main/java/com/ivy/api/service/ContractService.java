package com.ivy.api.service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Keys;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock.Block;
import org.web3j.tx.gas.DefaultGasProvider;

import com.ivy.api.contract.Ftso;
import com.ivy.api.contract.FtsoManager;
import com.ivy.api.contract.FtsoRegistry;
import com.ivy.api.contract.FtsoRewardManager;
import com.ivy.api.contract.PriceSubmitter;
import com.ivy.api.contract.VPContract;
import com.ivy.api.contract.VoterWhitelister;
import com.ivy.api.contract.WNat;

import lombok.Getter;

@Service
public class ContractService {
	private final Web3j web3j;

	@Value("${web3.chain:songbird}")
	private String chain;

	@Getter
	final PriceSubmitter priceSubmitter;

	@Getter
	final FtsoRegistry ftsoRegistry;

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

	@Getter
	private Map<String, Ftso> ftsos = new HashMap<>();

	public ContractService(Web3j web3j) throws Exception {
		this.web3j = web3j;

		Credentials dummyCredentials = Credentials.create(Keys.createEcKeyPair());

		this.priceSubmitter = PriceSubmitter.load(
				"0x1000000000000000000000000000000000000003",
				this.web3j,
				dummyCredentials, new DefaultGasProvider());

		this.ftsoRegistry = FtsoRegistry.load(
				this.priceSubmitter.getFtsoRegistry().send(),
				this.web3j,
				dummyCredentials, new DefaultGasProvider());

		var ftsoAddresses = this.ftsoRegistry.getAllFtsos().send();

		for (var ftsoAddress : ftsoAddresses) {
			if (!(ftsoAddress instanceof String)) {
				throw new Exception("ftso address should be string, but its not");
			}
			Ftso ftsoContract = chain == "songbird" ? com.ivy.api.contract.songbird.Ftso.load(
					(String) ftsoAddress,
					this.web3j,
					dummyCredentials, new DefaultGasProvider())
					: com.ivy.api.contract.flare.Ftso.load(
							(String) ftsoAddress,
							this.web3j,
							dummyCredentials, new DefaultGasProvider());
			this.ftsos.put(ftsoContract.symbol().send(), ftsoContract);
		}

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

	public Ftso getFtso(String symbol) {
		return this.ftsos.get(symbol);
	}

	public Block getLatestBlock() {
		Block latestBlock;
		try {
			latestBlock = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false)
					.send().getBlock();
		} catch (IOException e) {
			throw new ResponseStatusException(
					HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get latest block", e);
		}
		return latestBlock;
	}

	public BigInteger getLatestBlockNumber() {
		BigInteger latestBlockNumber;
		try {
			latestBlockNumber = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false)
					.send().getBlock().getNumber();
		} catch (IOException e) {
			throw new ResponseStatusException(
					HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get latest block number", e);
		}
		return latestBlockNumber;
	}
}
