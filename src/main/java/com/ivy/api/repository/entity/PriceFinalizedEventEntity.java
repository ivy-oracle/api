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
@Table(name = "price_finalized_event")
public class PriceFinalizedEventEntity {
    @Id
    @Column(name = "price_finalized_event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @NotNull
    @Column(columnDefinition = "numeric")
    private BigInteger epochId;

    @NotNull
    private String symbol;

    @NotNull
    @Column(columnDefinition = "numeric")
    private BigInteger price;

    @NotNull
    private Boolean rewardedFtso;

    @NotNull
    @Column(columnDefinition = "numeric")
    private BigInteger lowRewardPrice;

    @NotNull
    @Column(columnDefinition = "numeric")
    private BigInteger highRewardPrice;

    @NotNull
    private BigInteger finalizationType;

    @NotNull
    private BigInteger timestamp;
}
