package com.ivy.api.contract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class FtsoRewardManager extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_ACTIVATE = "activate";

    public static final String FUNC_ACTIVE = "active";

    public static final String FUNC_CLAIMGOVERNANCE = "claimGovernance";

    public static final String FUNC_CLAIMREWARD = "claimReward";

    public static final String FUNC_CLAIMREWARDFROMDATAPROVIDERS = "claimRewardFromDataProviders";

    public static final String FUNC_CLOSEEXPIREDREWARDEPOCH = "closeExpiredRewardEpoch";

    public static final String FUNC_DAILYAUTHORIZEDINFLATION = "dailyAuthorizedInflation";

    public static final String FUNC_DEACTIVATE = "deactivate";

    public static final String FUNC_DEFAULTFEEPERCENTAGE = "defaultFeePercentage";

    public static final String FUNC_DISTRIBUTEREWARDS = "distributeRewards";

    public static final String FUNC_FEEPERCENTAGEUPDATEOFFSET = "feePercentageUpdateOffset";

    public static final String FUNC_FTSOMANAGER = "ftsoManager";

    public static final String FUNC_GETCLAIMEDREWARD = "getClaimedReward";

    public static final String FUNC_GETDATAPROVIDERCURRENTFEEPERCENTAGE = "getDataProviderCurrentFeePercentage";

    public static final String FUNC_GETDATAPROVIDERSCHEDULEDFEEPERCENTAGECHANGES = "getDataProviderScheduledFeePercentageChanges";

    public static final String FUNC_GETEPOCHREWARD = "getEpochReward";

    public static final String FUNC_GETEPOCHSWITHCLAIMABLEREWARDS = "getEpochsWithClaimableRewards";

    public static final String FUNC_GETEPOCHSWITHUNCLAIMEDREWARDS = "getEpochsWithUnclaimedRewards";

    public static final String FUNC_GETINFLATIONADDRESS = "getInflationAddress";

    public static final String FUNC_GETREWARDEPOCHTOEXPIRENEXT = "getRewardEpochToExpireNext";

    public static final String FUNC_GETSTATEOFREWARDS = "getStateOfRewards";

    public static final String FUNC_GETSTATEOFREWARDSFROMDATAPROVIDERS = "getStateOfRewardsFromDataProviders";

    public static final String FUNC_GETTOKENPOOLSUPPLYDATA = "getTokenPoolSupplyData";

    public static final String FUNC_GETUNCLAIMEDREWARD = "getUnclaimedReward";

    public static final String FUNC_GOVERNANCE = "governance";

    public static final String FUNC_INITIALISE = "initialise";

    public static final String FUNC_LASTINFLATIONAUTHORIZATIONRECEIVEDTS = "lastInflationAuthorizationReceivedTs";

    public static final String FUNC_PROPOSEGOVERNANCE = "proposeGovernance";

    public static final String FUNC_PROPOSEDGOVERNANCE = "proposedGovernance";

    public static final String FUNC_RECEIVEINFLATION = "receiveInflation";

    public static final String FUNC_SETCONTRACTADDRESSES = "setContractAddresses";

    public static final String FUNC_SETDAILYAUTHORIZEDINFLATION = "setDailyAuthorizedInflation";

    public static final String FUNC_SETDATAPROVIDERFEEPERCENTAGE = "setDataProviderFeePercentage";

    public static final String FUNC_TOTALAWARDEDWEI = "totalAwardedWei";

    public static final String FUNC_TOTALCLAIMEDWEI = "totalClaimedWei";

    public static final String FUNC_TOTALEXPIREDWEI = "totalExpiredWei";

    public static final String FUNC_TOTALINFLATIONAUTHORIZEDWEI = "totalInflationAuthorizedWei";

    public static final String FUNC_TOTALINFLATIONRECEIVEDWEI = "totalInflationReceivedWei";

    public static final String FUNC_TOTALSELFDESTRUCTRECEIVEDWEI = "totalSelfDestructReceivedWei";

    public static final String FUNC_TRANSFERGOVERNANCE = "transferGovernance";

    public static final String FUNC_WNAT = "wNat";

    public static final Event DAILYAUTHORIZEDINFLATIONSET_EVENT = new Event("DailyAuthorizedInflationSet", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event FEEPERCENTAGECHANGED_EVENT = new Event("FeePercentageChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event GOVERNANCEPROPOSED_EVENT = new Event("GovernanceProposed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event GOVERNANCEUPDATED_EVENT = new Event("GovernanceUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event INFLATIONRECEIVED_EVENT = new Event("InflationReceived", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event REWARDCLAIMED_EVENT = new Event("RewardClaimed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event REWARDCLAIMSEXPIRED_EVENT = new Event("RewardClaimsExpired", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event REWARDSDISTRIBUTED_EVENT = new Event("RewardsDistributed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicArray<Address>>() {}, new TypeReference<DynamicArray<Uint256>>() {}));
    ;

    @Deprecated
    protected FtsoRewardManager(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected FtsoRewardManager(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected FtsoRewardManager(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected FtsoRewardManager(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<DailyAuthorizedInflationSetEventResponse> getDailyAuthorizedInflationSetEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(DAILYAUTHORIZEDINFLATIONSET_EVENT, transactionReceipt);
        ArrayList<DailyAuthorizedInflationSetEventResponse> responses = new ArrayList<DailyAuthorizedInflationSetEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DailyAuthorizedInflationSetEventResponse typedResponse = new DailyAuthorizedInflationSetEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.authorizedAmountWei = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<DailyAuthorizedInflationSetEventResponse> dailyAuthorizedInflationSetEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, DailyAuthorizedInflationSetEventResponse>() {
            @Override
            public DailyAuthorizedInflationSetEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(DAILYAUTHORIZEDINFLATIONSET_EVENT, log);
                DailyAuthorizedInflationSetEventResponse typedResponse = new DailyAuthorizedInflationSetEventResponse();
                typedResponse.log = log;
                typedResponse.authorizedAmountWei = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DailyAuthorizedInflationSetEventResponse> dailyAuthorizedInflationSetEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DAILYAUTHORIZEDINFLATIONSET_EVENT));
        return dailyAuthorizedInflationSetEventFlowable(filter);
    }

    public List<FeePercentageChangedEventResponse> getFeePercentageChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(FEEPERCENTAGECHANGED_EVENT, transactionReceipt);
        ArrayList<FeePercentageChangedEventResponse> responses = new ArrayList<FeePercentageChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FeePercentageChangedEventResponse typedResponse = new FeePercentageChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.dataProvider = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.validFromEpoch = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<FeePercentageChangedEventResponse> feePercentageChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, FeePercentageChangedEventResponse>() {
            @Override
            public FeePercentageChangedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(FEEPERCENTAGECHANGED_EVENT, log);
                FeePercentageChangedEventResponse typedResponse = new FeePercentageChangedEventResponse();
                typedResponse.log = log;
                typedResponse.dataProvider = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.validFromEpoch = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<FeePercentageChangedEventResponse> feePercentageChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FEEPERCENTAGECHANGED_EVENT));
        return feePercentageChangedEventFlowable(filter);
    }

    public List<GovernanceProposedEventResponse> getGovernanceProposedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(GOVERNANCEPROPOSED_EVENT, transactionReceipt);
        ArrayList<GovernanceProposedEventResponse> responses = new ArrayList<GovernanceProposedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            GovernanceProposedEventResponse typedResponse = new GovernanceProposedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.proposedGovernance = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<GovernanceProposedEventResponse> governanceProposedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, GovernanceProposedEventResponse>() {
            @Override
            public GovernanceProposedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(GOVERNANCEPROPOSED_EVENT, log);
                GovernanceProposedEventResponse typedResponse = new GovernanceProposedEventResponse();
                typedResponse.log = log;
                typedResponse.proposedGovernance = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<GovernanceProposedEventResponse> governanceProposedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(GOVERNANCEPROPOSED_EVENT));
        return governanceProposedEventFlowable(filter);
    }

    public List<GovernanceUpdatedEventResponse> getGovernanceUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(GOVERNANCEUPDATED_EVENT, transactionReceipt);
        ArrayList<GovernanceUpdatedEventResponse> responses = new ArrayList<GovernanceUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            GovernanceUpdatedEventResponse typedResponse = new GovernanceUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldGovernance = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.newGoveranance = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<GovernanceUpdatedEventResponse> governanceUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, GovernanceUpdatedEventResponse>() {
            @Override
            public GovernanceUpdatedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(GOVERNANCEUPDATED_EVENT, log);
                GovernanceUpdatedEventResponse typedResponse = new GovernanceUpdatedEventResponse();
                typedResponse.log = log;
                typedResponse.oldGovernance = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.newGoveranance = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<GovernanceUpdatedEventResponse> governanceUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(GOVERNANCEUPDATED_EVENT));
        return governanceUpdatedEventFlowable(filter);
    }

    public List<InflationReceivedEventResponse> getInflationReceivedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(INFLATIONRECEIVED_EVENT, transactionReceipt);
        ArrayList<InflationReceivedEventResponse> responses = new ArrayList<InflationReceivedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            InflationReceivedEventResponse typedResponse = new InflationReceivedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.amountReceivedWei = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<InflationReceivedEventResponse> inflationReceivedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, InflationReceivedEventResponse>() {
            @Override
            public InflationReceivedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(INFLATIONRECEIVED_EVENT, log);
                InflationReceivedEventResponse typedResponse = new InflationReceivedEventResponse();
                typedResponse.log = log;
                typedResponse.amountReceivedWei = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<InflationReceivedEventResponse> inflationReceivedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(INFLATIONRECEIVED_EVENT));
        return inflationReceivedEventFlowable(filter);
    }

    public List<RewardClaimedEventResponse> getRewardClaimedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(REWARDCLAIMED_EVENT, transactionReceipt);
        ArrayList<RewardClaimedEventResponse> responses = new ArrayList<RewardClaimedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RewardClaimedEventResponse typedResponse = new RewardClaimedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.dataProvider = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.whoClaimed = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.sentTo = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.rewardEpoch = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RewardClaimedEventResponse> rewardClaimedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, RewardClaimedEventResponse>() {
            @Override
            public RewardClaimedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(REWARDCLAIMED_EVENT, log);
                RewardClaimedEventResponse typedResponse = new RewardClaimedEventResponse();
                typedResponse.log = log;
                typedResponse.dataProvider = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.whoClaimed = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.sentTo = (String) eventValues.getIndexedValues().get(2).getValue();
                typedResponse.rewardEpoch = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RewardClaimedEventResponse> rewardClaimedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REWARDCLAIMED_EVENT));
        return rewardClaimedEventFlowable(filter);
    }

    public List<RewardClaimsExpiredEventResponse> getRewardClaimsExpiredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(REWARDCLAIMSEXPIRED_EVENT, transactionReceipt);
        ArrayList<RewardClaimsExpiredEventResponse> responses = new ArrayList<RewardClaimsExpiredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RewardClaimsExpiredEventResponse typedResponse = new RewardClaimsExpiredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.rewardEpochId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RewardClaimsExpiredEventResponse> rewardClaimsExpiredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, RewardClaimsExpiredEventResponse>() {
            @Override
            public RewardClaimsExpiredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(REWARDCLAIMSEXPIRED_EVENT, log);
                RewardClaimsExpiredEventResponse typedResponse = new RewardClaimsExpiredEventResponse();
                typedResponse.log = log;
                typedResponse.rewardEpochId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RewardClaimsExpiredEventResponse> rewardClaimsExpiredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REWARDCLAIMSEXPIRED_EVENT));
        return rewardClaimsExpiredEventFlowable(filter);
    }

    public List<RewardsDistributedEventResponse> getRewardsDistributedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(REWARDSDISTRIBUTED_EVENT, transactionReceipt);
        ArrayList<RewardsDistributedEventResponse> responses = new ArrayList<RewardsDistributedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RewardsDistributedEventResponse typedResponse = new RewardsDistributedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ftso = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.epochId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.addresses = (List<String>) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.rewards = (List<BigInteger>) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RewardsDistributedEventResponse> rewardsDistributedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, RewardsDistributedEventResponse>() {
            @Override
            public RewardsDistributedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(REWARDSDISTRIBUTED_EVENT, log);
                RewardsDistributedEventResponse typedResponse = new RewardsDistributedEventResponse();
                typedResponse.log = log;
                typedResponse.ftso = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.epochId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.addresses = (List<String>) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.rewards = (List<BigInteger>) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RewardsDistributedEventResponse> rewardsDistributedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REWARDSDISTRIBUTED_EVENT));
        return rewardsDistributedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> activate() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ACTIVATE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> active() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ACTIVE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> claimGovernance() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CLAIMGOVERNANCE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> claimReward(String _recipient, List<BigInteger> _rewardEpochs) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CLAIMREWARD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _recipient), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_rewardEpochs, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> claimRewardFromDataProviders(String _recipient, List<BigInteger> _rewardEpochs, List<String> _dataProviders) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CLAIMREWARDFROMDATAPROVIDERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _recipient), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_rewardEpochs, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_dataProviders, org.web3j.abi.datatypes.Address.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> closeExpiredRewardEpoch(BigInteger _rewardEpoch) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CLOSEEXPIREDREWARDEPOCH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_rewardEpoch)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> dailyAuthorizedInflation() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DAILYAUTHORIZEDINFLATION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> deactivate() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEACTIVATE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> defaultFeePercentage() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DEFAULTFEEPERCENTAGE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> distributeRewards(List<String> _addresses, List<BigInteger> _weights, BigInteger _totalWeight, BigInteger _epochId, String _ftso, BigInteger _priceEpochDurationSeconds, BigInteger _currentRewardEpoch, BigInteger _priceEpochEndTime, BigInteger _votePowerBlock) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DISTRIBUTEREWARDS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_addresses, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_weights, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.generated.Uint256(_totalWeight), 
                new org.web3j.abi.datatypes.generated.Uint256(_epochId), 
                new org.web3j.abi.datatypes.Address(160, _ftso), 
                new org.web3j.abi.datatypes.generated.Uint256(_priceEpochDurationSeconds), 
                new org.web3j.abi.datatypes.generated.Uint256(_currentRewardEpoch), 
                new org.web3j.abi.datatypes.generated.Uint256(_priceEpochEndTime), 
                new org.web3j.abi.datatypes.generated.Uint256(_votePowerBlock)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> feePercentageUpdateOffset() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_FEEPERCENTAGEUPDATEOFFSET, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> ftsoManager() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_FTSOMANAGER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Tuple2<Boolean, BigInteger>> getClaimedReward(BigInteger _rewardEpoch, String _dataProvider, String _claimer) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCLAIMEDREWARD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_rewardEpoch), 
                new org.web3j.abi.datatypes.Address(160, _dataProvider), 
                new org.web3j.abi.datatypes.Address(160, _claimer)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple2<Boolean, BigInteger>>(function,
                new Callable<Tuple2<Boolean, BigInteger>>() {
                    @Override
                    public Tuple2<Boolean, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<Boolean, BigInteger>(
                                (Boolean) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> getDataProviderCurrentFeePercentage(String _dataProvider) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETDATAPROVIDERCURRENTFEEPERCENTAGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _dataProvider)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple3<List<BigInteger>, List<BigInteger>, List<Boolean>>> getDataProviderScheduledFeePercentageChanges(String _dataProvider) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETDATAPROVIDERSCHEDULEDFEEPERCENTAGECHANGES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _dataProvider)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Bool>>() {}));
        return new RemoteFunctionCall<Tuple3<List<BigInteger>, List<BigInteger>, List<Boolean>>>(function,
                new Callable<Tuple3<List<BigInteger>, List<BigInteger>, List<Boolean>>>() {
                    @Override
                    public Tuple3<List<BigInteger>, List<BigInteger>, List<Boolean>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<List<BigInteger>, List<BigInteger>, List<Boolean>>(
                                convertToNative((List<Uint256>) results.get(0).getValue()), 
                                convertToNative((List<Uint256>) results.get(1).getValue()), 
                                convertToNative((List<Bool>) results.get(2).getValue()));
                    }
                });
    }

    public RemoteFunctionCall<Tuple2<BigInteger, BigInteger>> getEpochReward(BigInteger _rewardEpoch) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETEPOCHREWARD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_rewardEpoch)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple2<BigInteger, BigInteger>>(function,
                new Callable<Tuple2<BigInteger, BigInteger>>() {
                    @Override
                    public Tuple2<BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple2<BigInteger, BigInteger>> getEpochsWithClaimableRewards() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETEPOCHSWITHCLAIMABLEREWARDS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple2<BigInteger, BigInteger>>(function,
                new Callable<Tuple2<BigInteger, BigInteger>>() {
                    @Override
                    public Tuple2<BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<List> getEpochsWithUnclaimedRewards(String _beneficiary) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETEPOCHSWITHUNCLAIMEDREWARDS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _beneficiary)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<String> getInflationAddress() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETINFLATIONADDRESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> getRewardEpochToExpireNext() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETREWARDEPOCHTOEXPIRENEXT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple4<List<String>, List<BigInteger>, List<Boolean>, Boolean>> getStateOfRewards(String _beneficiary, BigInteger _rewardEpoch) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETSTATEOFREWARDS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _beneficiary), 
                new org.web3j.abi.datatypes.generated.Uint256(_rewardEpoch)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Bool>>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple4<List<String>, List<BigInteger>, List<Boolean>, Boolean>>(function,
                new Callable<Tuple4<List<String>, List<BigInteger>, List<Boolean>, Boolean>>() {
                    @Override
                    public Tuple4<List<String>, List<BigInteger>, List<Boolean>, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<List<String>, List<BigInteger>, List<Boolean>, Boolean>(
                                convertToNative((List<Address>) results.get(0).getValue()), 
                                convertToNative((List<Uint256>) results.get(1).getValue()), 
                                convertToNative((List<Bool>) results.get(2).getValue()), 
                                (Boolean) results.get(3).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple3<List<BigInteger>, List<Boolean>, Boolean>> getStateOfRewardsFromDataProviders(String _beneficiary, BigInteger _rewardEpoch, List<String> _dataProviders) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETSTATEOFREWARDSFROMDATAPROVIDERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _beneficiary), 
                new org.web3j.abi.datatypes.generated.Uint256(_rewardEpoch), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_dataProviders, org.web3j.abi.datatypes.Address.class))), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Bool>>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple3<List<BigInteger>, List<Boolean>, Boolean>>(function,
                new Callable<Tuple3<List<BigInteger>, List<Boolean>, Boolean>>() {
                    @Override
                    public Tuple3<List<BigInteger>, List<Boolean>, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<List<BigInteger>, List<Boolean>, Boolean>(
                                convertToNative((List<Uint256>) results.get(0).getValue()), 
                                convertToNative((List<Bool>) results.get(1).getValue()), 
                                (Boolean) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple3<BigInteger, BigInteger, BigInteger>> getTokenPoolSupplyData() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETTOKENPOOLSUPPLYDATA, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple3<BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple3<BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple3<BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<BigInteger, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple2<BigInteger, BigInteger>> getUnclaimedReward(BigInteger _rewardEpoch, String _dataProvider) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETUNCLAIMEDREWARD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_rewardEpoch), 
                new org.web3j.abi.datatypes.Address(160, _dataProvider)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple2<BigInteger, BigInteger>>(function,
                new Callable<Tuple2<BigInteger, BigInteger>>() {
                    @Override
                    public Tuple2<BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<String> governance() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GOVERNANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> initialise(String _governance) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INITIALISE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _governance)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> lastInflationAuthorizationReceivedTs() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_LASTINFLATIONAUTHORIZATIONRECEIVEDTS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> proposeGovernance(String _governance) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_PROPOSEGOVERNANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _governance)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> proposedGovernance() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PROPOSEDGOVERNANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> receiveInflation() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RECEIVEINFLATION, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setContractAddresses(String _inflation, String _ftsoManager, String _wNat) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETCONTRACTADDRESSES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _inflation), 
                new org.web3j.abi.datatypes.Address(160, _ftsoManager), 
                new org.web3j.abi.datatypes.Address(160, _wNat)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setDailyAuthorizedInflation(BigInteger _toAuthorizeWei) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETDAILYAUTHORIZEDINFLATION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_toAuthorizeWei)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setDataProviderFeePercentage(BigInteger _feePercentageBIPS) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETDATAPROVIDERFEEPERCENTAGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_feePercentageBIPS)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> totalAwardedWei() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOTALAWARDEDWEI, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> totalClaimedWei() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOTALCLAIMEDWEI, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> totalExpiredWei() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOTALEXPIREDWEI, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> totalInflationAuthorizedWei() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOTALINFLATIONAUTHORIZEDWEI, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> totalInflationReceivedWei() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOTALINFLATIONRECEIVEDWEI, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> totalSelfDestructReceivedWei() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOTALSELFDESTRUCTRECEIVEDWEI, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferGovernance(String _governance) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFERGOVERNANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _governance)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> wNat() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_WNAT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static FtsoRewardManager load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new FtsoRewardManager(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static FtsoRewardManager load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new FtsoRewardManager(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static FtsoRewardManager load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new FtsoRewardManager(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static FtsoRewardManager load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new FtsoRewardManager(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class DailyAuthorizedInflationSetEventResponse extends BaseEventResponse {
        public BigInteger authorizedAmountWei;
    }

    public static class FeePercentageChangedEventResponse extends BaseEventResponse {
        public String dataProvider;

        public BigInteger value;

        public BigInteger validFromEpoch;
    }

    public static class GovernanceProposedEventResponse extends BaseEventResponse {
        public String proposedGovernance;
    }

    public static class GovernanceUpdatedEventResponse extends BaseEventResponse {
        public String oldGovernance;

        public String newGoveranance;
    }

    public static class InflationReceivedEventResponse extends BaseEventResponse {
        public BigInteger amountReceivedWei;
    }

    public static class RewardClaimedEventResponse extends BaseEventResponse {
        public String dataProvider;

        public String whoClaimed;

        public String sentTo;

        public BigInteger rewardEpoch;

        public BigInteger amount;
    }

    public static class RewardClaimsExpiredEventResponse extends BaseEventResponse {
        public BigInteger rewardEpochId;
    }

    public static class RewardsDistributedEventResponse extends BaseEventResponse {
        public String ftso;

        public BigInteger epochId;

        public List<String> addresses;

        public List<BigInteger> rewards;
    }
}
