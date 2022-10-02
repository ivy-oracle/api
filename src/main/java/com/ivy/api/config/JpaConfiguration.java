package com.ivy.api.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.ivy.api.repository")
@EntityScan("com.ivy.api.repository.entity")
public class JpaConfiguration {

}
