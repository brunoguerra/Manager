package com.ajurasz.service;

import com.ajurasz.model.Customer;

import java.util.List;

/**
 * @author Arek Jurasz
 */
public interface ManagerService {
    Customer save(Customer customer);
    List<Customer> findAll();
    Customer get(Long id);
    void delete(Customer customer);
}
