package com.ajurasz.repository;

import com.ajurasz.model.Reason;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Arek Jurasz
 */
public interface ReasonRepository extends JpaRepository<Reason, Long> {
}
