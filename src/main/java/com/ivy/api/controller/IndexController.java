package com.ivy.api.controller;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    private final ConfigurableEnvironment configurableEnvironment;

    public IndexController(ConfigurableEnvironment configurableEnvironment) {
        this.configurableEnvironment = configurableEnvironment;
    }

    @GetMapping("/")
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("Ivy API");
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }

    @GetMapping("/profile")
    public ResponseEntity<String> defaultProfiles() {
        var activeProfiles = this.configurableEnvironment.getActiveProfiles();
        if (activeProfiles.length > 0) {
            return ResponseEntity.ok(activeProfiles[0]);
        }
        var defaultProfiles = this.configurableEnvironment.getDefaultProfiles();
        if (defaultProfiles.length > 0) {
            return ResponseEntity.ok(defaultProfiles[0]);
        }
        return ResponseEntity.ok("no profile set");
    }

}
