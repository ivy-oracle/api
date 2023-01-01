package com.ivy.api.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "eth_address")
public class EthAddressEntity {
    @Id
    @NotNull
    @Column(name = "eth_address")
    private String address;

    @NotNull
    @Column(name = "is_contract")
    private Boolean isContract;
}
