package com.ajurasz.repository;

import com.ajurasz.model.Company;
import com.ajurasz.model.Reason;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Arek Jurasz
 */
public interface ReasonRepository extends JpaRepository<Reason, Long> {
    List<Reason> findAllByCompany(Company company);
}
