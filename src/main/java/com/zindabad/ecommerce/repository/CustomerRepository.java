package com.zindabad.ecommerce.repository;

import com.zindabad.ecommerce.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Optional<Customer> findByUsername(String username);
    Customer findCustomerByUsername(String username);
    boolean existsByUsername(String username);
}
