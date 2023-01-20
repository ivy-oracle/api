package com.ivy.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ivy.api.dto.request.AuthLoginResponse;
import com.ivy.api.dto.request.AuthRegisterRequestBody;
import com.ivy.api.repository.entity.UserEntity;
import com.ivy.api.service.AuthService;

@Controller
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("register")
    public ResponseEntity<UserEntity> register(@RequestBody AuthRegisterRequestBody body) {
        // TODO: Save additional user info
        UserEntity userEntity = authService.register(body.getAddress());
        return ResponseEntity.ok(userEntity);
    }

    @GetMapping("login")
    public ResponseEntity<AuthLoginResponse> getLoginNonce(@RequestParam("address") String address) {
        String message = authService.getLoginMessageToSign(address);
        return ResponseEntity.ok(new AuthLoginResponse(message));
    }

}
