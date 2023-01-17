package com.ivy.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Keys;

import com.ivy.api.repository.UserRepository;
import com.ivy.api.repository.entity.UserEntity;
import com.ivy.api.util.EthersUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public Boolean authenticate(String address, String signature) {
        UserEntity user = userRepository.getByAddress(address.toLowerCase());
        if (user == null) {
            return false;
        }

        String message = String.format("Please sign this message for authentication: %d", user.getNonce());
        try {
            String recoveredAddress = EthersUtils.verifyMessage(message, signature);
            return Keys.toChecksumAddress(address).equals(Keys.toChecksumAddress(recoveredAddress));
        } catch (Exception e) {
            log.error(e);
            return false;
        }

    }

}
