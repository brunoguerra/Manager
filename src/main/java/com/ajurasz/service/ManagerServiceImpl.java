package com.ajurasz.service;

import com.ajurasz.model.Customer;
import com.ajurasz.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Arek Jurasz
 */
@Service("managerService")
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private CustomerRepo customerRepo;

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
    public Customer get(Long id) {
        Customer c = customerRepo.findOne(id);
        return c;
    }

    @Override
    public void delete(Customer customer) {
        customerRepo.delete(customer);
    }
}
