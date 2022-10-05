package com.ivy.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class Web3jConfiguration {
    @Bean
    Web3j web3j(@Value("${web3.rpc.url}") String rpcUrl) {
        return Web3j.build(new HttpService(rpcUrl));
    }
}
