package com.ivy.api.repository.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "eth_user")
public class UserEntity {
    @Id
    @Column
    private String address;

    @Column
    private Integer nonce;

    @Column
    private Date nonceUpdatedAt;

    @Column
    private Date lastLoggedInAt;

    @Column
    private Date createdAt;
}
