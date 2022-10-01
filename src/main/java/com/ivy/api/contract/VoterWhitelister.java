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
public class VoterWhitelister extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_ADDFTSO = "addFtso";

    public static final String FUNC_CLAIMGOVERNANCE = "claimGovernance";

    public static final String FUNC_DEFAULTMAXVOTERSFORFTSO = "defaultMaxVotersForFtso";

    public static final String FUNC_FTSOMANAGER = "ftsoManager";

    public static final String FUNC_FTSOREGISTRY = "ftsoRegistry";

    public static final String FUNC_GETFTSOWHITELISTEDPRICEPROVIDERS = "getFtsoWhitelistedPriceProviders";

    public static final String FUNC_GETFTSOWHITELISTEDPRICEPROVIDERSBYSYMBOL = "getFtsoWhitelistedPriceProvidersBySymbol";

    public static final String FUNC_GOVERNANCE = "governance";

    public static final String FUNC_INITIALISE = "initialise";

    public static final String FUNC_MAXVOTERSFORFTSO = "maxVotersForFtso";

    public static final String FUNC_PRICESUBMITTER = "priceSubmitter";

    public static final String FUNC_PROPOSEGOVERNANCE = "proposeGovernance";

    public static final String FUNC_PROPOSEDGOVERNANCE = "proposedGovernance";

    public static final String FUNC_REMOVEFTSO = "removeFtso";

    public static final String FUNC_REMOVETRUSTEDADDRESSFROMWHITELIST = "removeTrustedAddressFromWhitelist";

    public static final String FUNC_REQUESTFULLVOTERWHITELISTING = "requestFullVoterWhitelisting";

    public static final String FUNC_REQUESTWHITELISTINGVOTER = "requestWhitelistingVoter";

    public static final String FUNC_SETCONTRACTADDRESSES = "setContractAddresses";

    public static final String FUNC_SETDEFAULTMAXVOTERSFORFTSO = "setDefaultMaxVotersForFtso";

    public static final String FUNC_SETMAXVOTERSFORFTSO = "setMaxVotersForFtso";

    public static final String FUNC_TRANSFERGOVERNANCE = "transferGovernance";

    public static final Event GOVERNANCEPROPOSED_EVENT = new Event("GovernanceProposed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event GOVERNANCEUPDATED_EVENT = new Event("GovernanceUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event VOTERREMOVEDFROMWHITELIST_EVENT = new Event("VoterRemovedFromWhitelist", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event VOTERWHITELISTED_EVENT = new Event("VoterWhitelisted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected VoterWhitelister(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected VoterWhitelister(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected VoterWhitelister(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected VoterWhitelister(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
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

    public List<VoterRemovedFromWhitelistEventResponse> getVoterRemovedFromWhitelistEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(VOTERREMOVEDFROMWHITELIST_EVENT, transactionReceipt);
        ArrayList<VoterRemovedFromWhitelistEventResponse> responses = new ArrayList<VoterRemovedFromWhitelistEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            VoterRemovedFromWhitelistEventResponse typedResponse = new VoterRemovedFromWhitelistEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.voter = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.ftsoIndex = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<VoterRemovedFromWhitelistEventResponse> voterRemovedFromWhitelistEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, VoterRemovedFromWhitelistEventResponse>() {
            @Override
            public VoterRemovedFromWhitelistEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(VOTERREMOVEDFROMWHITELIST_EVENT, log);
                VoterRemovedFromWhitelistEventResponse typedResponse = new VoterRemovedFromWhitelistEventResponse();
                typedResponse.log = log;
                typedResponse.voter = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.ftsoIndex = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<VoterRemovedFromWhitelistEventResponse> voterRemovedFromWhitelistEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VOTERREMOVEDFROMWHITELIST_EVENT));
        return voterRemovedFromWhitelistEventFlowable(filter);
    }

    public List<VoterWhitelistedEventResponse> getVoterWhitelistedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(VOTERWHITELISTED_EVENT, transactionReceipt);
        ArrayList<VoterWhitelistedEventResponse> responses = new ArrayList<VoterWhitelistedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            VoterWhitelistedEventResponse typedResponse = new VoterWhitelistedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.voter = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.ftsoIndex = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<VoterWhitelistedEventResponse> voterWhitelistedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, VoterWhitelistedEventResponse>() {
            @Override
            public VoterWhitelistedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(VOTERWHITELISTED_EVENT, log);
                VoterWhitelistedEventResponse typedResponse = new VoterWhitelistedEventResponse();
                typedResponse.log = log;
                typedResponse.voter = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.ftsoIndex = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<VoterWhitelistedEventResponse> voterWhitelistedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VOTERWHITELISTED_EVENT));
        return voterWhitelistedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> addFtso(BigInteger _ftsoIndex) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDFTSO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_ftsoIndex)), 
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

    public RemoteFunctionCall<BigInteger> defaultMaxVotersForFtso() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DEFAULTMAXVOTERSFORFTSO, 
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

    public RemoteFunctionCall<String> ftsoRegistry() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_FTSOREGISTRY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<List> getFtsoWhitelistedPriceProviders(BigInteger _ftsoIndex) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETFTSOWHITELISTEDPRICEPROVIDERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_ftsoIndex)), 
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

    public RemoteFunctionCall<List> getFtsoWhitelistedPriceProvidersBySymbol(String _symbol) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETFTSOWHITELISTEDPRICEPROVIDERSBYSYMBOL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_symbol)), 
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

    public RemoteFunctionCall<BigInteger> maxVotersForFtso(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_MAXVOTERSFORFTSO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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

    public RemoteFunctionCall<TransactionReceipt> removeFtso(BigInteger _ftsoIndex) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REMOVEFTSO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_ftsoIndex)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> removeTrustedAddressFromWhitelist(String _trustedAddress, BigInteger _ftsoIndex) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REMOVETRUSTEDADDRESSFROMWHITELIST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _trustedAddress), 
                new org.web3j.abi.datatypes.generated.Uint256(_ftsoIndex)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> requestFullVoterWhitelisting(String _voter) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REQUESTFULLVOTERWHITELISTING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _voter)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> requestWhitelistingVoter(String _voter, BigInteger _ftsoIndex) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REQUESTWHITELISTINGVOTER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _voter), 
                new org.web3j.abi.datatypes.generated.Uint256(_ftsoIndex)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setContractAddresses(String _ftsoRegistry, String _ftsoManager) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETCONTRACTADDRESSES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _ftsoRegistry), 
                new org.web3j.abi.datatypes.Address(160, _ftsoManager)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setDefaultMaxVotersForFtso(BigInteger _defaultMaxVotersForFtso) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETDEFAULTMAXVOTERSFORFTSO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_defaultMaxVotersForFtso)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setMaxVotersForFtso(BigInteger _ftsoIndex, BigInteger _newMaxVoters) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETMAXVOTERSFORFTSO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_ftsoIndex), 
                new org.web3j.abi.datatypes.generated.Uint256(_newMaxVoters)), 
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
    public static VoterWhitelister load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new VoterWhitelister(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static VoterWhitelister load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new VoterWhitelister(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static VoterWhitelister load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new VoterWhitelister(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static VoterWhitelister load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new VoterWhitelister(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class GovernanceProposedEventResponse extends BaseEventResponse {
        public String proposedGovernance;
    }

    public static class GovernanceUpdatedEventResponse extends BaseEventResponse {
        public String oldGovernance;

        public String newGoveranance;
    }

    public static class VoterRemovedFromWhitelistEventResponse extends BaseEventResponse {
        public String voter;

        public BigInteger ftsoIndex;
    }

    public static class VoterWhitelistedEventResponse extends BaseEventResponse {
        public String voter;

        public BigInteger ftsoIndex;
    }
}
