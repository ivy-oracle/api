package com.ivy.api.service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

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

    public UserEntity register(String address) {
        UserEntity user = userRepository.getByAddress(address.toLowerCase());
        if (user != null) {
            return user;
        }
        int nonce = generateNonce();
        user = new UserEntity(address.toLowerCase(), nonce, new Date(), new Date(), new Date());
        userRepository.save(user);
        return user;
    }

    public String getLoginMessageToSign(String address) {
        UserEntity user = userRepository.getByAddress(address.toLowerCase());
        if (user == null) {
            return null;
        }
        // Re-generate nonce if 7 days has passed since last update
        if (user.getNonceUpdatedAt().getTime() - new Date().getTime() > 1000 * 60 * 60 * 24 * 7) {
            int nonce = generateNonce();
            user.setNonce(nonce);
            user.setNonceUpdatedAt(new Date());
            user = userRepository.save(user);
        }

        String message = composeMessageToSign(user);
        return message;
    }

    public Boolean authenticate(String address, String signature) {
        if (address == null || signature == null) {
            return false;
        }

        UserEntity user = userRepository.getByAddress(address.toLowerCase());
        if (user == null) {
            return false;
        }

        String message = composeMessageToSign(user);
        try {
            String recoveredAddress = EthersUtils.verifyMessage(message, signature);
            return Keys.toChecksumAddress(address).equals(Keys.toChecksumAddress(recoveredAddress));
        } catch (Exception e) {
            log.error(e);
            return false;
        }

    }

    private int generateNonce() {
        int min = 0;
        int max = 1_000_000;
        Random random = new SecureRandom();
        int nonce = random.nextInt(max - min + 1) + min;
        return nonce;
    }

    private String composeMessageToSign(UserEntity user) {
        return String.format("Please sign this message for authentication: %d", user.getNonce());
    }

}
