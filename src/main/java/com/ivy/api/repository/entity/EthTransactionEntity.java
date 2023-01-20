package com.ivy.api.repository.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "eth_transaction")
public class EthTransactionEntity {
    @Id
    @NotNull
    @Column(name = "transaction_hash")
    private String transactionHash;

    @NotNull
    @Column(name = "from_address")
    private String fromAddress;

    @NotNull
    @Column(name = "to_address")
    private String toAddress;

    @NotNull
    @Column(name = "value")
    private BigInteger value;

    @NotNull
    @Column(name = "gas")
    private BigInteger gas;

    @NotNull
    @Column(name = "gas_price")
    private BigInteger gasPrice;

    @NotNull
    @Column(name = "nonce")
    private BigInteger nonce;

    @NotNull
    @Column(name = "transactionIndex")
    private BigInteger transactionIndex;

    @ManyToOne
    @JoinColumn(name = "block_number", referencedColumnName = "block_number")
    private EthBlockEntity block;

    public BigInteger getBlockNumber() {
        return block.getBlockNumber();
    };

    public String getBlockHash() {
        return block.getBlockHash();
    };

    public Date getTimestamp() {
        return block.getTimestamp();
    };
}
