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
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint192;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.abi.datatypes.generated.Uint8;
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
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tuples.generated.Tuple9;
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
public class FtsoManager extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_MAX_TRUSTED_ADDRESSES_LENGTH = "MAX_TRUSTED_ADDRESSES_LENGTH";

    public static final String FUNC_ACTIVATE = "activate";

    public static final String FUNC_ACTIVE = "active";

    public static final String FUNC_ADDFTSO = "addFtso";

    public static final String FUNC_ADDREVERTERROR = "addRevertError";

    public static final String FUNC_CLAIMGOVERNANCE = "claimGovernance";

    public static final String FUNC_CLEANUPBLOCKNUMBERMANAGER = "cleanupBlockNumberManager";

    public static final String FUNC_DAEMONIZE = "daemonize";

    public static final String FUNC_ERRORDATA = "errorData";

    public static final String FUNC_FLAREDAEMON = "flareDaemon";

    public static final String FUNC_FTSOREGISTRY = "ftsoRegistry";

    public static final String FUNC_GETCURRENTPRICEEPOCHDATA = "getCurrentPriceEpochData";

    public static final String FUNC_GETCURRENTREWARDEPOCH = "getCurrentRewardEpoch";

    public static final String FUNC_GETFALLBACKMODE = "getFallbackMode";

    public static final String FUNC_GETFTSOS = "getFtsos";

    public static final String FUNC_GETPRICEEPOCHCONFIGURATION = "getPriceEpochConfiguration";

    public static final String FUNC_GETPRICESUBMITTER = "getPriceSubmitter";

    public static final String FUNC_GETREWARDEPOCHVOTEPOWERBLOCK = "getRewardEpochVotePowerBlock";

    public static final String FUNC_GETVOTEPOWERINTERVALFRACTION = "getVotePowerIntervalFraction";

    public static final String FUNC_GOVERNANCE = "governance";

    public static final String FUNC_INITIALISE = "initialise";

    public static final String FUNC_LASTREWARDEDFTSOADDRESS = "lastRewardedFtsoAddress";

    public static final String FUNC_PRICESUBMITTER = "priceSubmitter";

    public static final String FUNC_PROPOSEGOVERNANCE = "proposeGovernance";

    public static final String FUNC_PROPOSEDGOVERNANCE = "proposedGovernance";

    public static final String FUNC_REMOVEFTSO = "removeFtso";

    public static final String FUNC_REPLACEFTSO = "replaceFtso";

    public static final String FUNC_REWARDEPOCHDURATIONSECONDS = "rewardEpochDurationSeconds";

    public static final String FUNC_REWARDEPOCHS = "rewardEpochs";

    public static final String FUNC_REWARDEPOCHSSTARTTS = "rewardEpochsStartTs";

    public static final String FUNC_REWARDMANAGER = "rewardManager";

    public static final String FUNC_SETCONTRACTADDRESSES = "setContractAddresses";

    public static final String FUNC_SETFALLBACKMODE = "setFallbackMode";

    public static final String FUNC_SETFTSOASSET = "setFtsoAsset";

    public static final String FUNC_SETFTSOASSETFTSOS = "setFtsoAssetFtsos";

    public static final String FUNC_SETFTSOFALLBACKMODE = "setFtsoFallbackMode";

    public static final String FUNC_SETGOVERNANCEPARAMETERS = "setGovernanceParameters";

    public static final String FUNC_SETTINGS = "settings";

    public static final String FUNC_SHOWLASTREVERTEDERROR = "showLastRevertedError";

    public static final String FUNC_SHOWREVERTEDERRORS = "showRevertedErrors";

    public static final String FUNC_SUPPLY = "supply";

    public static final String FUNC_SWITCHTOFALLBACKMODE = "switchToFallbackMode";

    public static final String FUNC_TRANSFERGOVERNANCE = "transferGovernance";

    public static final String FUNC_VOTERWHITELISTER = "voterWhitelister";

    public static final Event CLEANUPBLOCKNUMBERMANAGERFAILEDFORBLOCK_EVENT = new Event("CleanupBlockNumberManagerFailedForBlock", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event CLEANUPBLOCKNUMBERMANAGERUNSET_EVENT = new Event("CleanupBlockNumberManagerUnset", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event CLOSINGEXPIREDREWARDEPOCHFAILED_EVENT = new Event("ClosingExpiredRewardEpochFailed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event CONTRACTREVERTERROR_EVENT = new Event("ContractRevertError", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event DISTRIBUTINGREWARDSFAILED_EVENT = new Event("DistributingRewardsFailed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event FALLBACKMODE_EVENT = new Event("FallbackMode", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
    ;

    public static final Event FINALIZINGPRICEEPOCHFAILED_EVENT = new Event("FinalizingPriceEpochFailed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint8>() {}));
    ;

    public static final Event FTSOADDED_EVENT = new Event("FtsoAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event FTSOFALLBACKMODE_EVENT = new Event("FtsoFallbackMode", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event GOVERNANCEPROPOSED_EVENT = new Event("GovernanceProposed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event GOVERNANCEUPDATED_EVENT = new Event("GovernanceUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event INITIALIZINGCURRENTEPOCHSTATEFORREVEALFAILED_EVENT = new Event("InitializingCurrentEpochStateForRevealFailed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event PRICEEPOCHFINALIZED_EVENT = new Event("PriceEpochFinalized", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event REWARDEPOCHFINALIZED_EVENT = new Event("RewardEpochFinalized", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected FtsoManager(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected FtsoManager(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected FtsoManager(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected FtsoManager(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<CleanupBlockNumberManagerFailedForBlockEventResponse> getCleanupBlockNumberManagerFailedForBlockEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CLEANUPBLOCKNUMBERMANAGERFAILEDFORBLOCK_EVENT, transactionReceipt);
        ArrayList<CleanupBlockNumberManagerFailedForBlockEventResponse> responses = new ArrayList<CleanupBlockNumberManagerFailedForBlockEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CleanupBlockNumberManagerFailedForBlockEventResponse typedResponse = new CleanupBlockNumberManagerFailedForBlockEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.blockNumber = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CleanupBlockNumberManagerFailedForBlockEventResponse> cleanupBlockNumberManagerFailedForBlockEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, CleanupBlockNumberManagerFailedForBlockEventResponse>() {
            @Override
            public CleanupBlockNumberManagerFailedForBlockEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CLEANUPBLOCKNUMBERMANAGERFAILEDFORBLOCK_EVENT, log);
                CleanupBlockNumberManagerFailedForBlockEventResponse typedResponse = new CleanupBlockNumberManagerFailedForBlockEventResponse();
                typedResponse.log = log;
                typedResponse.blockNumber = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CleanupBlockNumberManagerFailedForBlockEventResponse> cleanupBlockNumberManagerFailedForBlockEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLEANUPBLOCKNUMBERMANAGERFAILEDFORBLOCK_EVENT));
        return cleanupBlockNumberManagerFailedForBlockEventFlowable(filter);
    }

    public List<CleanupBlockNumberManagerUnsetEventResponse> getCleanupBlockNumberManagerUnsetEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CLEANUPBLOCKNUMBERMANAGERUNSET_EVENT, transactionReceipt);
        ArrayList<CleanupBlockNumberManagerUnsetEventResponse> responses = new ArrayList<CleanupBlockNumberManagerUnsetEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CleanupBlockNumberManagerUnsetEventResponse typedResponse = new CleanupBlockNumberManagerUnsetEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CleanupBlockNumberManagerUnsetEventResponse> cleanupBlockNumberManagerUnsetEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, CleanupBlockNumberManagerUnsetEventResponse>() {
            @Override
            public CleanupBlockNumberManagerUnsetEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CLEANUPBLOCKNUMBERMANAGERUNSET_EVENT, log);
                CleanupBlockNumberManagerUnsetEventResponse typedResponse = new CleanupBlockNumberManagerUnsetEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Flowable<CleanupBlockNumberManagerUnsetEventResponse> cleanupBlockNumberManagerUnsetEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLEANUPBLOCKNUMBERMANAGERUNSET_EVENT));
        return cleanupBlockNumberManagerUnsetEventFlowable(filter);
    }

    public List<ClosingExpiredRewardEpochFailedEventResponse> getClosingExpiredRewardEpochFailedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CLOSINGEXPIREDREWARDEPOCHFAILED_EVENT, transactionReceipt);
        ArrayList<ClosingExpiredRewardEpochFailedEventResponse> responses = new ArrayList<ClosingExpiredRewardEpochFailedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ClosingExpiredRewardEpochFailedEventResponse typedResponse = new ClosingExpiredRewardEpochFailedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._rewardEpoch = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ClosingExpiredRewardEpochFailedEventResponse> closingExpiredRewardEpochFailedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ClosingExpiredRewardEpochFailedEventResponse>() {
            @Override
            public ClosingExpiredRewardEpochFailedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CLOSINGEXPIREDREWARDEPOCHFAILED_EVENT, log);
                ClosingExpiredRewardEpochFailedEventResponse typedResponse = new ClosingExpiredRewardEpochFailedEventResponse();
                typedResponse.log = log;
                typedResponse._rewardEpoch = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ClosingExpiredRewardEpochFailedEventResponse> closingExpiredRewardEpochFailedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLOSINGEXPIREDREWARDEPOCHFAILED_EVENT));
        return closingExpiredRewardEpochFailedEventFlowable(filter);
    }

    public List<ContractRevertErrorEventResponse> getContractRevertErrorEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CONTRACTREVERTERROR_EVENT, transactionReceipt);
        ArrayList<ContractRevertErrorEventResponse> responses = new ArrayList<ContractRevertErrorEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ContractRevertErrorEventResponse typedResponse = new ContractRevertErrorEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.theContract = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.atBlock = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.theMessage = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ContractRevertErrorEventResponse> contractRevertErrorEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ContractRevertErrorEventResponse>() {
            @Override
            public ContractRevertErrorEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CONTRACTREVERTERROR_EVENT, log);
                ContractRevertErrorEventResponse typedResponse = new ContractRevertErrorEventResponse();
                typedResponse.log = log;
                typedResponse.theContract = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.atBlock = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.theMessage = (String) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ContractRevertErrorEventResponse> contractRevertErrorEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CONTRACTREVERTERROR_EVENT));
        return contractRevertErrorEventFlowable(filter);
    }

    public List<DistributingRewardsFailedEventResponse> getDistributingRewardsFailedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(DISTRIBUTINGREWARDSFAILED_EVENT, transactionReceipt);
        ArrayList<DistributingRewardsFailedEventResponse> responses = new ArrayList<DistributingRewardsFailedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DistributingRewardsFailedEventResponse typedResponse = new DistributingRewardsFailedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ftso = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.epochId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<DistributingRewardsFailedEventResponse> distributingRewardsFailedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, DistributingRewardsFailedEventResponse>() {
            @Override
            public DistributingRewardsFailedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(DISTRIBUTINGREWARDSFAILED_EVENT, log);
                DistributingRewardsFailedEventResponse typedResponse = new DistributingRewardsFailedEventResponse();
                typedResponse.log = log;
                typedResponse.ftso = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.epochId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DistributingRewardsFailedEventResponse> distributingRewardsFailedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DISTRIBUTINGREWARDSFAILED_EVENT));
        return distributingRewardsFailedEventFlowable(filter);
    }

    public List<FallbackModeEventResponse> getFallbackModeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(FALLBACKMODE_EVENT, transactionReceipt);
        ArrayList<FallbackModeEventResponse> responses = new ArrayList<FallbackModeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FallbackModeEventResponse typedResponse = new FallbackModeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.fallbackMode = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<FallbackModeEventResponse> fallbackModeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, FallbackModeEventResponse>() {
            @Override
            public FallbackModeEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(FALLBACKMODE_EVENT, log);
                FallbackModeEventResponse typedResponse = new FallbackModeEventResponse();
                typedResponse.log = log;
                typedResponse.fallbackMode = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<FallbackModeEventResponse> fallbackModeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FALLBACKMODE_EVENT));
        return fallbackModeEventFlowable(filter);
    }

    public List<FinalizingPriceEpochFailedEventResponse> getFinalizingPriceEpochFailedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(FINALIZINGPRICEEPOCHFAILED_EVENT, transactionReceipt);
        ArrayList<FinalizingPriceEpochFailedEventResponse> responses = new ArrayList<FinalizingPriceEpochFailedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FinalizingPriceEpochFailedEventResponse typedResponse = new FinalizingPriceEpochFailedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ftso = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.epochId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.failingType = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<FinalizingPriceEpochFailedEventResponse> finalizingPriceEpochFailedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, FinalizingPriceEpochFailedEventResponse>() {
            @Override
            public FinalizingPriceEpochFailedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(FINALIZINGPRICEEPOCHFAILED_EVENT, log);
                FinalizingPriceEpochFailedEventResponse typedResponse = new FinalizingPriceEpochFailedEventResponse();
                typedResponse.log = log;
                typedResponse.ftso = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.epochId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.failingType = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<FinalizingPriceEpochFailedEventResponse> finalizingPriceEpochFailedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FINALIZINGPRICEEPOCHFAILED_EVENT));
        return finalizingPriceEpochFailedEventFlowable(filter);
    }

    public List<FtsoAddedEventResponse> getFtsoAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(FTSOADDED_EVENT, transactionReceipt);
        ArrayList<FtsoAddedEventResponse> responses = new ArrayList<FtsoAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FtsoAddedEventResponse typedResponse = new FtsoAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ftso = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.add = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<FtsoAddedEventResponse> ftsoAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, FtsoAddedEventResponse>() {
            @Override
            public FtsoAddedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(FTSOADDED_EVENT, log);
                FtsoAddedEventResponse typedResponse = new FtsoAddedEventResponse();
                typedResponse.log = log;
                typedResponse.ftso = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.add = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<FtsoAddedEventResponse> ftsoAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FTSOADDED_EVENT));
        return ftsoAddedEventFlowable(filter);
    }

    public List<FtsoFallbackModeEventResponse> getFtsoFallbackModeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(FTSOFALLBACKMODE_EVENT, transactionReceipt);
        ArrayList<FtsoFallbackModeEventResponse> responses = new ArrayList<FtsoFallbackModeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FtsoFallbackModeEventResponse typedResponse = new FtsoFallbackModeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ftso = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.fallbackMode = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<FtsoFallbackModeEventResponse> ftsoFallbackModeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, FtsoFallbackModeEventResponse>() {
            @Override
            public FtsoFallbackModeEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(FTSOFALLBACKMODE_EVENT, log);
                FtsoFallbackModeEventResponse typedResponse = new FtsoFallbackModeEventResponse();
                typedResponse.log = log;
                typedResponse.ftso = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.fallbackMode = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<FtsoFallbackModeEventResponse> ftsoFallbackModeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FTSOFALLBACKMODE_EVENT));
        return ftsoFallbackModeEventFlowable(filter);
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

    public List<InitializingCurrentEpochStateForRevealFailedEventResponse> getInitializingCurrentEpochStateForRevealFailedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(INITIALIZINGCURRENTEPOCHSTATEFORREVEALFAILED_EVENT, transactionReceipt);
        ArrayList<InitializingCurrentEpochStateForRevealFailedEventResponse> responses = new ArrayList<InitializingCurrentEpochStateForRevealFailedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            InitializingCurrentEpochStateForRevealFailedEventResponse typedResponse = new InitializingCurrentEpochStateForRevealFailedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ftso = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.epochId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<InitializingCurrentEpochStateForRevealFailedEventResponse> initializingCurrentEpochStateForRevealFailedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, InitializingCurrentEpochStateForRevealFailedEventResponse>() {
            @Override
            public InitializingCurrentEpochStateForRevealFailedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(INITIALIZINGCURRENTEPOCHSTATEFORREVEALFAILED_EVENT, log);
                InitializingCurrentEpochStateForRevealFailedEventResponse typedResponse = new InitializingCurrentEpochStateForRevealFailedEventResponse();
                typedResponse.log = log;
                typedResponse.ftso = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.epochId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<InitializingCurrentEpochStateForRevealFailedEventResponse> initializingCurrentEpochStateForRevealFailedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(INITIALIZINGCURRENTEPOCHSTATEFORREVEALFAILED_EVENT));
        return initializingCurrentEpochStateForRevealFailedEventFlowable(filter);
    }

    public List<PriceEpochFinalizedEventResponse> getPriceEpochFinalizedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PRICEEPOCHFINALIZED_EVENT, transactionReceipt);
        ArrayList<PriceEpochFinalizedEventResponse> responses = new ArrayList<PriceEpochFinalizedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PriceEpochFinalizedEventResponse typedResponse = new PriceEpochFinalizedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.chosenFtso = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.rewardEpochId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PriceEpochFinalizedEventResponse> priceEpochFinalizedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, PriceEpochFinalizedEventResponse>() {
            @Override
            public PriceEpochFinalizedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PRICEEPOCHFINALIZED_EVENT, log);
                PriceEpochFinalizedEventResponse typedResponse = new PriceEpochFinalizedEventResponse();
                typedResponse.log = log;
                typedResponse.chosenFtso = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.rewardEpochId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PriceEpochFinalizedEventResponse> priceEpochFinalizedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PRICEEPOCHFINALIZED_EVENT));
        return priceEpochFinalizedEventFlowable(filter);
    }

    public List<RewardEpochFinalizedEventResponse> getRewardEpochFinalizedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(REWARDEPOCHFINALIZED_EVENT, transactionReceipt);
        ArrayList<RewardEpochFinalizedEventResponse> responses = new ArrayList<RewardEpochFinalizedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RewardEpochFinalizedEventResponse typedResponse = new RewardEpochFinalizedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.votepowerBlock = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.startBlock = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RewardEpochFinalizedEventResponse> rewardEpochFinalizedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, RewardEpochFinalizedEventResponse>() {
            @Override
            public RewardEpochFinalizedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(REWARDEPOCHFINALIZED_EVENT, log);
                RewardEpochFinalizedEventResponse typedResponse = new RewardEpochFinalizedEventResponse();
                typedResponse.log = log;
                typedResponse.votepowerBlock = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.startBlock = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RewardEpochFinalizedEventResponse> rewardEpochFinalizedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REWARDEPOCHFINALIZED_EVENT));
        return rewardEpochFinalizedEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> MAX_TRUSTED_ADDRESSES_LENGTH() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_MAX_TRUSTED_ADDRESSES_LENGTH, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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

    public RemoteFunctionCall<TransactionReceipt> addFtso(String _ftso) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDFTSO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _ftso)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addRevertError(String revertedContract, String message) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDREVERTERROR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, revertedContract), 
                new org.web3j.abi.datatypes.Utf8String(message)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> claimGovernance() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CLAIMGOVERNANCE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> cleanupBlockNumberManager() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CLEANUPBLOCKNUMBERMANAGER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> daemonize() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DAEMONIZE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple2<BigInteger, BigInteger>> errorData() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ERRORDATA, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint192>() {}, new TypeReference<Uint64>() {}));
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

    public RemoteFunctionCall<String> flareDaemon() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_FLAREDAEMON, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> ftsoRegistry() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_FTSOREGISTRY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>> getCurrentPriceEpochData() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCURRENTPRICEEPOCHDATA, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> getCurrentRewardEpoch() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCURRENTREWARDEPOCH, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple3<Boolean, List<String>, List<Boolean>>> getFallbackMode() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETFALLBACKMODE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<DynamicArray<Address>>() {}, new TypeReference<DynamicArray<Bool>>() {}));
        return new RemoteFunctionCall<Tuple3<Boolean, List<String>, List<Boolean>>>(function,
                new Callable<Tuple3<Boolean, List<String>, List<Boolean>>>() {
                    @Override
                    public Tuple3<Boolean, List<String>, List<Boolean>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<Boolean, List<String>, List<Boolean>>(
                                (Boolean) results.get(0).getValue(), 
                                convertToNative((List<Address>) results.get(1).getValue()), 
                                convertToNative((List<Bool>) results.get(2).getValue()));
                    }
                });
    }

    public RemoteFunctionCall<List> getFtsos() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETFTSOS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
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

    public RemoteFunctionCall<Tuple3<BigInteger, BigInteger, BigInteger>> getPriceEpochConfiguration() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETPRICEEPOCHCONFIGURATION, 
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

    public RemoteFunctionCall<String> getPriceSubmitter() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETPRICESUBMITTER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> getRewardEpochVotePowerBlock(BigInteger _rewardEpoch) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETREWARDEPOCHVOTEPOWERBLOCK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_rewardEpoch)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getVotePowerIntervalFraction() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETVOTEPOWERINTERVALFRACTION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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

    public RemoteFunctionCall<String> lastRewardedFtsoAddress() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_LASTREWARDEDFTSOADDRESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> priceSubmitter() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PRICESUBMITTER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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

    public RemoteFunctionCall<TransactionReceipt> removeFtso(String _ftso) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REMOVEFTSO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _ftso)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> replaceFtso(String _ftsoToRemove, String _ftsoToAdd, Boolean _copyCurrentPrice, Boolean _copyAssetOrAssetFtsos) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REPLACEFTSO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _ftsoToRemove), 
                new org.web3j.abi.datatypes.Address(160, _ftsoToAdd), 
                new org.web3j.abi.datatypes.Bool(_copyCurrentPrice), 
                new org.web3j.abi.datatypes.Bool(_copyAssetOrAssetFtsos)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> rewardEpochDurationSeconds() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_REWARDEPOCHDURATIONSECONDS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple3<BigInteger, BigInteger, BigInteger>> rewardEpochs(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_REWARDEPOCHS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
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

    public RemoteFunctionCall<BigInteger> rewardEpochsStartTs() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_REWARDEPOCHSSTARTTS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> rewardManager() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_REWARDMANAGER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setContractAddresses(String _rewardManager, String _ftsoRegistry, String _voterWhitelister, String _supply, String _cleanupBlockNumberManager) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETCONTRACTADDRESSES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _rewardManager), 
                new org.web3j.abi.datatypes.Address(160, _ftsoRegistry), 
                new org.web3j.abi.datatypes.Address(160, _voterWhitelister), 
                new org.web3j.abi.datatypes.Address(160, _supply), 
                new org.web3j.abi.datatypes.Address(160, _cleanupBlockNumberManager)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setFallbackMode(Boolean _fallbackMode) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETFALLBACKMODE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_fallbackMode)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setFtsoAsset(String _ftso, String _asset) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETFTSOASSET, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _ftso), 
                new org.web3j.abi.datatypes.Address(160, _asset)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setFtsoAssetFtsos(String _ftso, List<String> _assetFtsos) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETFTSOASSETFTSOS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _ftso), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_assetFtsos, org.web3j.abi.datatypes.Address.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setFtsoFallbackMode(String _ftso, Boolean _fallbackMode) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETFTSOFALLBACKMODE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _ftso), 
                new org.web3j.abi.datatypes.Bool(_fallbackMode)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setGovernanceParameters(BigInteger _maxVotePowerNatThresholdFraction, BigInteger _maxVotePowerAssetThresholdFraction, BigInteger _lowAssetUSDThreshold, BigInteger _highAssetUSDThreshold, BigInteger _highAssetTurnoutThresholdBIPS, BigInteger _lowNatTurnoutThresholdBIPS, BigInteger _rewardExpiryOffsetSeconds, List<String> _trustedAddresses) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETGOVERNANCEPARAMETERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_maxVotePowerNatThresholdFraction), 
                new org.web3j.abi.datatypes.generated.Uint256(_maxVotePowerAssetThresholdFraction), 
                new org.web3j.abi.datatypes.generated.Uint256(_lowAssetUSDThreshold), 
                new org.web3j.abi.datatypes.generated.Uint256(_highAssetUSDThreshold), 
                new org.web3j.abi.datatypes.generated.Uint256(_highAssetTurnoutThresholdBIPS), 
                new org.web3j.abi.datatypes.generated.Uint256(_lowNatTurnoutThresholdBIPS), 
                new org.web3j.abi.datatypes.generated.Uint256(_rewardExpiryOffsetSeconds), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_trustedAddresses, org.web3j.abi.datatypes.Address.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple9<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, Boolean, Boolean>> settings() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SETTINGS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple9<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, Boolean, Boolean>>(function,
                new Callable<Tuple9<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, Boolean, Boolean>>() {
                    @Override
                    public Tuple9<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, Boolean, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple9<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, Boolean, Boolean>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue(), 
                                (Boolean) results.get(7).getValue(), 
                                (Boolean) results.get(8).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple5<List<BigInteger>, List<BigInteger>, List<String>, List<String>, BigInteger>> showLastRevertedError() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SHOWLASTREVERTEDERROR, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Utf8String>>() {}, new TypeReference<DynamicArray<Address>>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple5<List<BigInteger>, List<BigInteger>, List<String>, List<String>, BigInteger>>(function,
                new Callable<Tuple5<List<BigInteger>, List<BigInteger>, List<String>, List<String>, BigInteger>>() {
                    @Override
                    public Tuple5<List<BigInteger>, List<BigInteger>, List<String>, List<String>, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<List<BigInteger>, List<BigInteger>, List<String>, List<String>, BigInteger>(
                                convertToNative((List<Uint256>) results.get(0).getValue()), 
                                convertToNative((List<Uint256>) results.get(1).getValue()), 
                                convertToNative((List<Utf8String>) results.get(2).getValue()), 
                                convertToNative((List<Address>) results.get(3).getValue()), 
                                (BigInteger) results.get(4).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple5<List<BigInteger>, List<BigInteger>, List<String>, List<String>, BigInteger>> showRevertedErrors(BigInteger startIndex, BigInteger numErrorTypesToShow) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SHOWREVERTEDERRORS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(startIndex), 
                new org.web3j.abi.datatypes.generated.Uint256(numErrorTypesToShow)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Utf8String>>() {}, new TypeReference<DynamicArray<Address>>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple5<List<BigInteger>, List<BigInteger>, List<String>, List<String>, BigInteger>>(function,
                new Callable<Tuple5<List<BigInteger>, List<BigInteger>, List<String>, List<String>, BigInteger>>() {
                    @Override
                    public Tuple5<List<BigInteger>, List<BigInteger>, List<String>, List<String>, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<List<BigInteger>, List<BigInteger>, List<String>, List<String>, BigInteger>(
                                convertToNative((List<Uint256>) results.get(0).getValue()), 
                                convertToNative((List<Uint256>) results.get(1).getValue()), 
                                convertToNative((List<Utf8String>) results.get(2).getValue()), 
                                convertToNative((List<Address>) results.get(3).getValue()), 
                                (BigInteger) results.get(4).getValue());
                    }
                });
    }

    public RemoteFunctionCall<String> supply() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> switchToFallbackMode() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SWITCHTOFALLBACKMODE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferGovernance(String _governance) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFERGOVERNANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _governance)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> voterWhitelister() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VOTERWHITELISTER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static FtsoManager load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new FtsoManager(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static FtsoManager load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new FtsoManager(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static FtsoManager load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new FtsoManager(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static FtsoManager load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new FtsoManager(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class CleanupBlockNumberManagerFailedForBlockEventResponse extends BaseEventResponse {
        public BigInteger blockNumber;
    }

    public static class CleanupBlockNumberManagerUnsetEventResponse extends BaseEventResponse {
    }

    public static class ClosingExpiredRewardEpochFailedEventResponse extends BaseEventResponse {
        public BigInteger _rewardEpoch;
    }

    public static class ContractRevertErrorEventResponse extends BaseEventResponse {
        public String theContract;

        public BigInteger atBlock;

        public String theMessage;
    }

    public static class DistributingRewardsFailedEventResponse extends BaseEventResponse {
        public String ftso;

        public BigInteger epochId;
    }

    public static class FallbackModeEventResponse extends BaseEventResponse {
        public Boolean fallbackMode;
    }

    public static class FinalizingPriceEpochFailedEventResponse extends BaseEventResponse {
        public String ftso;

        public BigInteger epochId;

        public BigInteger failingType;
    }

    public static class FtsoAddedEventResponse extends BaseEventResponse {
        public String ftso;

        public Boolean add;
    }

    public static class FtsoFallbackModeEventResponse extends BaseEventResponse {
        public String ftso;

        public Boolean fallbackMode;
    }

    public static class GovernanceProposedEventResponse extends BaseEventResponse {
        public String proposedGovernance;
    }

    public static class GovernanceUpdatedEventResponse extends BaseEventResponse {
        public String oldGovernance;

        public String newGoveranance;
    }

    public static class InitializingCurrentEpochStateForRevealFailedEventResponse extends BaseEventResponse {
        public String ftso;

        public BigInteger epochId;
    }

    public static class PriceEpochFinalizedEventResponse extends BaseEventResponse {
        public String chosenFtso;

        public BigInteger rewardEpochId;
    }

    public static class RewardEpochFinalizedEventResponse extends BaseEventResponse {
        public BigInteger votepowerBlock;

        public BigInteger startBlock;
    }
}
