package com.ajurasz.repository;

import com.ajurasz.model.Company;
import com.ajurasz.model.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author Arek Jurasz
 */
@Repository("reportRepo")
public interface ReportRepository extends JpaRepository<Report, Long> {
    Page<Report> findAllByCompany(Company company, Pageable pageable);
    Report findByIdAndCompany(Long id, Company company);
}
