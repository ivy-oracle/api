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
@Table(name = "delegation_event")
public class DelegationEventEntity {
    @Id
    @Column(name = "delegation_event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @NotNull
    @Column(name = "from_address")
    private String from;

    @NotNull
    @Column(name = "to_address")
    private String to;

    @NotNull
    @Column(name = "prior_vote_power", columnDefinition = "numeric")
    private BigInteger priorVotePower;

    @NotNull
    @Column(name = "new_vote_power", columnDefinition = "numeric")
    private BigInteger newVotePower;

    @NotNull
    @Column(name = "block_number", columnDefinition = "numeric")
    private BigInteger blockNumber;

    @NotNull
    @Column(name = "transaction_index", columnDefinition = "numeric")
    private BigInteger transactionIndex;

    @NotNull
    @Column(name = "transaction_hash")
    private String transactionHash;

    @NotNull
    @Column(name = "log_index", columnDefinition = "numeric")
    private BigInteger logIndex;

}
