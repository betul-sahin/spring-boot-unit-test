package com.betulsahin.springbootunittest.repositories;

import com.betulsahin.springbootunittest.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByIdentificationNumber(String identificationNumber);
}
