package com.web.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.ecommerce.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
