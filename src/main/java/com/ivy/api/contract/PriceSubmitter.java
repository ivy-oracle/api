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
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
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
public class PriceSubmitter extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_CLAIMGOVERNANCE = "claimGovernance";

    public static final String FUNC_GETFTSOMANAGER = "getFtsoManager";

    public static final String FUNC_GETFTSOREGISTRY = "getFtsoRegistry";

    public static final String FUNC_GETTRUSTEDADDRESSES = "getTrustedAddresses";

    public static final String FUNC_GETVOTERWHITELISTER = "getVoterWhitelister";

    public static final String FUNC_GOVERNANCE = "governance";

    public static final String FUNC_INITIALISE = "initialise";

    public static final String FUNC_INITIALISEFIXEDADDRESS = "initialiseFixedAddress";

    public static final String FUNC_PROPOSEGOVERNANCE = "proposeGovernance";

    public static final String FUNC_PROPOSEDGOVERNANCE = "proposedGovernance";

    public static final String FUNC_REVEALPRICES = "revealPrices";

    public static final String FUNC_SETCONTRACTADDRESSES = "setContractAddresses";

    public static final String FUNC_SETTRUSTEDADDRESSES = "setTrustedAddresses";

    public static final String FUNC_SUBMITPRICEHASHES = "submitPriceHashes";

    public static final String FUNC_TRANSFERGOVERNANCE = "transferGovernance";

    public static final String FUNC_VOTERWHITELISTBITMAP = "voterWhitelistBitmap";

    public static final String FUNC_VOTERWHITELISTED = "voterWhitelisted";

    public static final String FUNC_VOTERSREMOVEDFROMWHITELIST = "votersRemovedFromWhitelist";

    public static final Event GOVERNANCEPROPOSED_EVENT = new Event("GovernanceProposed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event GOVERNANCEUPDATED_EVENT = new Event("GovernanceUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event PRICEHASHESSUBMITTED_EVENT = new Event("PriceHashesSubmitted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}, new TypeReference<DynamicArray<Address>>() {}, new TypeReference<DynamicArray<Bytes32>>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event PRICESREVEALED_EVENT = new Event("PricesRevealed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}, new TypeReference<DynamicArray<Address>>() {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected PriceSubmitter(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PriceSubmitter(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected PriceSubmitter(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected PriceSubmitter(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
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

    public List<PriceHashesSubmittedEventResponse> getPriceHashesSubmittedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PRICEHASHESSUBMITTED_EVENT, transactionReceipt);
        ArrayList<PriceHashesSubmittedEventResponse> responses = new ArrayList<PriceHashesSubmittedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PriceHashesSubmittedEventResponse typedResponse = new PriceHashesSubmittedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.submitter = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.epochId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.ftsos = (List<String>) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.hashes = (List<byte[]>) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PriceHashesSubmittedEventResponse> priceHashesSubmittedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, PriceHashesSubmittedEventResponse>() {
            @Override
            public PriceHashesSubmittedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PRICEHASHESSUBMITTED_EVENT, log);
                PriceHashesSubmittedEventResponse typedResponse = new PriceHashesSubmittedEventResponse();
                typedResponse.log = log;
                typedResponse.submitter = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.epochId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.ftsos = (List<String>) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.hashes = (List<byte[]>) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PriceHashesSubmittedEventResponse> priceHashesSubmittedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PRICEHASHESSUBMITTED_EVENT));
        return priceHashesSubmittedEventFlowable(filter);
    }

    public List<PricesRevealedEventResponse> getPricesRevealedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PRICESREVEALED_EVENT, transactionReceipt);
        ArrayList<PricesRevealedEventResponse> responses = new ArrayList<PricesRevealedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PricesRevealedEventResponse typedResponse = new PricesRevealedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.voter = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.epochId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.ftsos = (List<String>) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.prices = (List<BigInteger>) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.randoms = (List<BigInteger>) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PricesRevealedEventResponse> pricesRevealedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, PricesRevealedEventResponse>() {
            @Override
            public PricesRevealedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PRICESREVEALED_EVENT, log);
                PricesRevealedEventResponse typedResponse = new PricesRevealedEventResponse();
                typedResponse.log = log;
                typedResponse.voter = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.epochId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.ftsos = (List<String>) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.prices = (List<BigInteger>) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.randoms = (List<BigInteger>) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PricesRevealedEventResponse> pricesRevealedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PRICESREVEALED_EVENT));
        return pricesRevealedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> claimGovernance() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CLAIMGOVERNANCE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> getFtsoManager() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETFTSOMANAGER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getFtsoRegistry() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETFTSOREGISTRY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<List> getTrustedAddresses() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETTRUSTEDADDRESSES, 
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

    public RemoteFunctionCall<String> getVoterWhitelister() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETVOTERWHITELISTER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> governance() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GOVERNANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> initialiseFixedAddress() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INITIALISEFIXEDADDRESS, 
                Arrays.<Type>asList(), 
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

    public RemoteFunctionCall<TransactionReceipt> revealPrices(BigInteger _epochId, List<BigInteger> _ftsoIndices, List<BigInteger> _prices, List<BigInteger> _randoms) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REVEALPRICES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_epochId), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_ftsoIndices, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_prices, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_randoms, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setContractAddresses(String _ftsoRegistry, String _voterWhitelister, String _ftsoManager) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETCONTRACTADDRESSES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _ftsoRegistry), 
                new org.web3j.abi.datatypes.Address(160, _voterWhitelister), 
                new org.web3j.abi.datatypes.Address(160, _ftsoManager)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setTrustedAddresses(List<String> _trustedAddresses) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETTRUSTEDADDRESSES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_trustedAddresses, org.web3j.abi.datatypes.Address.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> submitPriceHashes(BigInteger _epochId, List<BigInteger> _ftsoIndices, List<byte[]> _hashes) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SUBMITPRICEHASHES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_epochId), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_ftsoIndices, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_hashes, org.web3j.abi.datatypes.generated.Bytes32.class))), 
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

    public RemoteFunctionCall<BigInteger> voterWhitelistBitmap(String _voter) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VOTERWHITELISTBITMAP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _voter)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> voterWhitelisted(String _voter, BigInteger _ftsoIndex) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_VOTERWHITELISTED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _voter), 
                new org.web3j.abi.datatypes.generated.Uint256(_ftsoIndex)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> votersRemovedFromWhitelist(List<String> _removedVoters, BigInteger _ftsoIndex) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_VOTERSREMOVEDFROMWHITELIST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_removedVoters, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.generated.Uint256(_ftsoIndex)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static PriceSubmitter load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new PriceSubmitter(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static PriceSubmitter load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PriceSubmitter(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static PriceSubmitter load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new PriceSubmitter(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static PriceSubmitter load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new PriceSubmitter(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class GovernanceProposedEventResponse extends BaseEventResponse {
        public String proposedGovernance;
    }

    public static class GovernanceUpdatedEventResponse extends BaseEventResponse {
        public String oldGovernance;

        public String newGoveranance;
    }

    public static class PriceHashesSubmittedEventResponse extends BaseEventResponse {
        public String submitter;

        public BigInteger epochId;

        public List<String> ftsos;

        public List<byte[]> hashes;

        public BigInteger timestamp;
    }

    public static class PricesRevealedEventResponse extends BaseEventResponse {
        public String voter;

        public BigInteger epochId;

        public List<String> ftsos;

        public List<BigInteger> prices;

        public List<BigInteger> randoms;

        public BigInteger timestamp;
    }
}
