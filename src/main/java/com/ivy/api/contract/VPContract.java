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
public class VPContract extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_BATCHVOTEPOWEROFAT = "batchVotePowerOfAt";

    public static final String FUNC_CLEANUPBLOCKNUMBER = "cleanupBlockNumber";

    public static final String FUNC_DELEGATE = "delegate";

    public static final String FUNC_DELEGATEEXPLICIT = "delegateExplicit";

    public static final String FUNC_DELEGATESOF = "delegatesOf";

    public static final String FUNC_DELEGATESOFAT = "delegatesOfAt";

    public static final String FUNC_DELEGATIONMODEOF = "delegationModeOf";

    public static final String FUNC_EXPLICITDELEGATIONHISTORYCLEANUP = "explicitDelegationHistoryCleanup";

    public static final String FUNC_ISREPLACEMENT = "isReplacement";

    public static final String FUNC_OWNERTOKEN = "ownerToken";

    public static final String FUNC_PERCENTAGEDELEGATIONHISTORYCLEANUP = "percentageDelegationHistoryCleanup";

    public static final String FUNC_REVOCATIONCLEANUP = "revocationCleanup";

    public static final String FUNC_REVOKEDELEGATIONAT = "revokeDelegationAt";

    public static final String FUNC_SETCLEANERCONTRACT = "setCleanerContract";

    public static final String FUNC_SETCLEANUPBLOCKNUMBER = "setCleanupBlockNumber";

    public static final String FUNC_SETCLEANUPBLOCKNUMBERMANAGER = "setCleanupBlockNumberManager";

    public static final String FUNC_UNDELEGATEALL = "undelegateAll";

    public static final String FUNC_UNDELEGATEALLEXPLICIT = "undelegateAllExplicit";

    public static final String FUNC_UNDELEGATEDVOTEPOWEROF = "undelegatedVotePowerOf";

    public static final String FUNC_UNDELEGATEDVOTEPOWEROFAT = "undelegatedVotePowerOfAt";

    public static final String FUNC_UPDATEATTOKENTRANSFER = "updateAtTokenTransfer";

    public static final String FUNC_VOTEPOWERCACHECLEANUP = "votePowerCacheCleanup";

    public static final String FUNC_VOTEPOWERFROMTO = "votePowerFromTo";

    public static final String FUNC_VOTEPOWERFROMTOAT = "votePowerFromToAt";

    public static final String FUNC_VOTEPOWERHISTORYCLEANUP = "votePowerHistoryCleanup";

    public static final String FUNC_VOTEPOWEROF = "votePowerOf";

    public static final String FUNC_VOTEPOWEROFAT = "votePowerOfAt";

    public static final String FUNC_VOTEPOWEROFATCACHED = "votePowerOfAtCached";

    public static final Event CREATEDVOTEPOWERCACHE_EVENT = new Event("CreatedVotePowerCache", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event DELEGATE_EVENT = new Event("Delegate", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event REVOKE_EVENT = new Event("Revoke", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected VPContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected VPContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected VPContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected VPContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<CreatedVotePowerCacheEventResponse> getCreatedVotePowerCacheEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CREATEDVOTEPOWERCACHE_EVENT, transactionReceipt);
        ArrayList<CreatedVotePowerCacheEventResponse> responses = new ArrayList<CreatedVotePowerCacheEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CreatedVotePowerCacheEventResponse typedResponse = new CreatedVotePowerCacheEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._blockNumber = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CreatedVotePowerCacheEventResponse> createdVotePowerCacheEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, CreatedVotePowerCacheEventResponse>() {
            @Override
            public CreatedVotePowerCacheEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CREATEDVOTEPOWERCACHE_EVENT, log);
                CreatedVotePowerCacheEventResponse typedResponse = new CreatedVotePowerCacheEventResponse();
                typedResponse.log = log;
                typedResponse._owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._blockNumber = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CreatedVotePowerCacheEventResponse> createdVotePowerCacheEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CREATEDVOTEPOWERCACHE_EVENT));
        return createdVotePowerCacheEventFlowable(filter);
    }

    public List<DelegateEventResponse> getDelegateEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(DELEGATE_EVENT, transactionReceipt);
        ArrayList<DelegateEventResponse> responses = new ArrayList<DelegateEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DelegateEventResponse typedResponse = new DelegateEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.priorVotePower = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.newVotePower = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<DelegateEventResponse> delegateEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, DelegateEventResponse>() {
            @Override
            public DelegateEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(DELEGATE_EVENT, log);
                DelegateEventResponse typedResponse = new DelegateEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.priorVotePower = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.newVotePower = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DelegateEventResponse> delegateEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DELEGATE_EVENT));
        return delegateEventFlowable(filter);
    }

    public List<RevokeEventResponse> getRevokeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(REVOKE_EVENT, transactionReceipt);
        ArrayList<RevokeEventResponse> responses = new ArrayList<RevokeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RevokeEventResponse typedResponse = new RevokeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.delegator = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.delegatee = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.votePower = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.blockNumber = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RevokeEventResponse> revokeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, RevokeEventResponse>() {
            @Override
            public RevokeEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(REVOKE_EVENT, log);
                RevokeEventResponse typedResponse = new RevokeEventResponse();
                typedResponse.log = log;
                typedResponse.delegator = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.delegatee = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.votePower = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.blockNumber = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RevokeEventResponse> revokeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REVOKE_EVENT));
        return revokeEventFlowable(filter);
    }

    public RemoteFunctionCall<List> batchVotePowerOfAt(List<String> _owners, BigInteger _blockNumber) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_BATCHVOTEPOWEROFAT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_owners, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.generated.Uint256(_blockNumber)), 
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

    public RemoteFunctionCall<BigInteger> cleanupBlockNumber() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CLEANUPBLOCKNUMBER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> delegate(String _from, String _to, BigInteger _balance, BigInteger _bips) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DELEGATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _from), 
                new org.web3j.abi.datatypes.Address(160, _to), 
                new org.web3j.abi.datatypes.generated.Uint256(_balance), 
                new org.web3j.abi.datatypes.generated.Uint256(_bips)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> delegateExplicit(String _from, String _to, BigInteger _balance, BigInteger _amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DELEGATEEXPLICIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _from), 
                new org.web3j.abi.datatypes.Address(160, _to), 
                new org.web3j.abi.datatypes.generated.Uint256(_balance), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple4<List<String>, List<BigInteger>, BigInteger, BigInteger>> delegatesOf(String _owner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DELEGATESOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _owner)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple4<List<String>, List<BigInteger>, BigInteger, BigInteger>>(function,
                new Callable<Tuple4<List<String>, List<BigInteger>, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple4<List<String>, List<BigInteger>, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<List<String>, List<BigInteger>, BigInteger, BigInteger>(
                                convertToNative((List<Address>) results.get(0).getValue()), 
                                convertToNative((List<Uint256>) results.get(1).getValue()), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple4<List<String>, List<BigInteger>, BigInteger, BigInteger>> delegatesOfAt(String _owner, BigInteger _blockNumber) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DELEGATESOFAT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _owner), 
                new org.web3j.abi.datatypes.generated.Uint256(_blockNumber)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple4<List<String>, List<BigInteger>, BigInteger, BigInteger>>(function,
                new Callable<Tuple4<List<String>, List<BigInteger>, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple4<List<String>, List<BigInteger>, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<List<String>, List<BigInteger>, BigInteger, BigInteger>(
                                convertToNative((List<Address>) results.get(0).getValue()), 
                                convertToNative((List<Uint256>) results.get(1).getValue()), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> delegationModeOf(String _who) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DELEGATIONMODEOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _who)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> explicitDelegationHistoryCleanup(String _from, String _to, BigInteger _count) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_EXPLICITDELEGATIONHISTORYCLEANUP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _from), 
                new org.web3j.abi.datatypes.Address(160, _to), 
                new org.web3j.abi.datatypes.generated.Uint256(_count)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> isReplacement() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISREPLACEMENT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> ownerToken() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNERTOKEN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> percentageDelegationHistoryCleanup(String _owner, BigInteger _count) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_PERCENTAGEDELEGATIONHISTORYCLEANUP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _owner), 
                new org.web3j.abi.datatypes.generated.Uint256(_count)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> revocationCleanup(String _from, String _to, BigInteger _blockNumber) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REVOCATIONCLEANUP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _from), 
                new org.web3j.abi.datatypes.Address(160, _to), 
                new org.web3j.abi.datatypes.generated.Uint256(_blockNumber)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> revokeDelegationAt(String _from, String _to, BigInteger _balance, BigInteger _blockNumber) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REVOKEDELEGATIONAT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _from), 
                new org.web3j.abi.datatypes.Address(160, _to), 
                new org.web3j.abi.datatypes.generated.Uint256(_balance), 
                new org.web3j.abi.datatypes.generated.Uint256(_blockNumber)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setCleanerContract(String _cleanerContract) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETCLEANERCONTRACT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cleanerContract)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setCleanupBlockNumber(BigInteger _blockNumber) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETCLEANUPBLOCKNUMBER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockNumber)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setCleanupBlockNumberManager(String _cbnManager) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETCLEANUPBLOCKNUMBERMANAGER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cbnManager)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> undelegateAll(String _from, BigInteger _balance) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UNDELEGATEALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _from), 
                new org.web3j.abi.datatypes.generated.Uint256(_balance)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> undelegateAllExplicit(String _from, List<String> _delegateAddresses) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UNDELEGATEALLEXPLICIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _from), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_delegateAddresses, org.web3j.abi.datatypes.Address.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> undelegatedVotePowerOf(String _owner, BigInteger _balance) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_UNDELEGATEDVOTEPOWEROF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _owner), 
                new org.web3j.abi.datatypes.generated.Uint256(_balance)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> undelegatedVotePowerOfAt(String _owner, BigInteger _balance, BigInteger _blockNumber) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_UNDELEGATEDVOTEPOWEROFAT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _owner), 
                new org.web3j.abi.datatypes.generated.Uint256(_balance), 
                new org.web3j.abi.datatypes.generated.Uint256(_blockNumber)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> updateAtTokenTransfer(String _from, String _to, BigInteger _fromBalance, BigInteger _toBalance, BigInteger _amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATEATTOKENTRANSFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _from), 
                new org.web3j.abi.datatypes.Address(160, _to), 
                new org.web3j.abi.datatypes.generated.Uint256(_fromBalance), 
                new org.web3j.abi.datatypes.generated.Uint256(_toBalance), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> votePowerCacheCleanup(String _owner, BigInteger _blockNumber) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_VOTEPOWERCACHECLEANUP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _owner), 
                new org.web3j.abi.datatypes.generated.Uint256(_blockNumber)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> votePowerFromTo(String _from, String _to, BigInteger _balance) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VOTEPOWERFROMTO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _from), 
                new org.web3j.abi.datatypes.Address(160, _to), 
                new org.web3j.abi.datatypes.generated.Uint256(_balance)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> votePowerFromToAt(String _from, String _to, BigInteger _balance, BigInteger _blockNumber) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VOTEPOWERFROMTOAT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _from), 
                new org.web3j.abi.datatypes.Address(160, _to), 
                new org.web3j.abi.datatypes.generated.Uint256(_balance), 
                new org.web3j.abi.datatypes.generated.Uint256(_blockNumber)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> votePowerHistoryCleanup(String _owner, BigInteger _count) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_VOTEPOWERHISTORYCLEANUP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _owner), 
                new org.web3j.abi.datatypes.generated.Uint256(_count)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> votePowerOf(String _who) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VOTEPOWEROF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _who)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> votePowerOfAt(String _who, BigInteger _blockNumber) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VOTEPOWEROFAT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _who), 
                new org.web3j.abi.datatypes.generated.Uint256(_blockNumber)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> votePowerOfAtCached(String _who, BigInteger _blockNumber) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_VOTEPOWEROFATCACHED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _who), 
                new org.web3j.abi.datatypes.generated.Uint256(_blockNumber)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static VPContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new VPContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static VPContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new VPContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static VPContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new VPContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static VPContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new VPContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class CreatedVotePowerCacheEventResponse extends BaseEventResponse {
        public String _owner;

        public BigInteger _blockNumber;
    }

    public static class DelegateEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger priorVotePower;

        public BigInteger newVotePower;
    }

    public static class RevokeEventResponse extends BaseEventResponse {
        public String delegator;

        public String delegatee;

        public BigInteger votePower;

        public BigInteger blockNumber;
    }
}
