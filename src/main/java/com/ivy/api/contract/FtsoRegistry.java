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
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.StaticArray5;
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
public class FtsoRegistry extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_ADDFTSO = "addFtso";

    public static final String FUNC_CLAIMGOVERNANCE = "claimGovernance";

    public static final String FUNC_FTSOMANAGER = "ftsoManager";

    public static final String FUNC_GETALLFTSOS = "getAllFtsos";

    public static final String FUNC_getCurrentPrice = "getCurrentPrice";

    public static final String FUNC_GETFTSO = "getFtso";

    public static final String FUNC_GETFTSOBYSYMBOL = "getFtsoBySymbol";

    public static final String FUNC_GETFTSOHISTORY = "getFtsoHistory";

    public static final String FUNC_GETFTSOINDEX = "getFtsoIndex";

    public static final String FUNC_GETFTSOSYMBOL = "getFtsoSymbol";

    public static final String FUNC_GETFTSOS = "getFtsos";

    public static final String FUNC_GETSUPPORTEDFTSOS = "getSupportedFtsos";

    public static final String FUNC_GETSUPPORTEDINDICES = "getSupportedIndices";

    public static final String FUNC_GETSUPPORTEDINDICESANDFTSOS = "getSupportedIndicesAndFtsos";

    public static final String FUNC_GETSUPPORTEDINDICESANDSYMBOLS = "getSupportedIndicesAndSymbols";

    public static final String FUNC_GETSUPPORTEDINDICESSYMBOLSANDFTSOS = "getSupportedIndicesSymbolsAndFtsos";

    public static final String FUNC_GETSUPPORTEDSYMBOLS = "getSupportedSymbols";

    public static final String FUNC_GETSUPPORTEDSYMBOLSANDFTSOS = "getSupportedSymbolsAndFtsos";

    public static final String FUNC_GOVERNANCE = "governance";

    public static final String FUNC_INITIALISE = "initialise";

    public static final String FUNC_PROPOSEGOVERNANCE = "proposeGovernance";

    public static final String FUNC_PROPOSEDGOVERNANCE = "proposedGovernance";

    public static final String FUNC_REMOVEFTSO = "removeFtso";

    public static final String FUNC_SETFTSOMANAGERADDRESS = "setFtsoManagerAddress";

    public static final String FUNC_TRANSFERGOVERNANCE = "transferGovernance";

    public static final Event GOVERNANCEPROPOSED_EVENT = new Event("GovernanceProposed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event GOVERNANCEUPDATED_EVENT = new Event("GovernanceUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
    ;

    @Deprecated
    protected FtsoRegistry(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected FtsoRegistry(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected FtsoRegistry(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected FtsoRegistry(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
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

    public RemoteFunctionCall<TransactionReceipt> addFtso(String _ftsoContract) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDFTSO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _ftsoContract)), 
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

    public RemoteFunctionCall<String> ftsoManager() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_FTSOMANAGER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<List> getAllFtsos() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETALLFTSOS, 
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

    public RemoteFunctionCall<Tuple2<BigInteger, BigInteger>> getCurrentPrice(String _symbol) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_getCurrentPrice, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_symbol)), 
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

    public RemoteFunctionCall<Tuple2<BigInteger, BigInteger>> getCurrentPrice(BigInteger _assetIndex) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_getCurrentPrice, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_assetIndex)), 
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

    public RemoteFunctionCall<String> getFtso(BigInteger _assetIndex) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETFTSO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_assetIndex)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getFtsoBySymbol(String _symbol) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETFTSOBYSYMBOL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_symbol)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<List> getFtsoHistory(BigInteger _assetIndex) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETFTSOHISTORY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_assetIndex)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<StaticArray5<Address>>() {}));
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

    public RemoteFunctionCall<BigInteger> getFtsoIndex(String _symbol) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETFTSOINDEX, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_symbol)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> getFtsoSymbol(BigInteger _assetIndex) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETFTSOSYMBOL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_assetIndex)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<List> getFtsos(List<BigInteger> _assetIndices) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETFTSOS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_assetIndices, org.web3j.abi.datatypes.generated.Uint256.class))), 
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

    public RemoteFunctionCall<List> getSupportedFtsos() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETSUPPORTEDFTSOS, 
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

    public RemoteFunctionCall<List> getSupportedIndices() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETSUPPORTEDINDICES, 
                Arrays.<Type>asList(), 
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

    public RemoteFunctionCall<Tuple2<List<BigInteger>, List<String>>> getSupportedIndicesAndFtsos() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETSUPPORTEDINDICESANDFTSOS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Address>>() {}));
        return new RemoteFunctionCall<Tuple2<List<BigInteger>, List<String>>>(function,
                new Callable<Tuple2<List<BigInteger>, List<String>>>() {
                    @Override
                    public Tuple2<List<BigInteger>, List<String>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<List<BigInteger>, List<String>>(
                                convertToNative((List<Uint256>) results.get(0).getValue()), 
                                convertToNative((List<Address>) results.get(1).getValue()));
                    }
                });
    }

    public RemoteFunctionCall<Tuple2<List<BigInteger>, List<String>>> getSupportedIndicesAndSymbols() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETSUPPORTEDINDICESANDSYMBOLS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Utf8String>>() {}));
        return new RemoteFunctionCall<Tuple2<List<BigInteger>, List<String>>>(function,
                new Callable<Tuple2<List<BigInteger>, List<String>>>() {
                    @Override
                    public Tuple2<List<BigInteger>, List<String>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<List<BigInteger>, List<String>>(
                                convertToNative((List<Uint256>) results.get(0).getValue()), 
                                convertToNative((List<Utf8String>) results.get(1).getValue()));
                    }
                });
    }

    public RemoteFunctionCall<Tuple3<List<BigInteger>, List<String>, List<String>>> getSupportedIndicesSymbolsAndFtsos() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETSUPPORTEDINDICESSYMBOLSANDFTSOS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Utf8String>>() {}, new TypeReference<DynamicArray<Address>>() {}));
        return new RemoteFunctionCall<Tuple3<List<BigInteger>, List<String>, List<String>>>(function,
                new Callable<Tuple3<List<BigInteger>, List<String>, List<String>>>() {
                    @Override
                    public Tuple3<List<BigInteger>, List<String>, List<String>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<List<BigInteger>, List<String>, List<String>>(
                                convertToNative((List<Uint256>) results.get(0).getValue()), 
                                convertToNative((List<Utf8String>) results.get(1).getValue()), 
                                convertToNative((List<Address>) results.get(2).getValue()));
                    }
                });
    }

    public RemoteFunctionCall<List> getSupportedSymbols() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETSUPPORTEDSYMBOLS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Utf8String>>() {}));
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

    public RemoteFunctionCall<Tuple2<List<String>, List<String>>> getSupportedSymbolsAndFtsos() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETSUPPORTEDSYMBOLSANDFTSOS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Utf8String>>() {}, new TypeReference<DynamicArray<Address>>() {}));
        return new RemoteFunctionCall<Tuple2<List<String>, List<String>>>(function,
                new Callable<Tuple2<List<String>, List<String>>>() {
                    @Override
                    public Tuple2<List<String>, List<String>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<List<String>, List<String>>(
                                convertToNative((List<Utf8String>) results.get(0).getValue()), 
                                convertToNative((List<Address>) results.get(1).getValue()));
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

    public RemoteFunctionCall<TransactionReceipt> setFtsoManagerAddress(String _ftsoManager) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETFTSOMANAGERADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _ftsoManager)), 
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

    @Deprecated
    public static FtsoRegistry load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new FtsoRegistry(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static FtsoRegistry load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new FtsoRegistry(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static FtsoRegistry load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new FtsoRegistry(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static FtsoRegistry load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new FtsoRegistry(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class GovernanceProposedEventResponse extends BaseEventResponse {
        public String proposedGovernance;
    }

    public static class GovernanceUpdatedEventResponse extends BaseEventResponse {
        public String oldGovernance;

        public String newGoveranance;
    }
}
