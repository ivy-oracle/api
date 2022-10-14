package com.ivy.api.repository.entity;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import com.ivy.api.dto.DelegationDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Immutable
@Subselect("SELECT * FROM delegation")
public class DelegationEntity {
    @Id
    @Column(name = "delegation_id")
    private String ID;

    private String fromAddress;

    private String toAddress;

    private Double amount;

    private BigInteger updatedAtBlock;

    private BigInteger createdAtBlock;

    public DelegationDTO toDto() {
        return new DelegationDTO(
                this.fromAddress,
                this.toAddress,
                this.amount,
                this.updatedAtBlock,
                this.createdAtBlock);
    }
}
