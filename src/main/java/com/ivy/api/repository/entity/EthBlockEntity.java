package com.ivy.api.repository.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.web3j.protocol.core.methods.response.EthBlock;

import com.ivy.api.util.CommonUtil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "eth_block")
public class EthBlockEntity {
    @Id
    @NotNull
    @Column(name = "block_number")
    private BigInteger blockNumber;

    @NotNull
    @Column(name = "block_hash")
    private String blockHash;

    @NotNull
    @Column(name = "block_timestamp")
    private Date timestamp;

    public static EthBlockEntity of(EthBlock ethBlock) {
        var ethBlockResult = ethBlock.getResult();
        return new EthBlockEntity(
                ethBlockResult.getNumber(),
                ethBlockResult.getHash(),
                CommonUtil.convertTimestampToDate(ethBlockResult.getTimestamp()));
    }
}
