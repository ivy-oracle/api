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
@Table(name = "price_revealed_event")
public class PriceRevealedEventEntity {
    @Id
    @Column(name = "price_revealed_event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @NotNull
    private String symbol;

    @NotNull
    private String voter;

    @NotNull
    @Column(columnDefinition = "numeric")
    private BigInteger epochId;

    @NotNull
    @Column(columnDefinition = "numeric")
    private BigInteger price;

    @NotNull
    @Column(columnDefinition = "numeric")
    private BigInteger random;

    @NotNull
    private BigInteger timestamp;

    @NotNull
    @Column(columnDefinition = "numeric")
    private BigInteger votePowerNat;

    @NotNull
    @Column(columnDefinition = "numeric")
    private BigInteger votePowerAsset;
}
