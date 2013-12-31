package com.ajurasz.service;

import com.ajurasz.model.Company;
import com.ajurasz.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Arek Jurasz
 */
@Service
public class CompanyDetailsServiceImpl implements CompanyDetailsService {

    @Autowired
    private CompanyRepository companyRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Company company = companyRepo.findCompanyByUsername(username);
        return company;
    }
}
