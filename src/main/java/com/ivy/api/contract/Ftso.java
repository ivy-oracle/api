package com.ivy.api.contract;

import org.web3j.protocol.core.RemoteFunctionCall;

public interface Ftso {
    public String getContractAddress();

    public RemoteFunctionCall<String> symbol();
}
