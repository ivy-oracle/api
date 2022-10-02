package com.ivy.api.repository.entity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "ftso_data_provider")
public class DelegationEventEntity {
    @Id
    @Column(name = "ftso_data_provider_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @NotNull
    @Column(name = "from_address")
    private String from;

    @NotNull
    @Column(name = "to_address")
    private String to;

    @NotNull
    @Column(name = "prior_vote_power")
    private String priorVotePower;

    @NotNull
    @Column(name = "new_vote_power")
    private String newVotePower;

    @NotNull
    @Column(name = "block_number")
    private BigInteger blockNumber;

    @NotNull
    @Column(name = "transaction_index")
    private int transactionIndex;

    @NotNull
    @Column(name = "transaction_hash")
    private String transactionHash;

    @NotNull
    @Column(name = "log_index")
    private int logIndex;

}
