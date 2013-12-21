package com.ajurasz.service;

import com.ajurasz.model.Customer;
import com.ajurasz.repository.CustomerRepository;
import com.ajurasz.util.object.CityPostCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ajurasz
 */
@Service("managerService")
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private CustomerRepository customerRepo;

    @Override
    @Transactional
    public Customer save(Customer customer) {
        return customerRepo.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findAll() {
        return customerRepo.findAll(new Sort(Sort.Direction.DESC, "id"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Customer> findAll(Pageable pageable) {
        return customerRepo.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer get(Long id) {
        Customer c = customerRepo.findOne(id);
        return c;
    }

    @Override
    public void delete(Customer customer) {
        customerRepo.delete(customer);
    }

    @Override
    public List<CityPostCode> findAllCitiesAndPostCodes(String cityName) {
        return customerRepo.findAllCitiesAndPostCodes(cityName);
    }
}
