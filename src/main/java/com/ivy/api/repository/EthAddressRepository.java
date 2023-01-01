package com.ivy.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ivy.api.repository.entity.EthAddressEntity;

@Repository
@Transactional
public interface EthAddressRepository extends JpaRepository<EthAddressEntity, String> {
    @Query(value = "SELECT * from eth_address WHERE eth_address IN :addresses", nativeQuery = true)
    public List<EthAddressEntity> getByAddresses(@Param("addresses") List<String> addresses);
}
