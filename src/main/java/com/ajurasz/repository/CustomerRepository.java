package com.ajurasz.repository;

import com.ajurasz.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("customerRepo")
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
