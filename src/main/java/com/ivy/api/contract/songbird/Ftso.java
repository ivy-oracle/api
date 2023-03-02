package com.ivy.api.contract.songbird;

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
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
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
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tuples.generated.Tuple9;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>
 * Auto generated code.
 * <p>
 * <strong>Do not modify!</strong>
 * <p>
 * Please use the <a href="https://docs.web3j.io/command_line.html">web3j
 * command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen
 * module</a> to update.
 *
 * <p>
 * Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class Ftso extends Contract implements com.ivy.api.contract.Ftso {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_ASSET_PRICE_USD_DECIMALS = "ASSET_PRICE_USD_DECIMALS";

    public static final String FUNC_ACTIVATEFTSO = "activateFtso";

    public static final String FUNC_ACTIVE = "active";

    public static final String FUNC_ASSETFTSOS = "assetFtsos";

    public static final String FUNC_ASSETS = "assets";

    public static final String FUNC_CONFIGUREEPOCHS = "configureEpochs";

    public static final String FUNC_DEACTIVATEFTSO = "deactivateFtso";

    public static final String FUNC_EPOCHSCONFIGURATION = "epochsConfiguration";

    public static final String FUNC_FALLBACKFINALIZEPRICEEPOCH = "fallbackFinalizePriceEpoch";

    public static final String FUNC_FINALIZEPRICEEPOCH = "finalizePriceEpoch";

    public static final String FUNC_FORCEFINALIZEPRICEEPOCH = "forceFinalizePriceEpoch";

    public static final String FUNC_FTSOMANAGER = "ftsoManager";

    public static final String FUNC_GETASSET = "getAsset";

    public static final String FUNC_GETASSETFTSOS = "getAssetFtsos";

    public static final String FUNC_GETCURRENTEPOCHID = "getCurrentEpochId";

    public static final String FUNC_GETCURRENTPRICE = "getCurrentPrice";

    public static final String FUNC_GETCURRENTPRICEDETAILS = "getCurrentPriceDetails";

    public static final String FUNC_GETCURRENTPRICEFROMTRUSTEDPROVIDERS = "getCurrentPriceFromTrustedProviders";

    public static final String FUNC_GETCURRENTPRICEWITHDECIMALS = "getCurrentPriceWithDecimals";

    public static final String FUNC_GETCURRENTPRICEWITHDECIMALSFROMTRUSTEDPROVIDERS = "getCurrentPriceWithDecimalsFromTrustedProviders";

    public static final String FUNC_GETCURRENTRANDOM = "getCurrentRandom";

    public static final String FUNC_GETEPOCHID = "getEpochId";

    public static final String FUNC_GETEPOCHPRICE = "getEpochPrice";

    public static final String FUNC_GETEPOCHPRICEFORVOTER = "getEpochPriceForVoter";

    public static final String FUNC_GETPRICEEPOCHCONFIGURATION = "getPriceEpochConfiguration";

    public static final String FUNC_GETPRICEEPOCHDATA = "getPriceEpochData";

    public static final String FUNC_GETRANDOM = "getRandom";

    public static final String FUNC_GETVOTEWEIGHTINGPARAMETERS = "getVoteWeightingParameters";

    public static final String FUNC_INITIALIZECURRENTEPOCHSTATEFORREVEAL = "initializeCurrentEpochStateForReveal";

    public static final String FUNC_PRICEDEVIATIONTHRESHOLDBIPS = "priceDeviationThresholdBIPS";

    public static final String FUNC_PRICEEPOCHCYCLICBUFFERSIZE = "priceEpochCyclicBufferSize";

    public static final String FUNC_PRICESUBMITTER = "priceSubmitter";

    public static final String FUNC_REVEALPRICESUBMITTER = "revealPriceSubmitter";

    public static final String FUNC_SETASSET = "setAsset";

    public static final String FUNC_SETASSETFTSOS = "setAssetFtsos";

    public static final String FUNC_SETVOTEPOWERBLOCK = "setVotePowerBlock";

    public static final String FUNC_SUBMITPRICEHASHSUBMITTER = "submitPriceHashSubmitter";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_UPDATEINITIALPRICE = "updateInitialPrice";

    public static final String FUNC_WNAT = "wNat";

    public static final String FUNC_WNATVOTEPOWERCACHED = "wNatVotePowerCached";

    public static final Event LOWTURNOUT_EVENT = new Event("LowTurnout",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }));;

    public static final Event PRICEEPOCHINITIALIZEDONFTSO_EVENT = new Event("PriceEpochInitializedOnFtso",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }));;

    public static final Event PRICEFINALIZED_EVENT = new Event("PriceFinalized",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Bool>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint8>() {
            }, new TypeReference<Uint256>() {
            }));;

    public static final Event PRICEHASHSUBMITTED_EVENT = new Event("PriceHashSubmitted",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>(true) {
            }, new TypeReference<Bytes32>() {
            }, new TypeReference<Uint256>() {
            }));;

    public static final Event PRICEREVEALED_EVENT = new Event("PriceRevealed",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>(true) {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }));;

    @Deprecated
    protected Ftso(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice,
            BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Ftso(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Ftso(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice,
            BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Ftso(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<LowTurnoutEventResponse> getLowTurnoutEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(LOWTURNOUT_EVENT,
                transactionReceipt);
        ArrayList<LowTurnoutEventResponse> responses = new ArrayList<LowTurnoutEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LowTurnoutEventResponse typedResponse = new LowTurnoutEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.epochId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.natTurnout = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.lowNatTurnoutThresholdBIPS = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LowTurnoutEventResponse> lowTurnoutEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LowTurnoutEventResponse>() {
            @Override
            public LowTurnoutEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(LOWTURNOUT_EVENT, log);
                LowTurnoutEventResponse typedResponse = new LowTurnoutEventResponse();
                typedResponse.log = log;
                typedResponse.epochId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.natTurnout = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.lowNatTurnoutThresholdBIPS = (BigInteger) eventValues.getNonIndexedValues().get(1)
                        .getValue();
                typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LowTurnoutEventResponse> lowTurnoutEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOWTURNOUT_EVENT));
        return lowTurnoutEventFlowable(filter);
    }

    public List<PriceEpochInitializedOnFtsoEventResponse> getPriceEpochInitializedOnFtsoEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PRICEEPOCHINITIALIZEDONFTSO_EVENT,
                transactionReceipt);
        ArrayList<PriceEpochInitializedOnFtsoEventResponse> responses = new ArrayList<PriceEpochInitializedOnFtsoEventResponse>(
                valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PriceEpochInitializedOnFtsoEventResponse typedResponse = new PriceEpochInitializedOnFtsoEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.epochId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.endTime = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PriceEpochInitializedOnFtsoEventResponse> priceEpochInitializedOnFtsoEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, PriceEpochInitializedOnFtsoEventResponse>() {
            @Override
            public PriceEpochInitializedOnFtsoEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(
                        PRICEEPOCHINITIALIZEDONFTSO_EVENT, log);
                PriceEpochInitializedOnFtsoEventResponse typedResponse = new PriceEpochInitializedOnFtsoEventResponse();
                typedResponse.log = log;
                typedResponse.epochId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.endTime = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PriceEpochInitializedOnFtsoEventResponse> priceEpochInitializedOnFtsoEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PRICEEPOCHINITIALIZEDONFTSO_EVENT));
        return priceEpochInitializedOnFtsoEventFlowable(filter);
    }

    public List<PriceFinalizedEventResponse> getPriceFinalizedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PRICEFINALIZED_EVENT,
                transactionReceipt);
        ArrayList<PriceFinalizedEventResponse> responses = new ArrayList<PriceFinalizedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PriceFinalizedEventResponse typedResponse = new PriceFinalizedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.epochId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.rewardedFtso = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.lowIQRRewardPrice = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.highIQRRewardPrice = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.lowElasticBandRewardPrice = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.highElasticBandRewardPrice = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
            typedResponse.finalizationType = (BigInteger) eventValues.getNonIndexedValues().get(6).getValue();
            typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(7).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PriceFinalizedEventResponse> priceFinalizedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, PriceFinalizedEventResponse>() {
            @Override
            public PriceFinalizedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PRICEFINALIZED_EVENT, log);
                PriceFinalizedEventResponse typedResponse = new PriceFinalizedEventResponse();
                typedResponse.log = log;
                typedResponse.epochId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.rewardedFtso = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.lowIQRRewardPrice = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.highIQRRewardPrice = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.lowElasticBandRewardPrice = (BigInteger) eventValues.getNonIndexedValues().get(4)
                        .getValue();
                typedResponse.highElasticBandRewardPrice = (BigInteger) eventValues.getNonIndexedValues().get(5)
                        .getValue();
                typedResponse.finalizationType = (BigInteger) eventValues.getNonIndexedValues().get(6).getValue();
                typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(7).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PriceFinalizedEventResponse> priceFinalizedEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PRICEFINALIZED_EVENT));
        return priceFinalizedEventFlowable(filter);
    }

    public List<PriceHashSubmittedEventResponse> getPriceHashSubmittedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PRICEHASHSUBMITTED_EVENT,
                transactionReceipt);
        ArrayList<PriceHashSubmittedEventResponse> responses = new ArrayList<PriceHashSubmittedEventResponse>(
                valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PriceHashSubmittedEventResponse typedResponse = new PriceHashSubmittedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.submitter = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.epochId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.hash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PriceHashSubmittedEventResponse> priceHashSubmittedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, PriceHashSubmittedEventResponse>() {
            @Override
            public PriceHashSubmittedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PRICEHASHSUBMITTED_EVENT, log);
                PriceHashSubmittedEventResponse typedResponse = new PriceHashSubmittedEventResponse();
                typedResponse.log = log;
                typedResponse.submitter = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.epochId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.hash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PriceHashSubmittedEventResponse> priceHashSubmittedEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PRICEHASHSUBMITTED_EVENT));
        return priceHashSubmittedEventFlowable(filter);
    }

    public List<PriceRevealedEventResponse> getPriceRevealedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PRICEREVEALED_EVENT,
                transactionReceipt);
        ArrayList<PriceRevealedEventResponse> responses = new ArrayList<PriceRevealedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PriceRevealedEventResponse typedResponse = new PriceRevealedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.voter = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.epochId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.random = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.votePowerNat = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.votePowerAsset = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PriceRevealedEventResponse> priceRevealedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, PriceRevealedEventResponse>() {
            @Override
            public PriceRevealedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PRICEREVEALED_EVENT, log);
                PriceRevealedEventResponse typedResponse = new PriceRevealedEventResponse();
                typedResponse.log = log;
                typedResponse.voter = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.epochId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.random = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.votePowerNat = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.votePowerAsset = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PriceRevealedEventResponse> priceRevealedEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PRICEREVEALED_EVENT));
        return priceRevealedEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> ASSET_PRICE_USD_DECIMALS() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ASSET_PRICE_USD_DECIMALS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> activateFtso(BigInteger _firstEpochStartTs,
            BigInteger _submitPeriodSeconds, BigInteger _revealPeriodSeconds) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ACTIVATEFTSO,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_firstEpochStartTs),
                        new org.web3j.abi.datatypes.generated.Uint256(_submitPeriodSeconds),
                        new org.web3j.abi.datatypes.generated.Uint256(_revealPeriodSeconds)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> active() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ACTIVE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> assetFtsos(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ASSETFTSOS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> assets(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ASSETS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> configureEpochs(BigInteger _maxVotePowerNatThresholdFraction,
            BigInteger _maxVotePowerAssetThresholdFraction, BigInteger _lowAssetUSDThreshold,
            BigInteger _highAssetUSDThreshold, BigInteger _highAssetTurnoutThresholdBIPS,
            BigInteger _lowNatTurnoutThresholdBIPS, BigInteger _elasticBandRewardBIPS, BigInteger _elasticBandWidthPPM,
            List<String> _trustedAddresses) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CONFIGUREEPOCHS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_maxVotePowerNatThresholdFraction),
                        new org.web3j.abi.datatypes.generated.Uint256(_maxVotePowerAssetThresholdFraction),
                        new org.web3j.abi.datatypes.generated.Uint256(_lowAssetUSDThreshold),
                        new org.web3j.abi.datatypes.generated.Uint256(_highAssetUSDThreshold),
                        new org.web3j.abi.datatypes.generated.Uint256(_highAssetTurnoutThresholdBIPS),
                        new org.web3j.abi.datatypes.generated.Uint256(_lowNatTurnoutThresholdBIPS),
                        new org.web3j.abi.datatypes.generated.Uint256(_elasticBandRewardBIPS),
                        new org.web3j.abi.datatypes.generated.Uint256(_elasticBandWidthPPM),
                        new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                                org.web3j.abi.datatypes.Address.class,
                                org.web3j.abi.Utils.typeMap(_trustedAddresses, org.web3j.abi.datatypes.Address.class))),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> deactivateFtso() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEACTIVATEFTSO,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple9<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, List<String>>> epochsConfiguration() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_EPOCHSCONFIGURATION,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<DynamicArray<Address>>() {
                }));
        return new RemoteFunctionCall<Tuple9<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, List<String>>>(
                function,
                new Callable<Tuple9<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, List<String>>>() {
                    @Override
                    public Tuple9<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, List<String>> call()
                            throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple9<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, List<String>>(
                                (BigInteger) results.get(0).getValue(),
                                (BigInteger) results.get(1).getValue(),
                                (BigInteger) results.get(2).getValue(),
                                (BigInteger) results.get(3).getValue(),
                                (BigInteger) results.get(4).getValue(),
                                (BigInteger) results.get(5).getValue(),
                                (BigInteger) results.get(6).getValue(),
                                (BigInteger) results.get(7).getValue(),
                                convertToNative((List<Address>) results.get(8).getValue()));
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> fallbackFinalizePriceEpoch(BigInteger _epochId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FALLBACKFINALIZEPRICEEPOCH,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_epochId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> finalizePriceEpoch(BigInteger _epochId, Boolean _returnRewardData) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FINALIZEPRICEEPOCH,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_epochId),
                        new org.web3j.abi.datatypes.Bool(_returnRewardData)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> forceFinalizePriceEpoch(BigInteger _epochId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FORCEFINALIZEPRICEEPOCH,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_epochId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> ftsoManager() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_FTSOMANAGER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getAsset() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETASSET,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<List> getAssetFtsos() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETASSETFTSOS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {
                }));
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

    public RemoteFunctionCall<BigInteger> getCurrentEpochId() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCURRENTEPOCHID,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple2<BigInteger, BigInteger>> getCurrentPrice() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCURRENTPRICE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }));
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

    public RemoteFunctionCall<Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>> getCurrentPriceDetails() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETCURRENTPRICEDETAILS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint8>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint8>() {
                }));
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

    public RemoteFunctionCall<Tuple2<BigInteger, BigInteger>> getCurrentPriceFromTrustedProviders() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETCURRENTPRICEFROMTRUSTEDPROVIDERS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }));
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

    public RemoteFunctionCall<Tuple3<BigInteger, BigInteger, BigInteger>> getCurrentPriceWithDecimals() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETCURRENTPRICEWITHDECIMALS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }));
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

    public RemoteFunctionCall<Tuple3<BigInteger, BigInteger, BigInteger>> getCurrentPriceWithDecimalsFromTrustedProviders() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETCURRENTPRICEWITHDECIMALSFROMTRUSTEDPROVIDERS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }));
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

    public RemoteFunctionCall<BigInteger> getCurrentRandom() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCURRENTRANDOM,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getEpochId(BigInteger _timestamp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETEPOCHID,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_timestamp)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getEpochPrice(BigInteger _epochId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETEPOCHPRICE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_epochId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getEpochPriceForVoter(BigInteger _epochId, String _voter) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETEPOCHPRICEFORVOTER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_epochId),
                        new org.web3j.abi.datatypes.Address(160, _voter)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple3<BigInteger, BigInteger, BigInteger>> getPriceEpochConfiguration() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETPRICEEPOCHCONFIGURATION,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }));
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

    public RemoteFunctionCall<Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, Boolean>> getPriceEpochData() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETPRICEEPOCHDATA,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Bool>() {
                }));
        return new RemoteFunctionCall<Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, Boolean>>(function,
                new Callable<Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, Boolean>>() {
                    @Override
                    public Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, Boolean>(
                                (BigInteger) results.get(0).getValue(),
                                (BigInteger) results.get(1).getValue(),
                                (BigInteger) results.get(2).getValue(),
                                (BigInteger) results.get(3).getValue(),
                                (Boolean) results.get(4).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> getRandom(BigInteger _epochId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETRANDOM,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_epochId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple6<List<String>, List<BigInteger>, BigInteger, BigInteger, BigInteger, BigInteger>> getVoteWeightingParameters() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETVOTEWEIGHTINGPARAMETERS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {
                }, new TypeReference<DynamicArray<Uint256>>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Uint256>() {
                }));
        return new RemoteFunctionCall<Tuple6<List<String>, List<BigInteger>, BigInteger, BigInteger, BigInteger, BigInteger>>(
                function,
                new Callable<Tuple6<List<String>, List<BigInteger>, BigInteger, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple6<List<String>, List<BigInteger>, BigInteger, BigInteger, BigInteger, BigInteger> call()
                            throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<List<String>, List<BigInteger>, BigInteger, BigInteger, BigInteger, BigInteger>(
                                convertToNative((List<Address>) results.get(0).getValue()),
                                convertToNative((List<Uint256>) results.get(1).getValue()),
                                (BigInteger) results.get(2).getValue(),
                                (BigInteger) results.get(3).getValue(),
                                (BigInteger) results.get(4).getValue(),
                                (BigInteger) results.get(5).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> initializeCurrentEpochStateForReveal(BigInteger _circulatingSupplyNat,
            Boolean _fallbackMode) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INITIALIZECURRENTEPOCHSTATEFORREVEAL,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_circulatingSupplyNat),
                        new org.web3j.abi.datatypes.Bool(_fallbackMode)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> priceDeviationThresholdBIPS() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_PRICEDEVIATIONTHRESHOLDBIPS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> priceEpochCyclicBufferSize() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_PRICEEPOCHCYCLICBUFFERSIZE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> priceSubmitter() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PRICESUBMITTER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> revealPriceSubmitter(String _voter, BigInteger _epochId,
            BigInteger _price, BigInteger _random, BigInteger _voterWNatVP) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REVEALPRICESUBMITTER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _voter),
                        new org.web3j.abi.datatypes.generated.Uint256(_epochId),
                        new org.web3j.abi.datatypes.generated.Uint256(_price),
                        new org.web3j.abi.datatypes.generated.Uint256(_random),
                        new org.web3j.abi.datatypes.generated.Uint256(_voterWNatVP)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setAsset(String _asset) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETASSET,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _asset)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setAssetFtsos(List<String> _assetFtsos) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETASSETFTSOS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_assetFtsos, org.web3j.abi.datatypes.Address.class))),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setVotePowerBlock(BigInteger _votePowerBlock) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETVOTEPOWERBLOCK,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_votePowerBlock)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> submitPriceHashSubmitter(String _sender, BigInteger _epochId,
            byte[] _hash) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SUBMITPRICEHASHSUBMITTER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _sender),
                        new org.web3j.abi.datatypes.generated.Uint256(_epochId),
                        new org.web3j.abi.datatypes.generated.Bytes32(_hash)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> symbol() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SYMBOL,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> updateInitialPrice(BigInteger _initialPriceUSD,
            BigInteger _initialPriceTimestamp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATEINITIALPRICE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_initialPriceUSD),
                        new org.web3j.abi.datatypes.generated.Uint256(_initialPriceTimestamp)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> wNat() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_WNAT,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> wNatVotePowerCached(String _owner, BigInteger _epochId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_WNATVOTEPOWERCACHED,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _owner),
                        new org.web3j.abi.datatypes.generated.Uint256(_epochId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Ftso load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice,
            BigInteger gasLimit) {
        return new Ftso(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Ftso load(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new Ftso(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Ftso load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new Ftso(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Ftso load(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return new Ftso(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class LowTurnoutEventResponse extends BaseEventResponse {
        public BigInteger epochId;

        public BigInteger natTurnout;

        public BigInteger lowNatTurnoutThresholdBIPS;

        public BigInteger timestamp;
    }

    public static class PriceEpochInitializedOnFtsoEventResponse extends BaseEventResponse {
        public BigInteger epochId;

        public BigInteger endTime;

        public BigInteger timestamp;
    }

    public static class PriceFinalizedEventResponse extends BaseEventResponse {
        public BigInteger epochId;

        public BigInteger price;

        public Boolean rewardedFtso;

        public BigInteger lowIQRRewardPrice;

        public BigInteger highIQRRewardPrice;

        public BigInteger lowElasticBandRewardPrice;

        public BigInteger highElasticBandRewardPrice;

        public BigInteger finalizationType;

        public BigInteger timestamp;
    }

    public static class PriceHashSubmittedEventResponse extends BaseEventResponse {
        public String submitter;

        public BigInteger epochId;

        public byte[] hash;

        public BigInteger timestamp;
    }

    public static class PriceRevealedEventResponse extends BaseEventResponse {
        public String voter;

        public BigInteger epochId;

        public BigInteger price;

        public BigInteger random;

        public BigInteger timestamp;

        public BigInteger votePowerNat;

        public BigInteger votePowerAsset;
    }
}
