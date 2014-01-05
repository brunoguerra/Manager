package com.ajurasz.repository;

import com.ajurasz.model.Company;
import com.ajurasz.model.CustomerVat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Arek Jurasz
 */
@Repository("customerVatRepo")
public interface CustomerVatRepository extends JpaRepository<CustomerVat, Long> {
    @Query("select customer from CustomerVat customer where customer.name like %:name% and customer.company=:company")
    List<CustomerVat> findAllByCustomerVatNameAndCompany(@Param("name") String name, @Param("company") Company company);

    Page<CustomerVat> findAllByCompany(Company company, Pageable pageable);
    CustomerVat findCustomerByIdAndCompany(Long id, Company company);
}
