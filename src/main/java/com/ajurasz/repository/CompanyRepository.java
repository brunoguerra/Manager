package com.ajurasz.repository;

import com.ajurasz.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Arek Jurasz
 */
@Repository("companyRepo")
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findCompanyByUsername(String username);
}
