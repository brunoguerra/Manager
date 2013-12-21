package com.ajurasz.service;

import com.ajurasz.model.Customer;
import com.ajurasz.util.object.CityPostCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author ajurasz
 */
public interface ManagerService {
    Customer save(Customer customer);
    List<Customer> findAll();
    Page<Customer> findAll(Pageable pageable);
    Customer get(Long id);
    void delete(Customer customer);
    List<CityPostCode> findAllCitiesAndPostCodes(String cityName);
}
