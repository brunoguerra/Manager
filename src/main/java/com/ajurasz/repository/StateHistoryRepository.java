package com.ajurasz.repository;

import com.ajurasz.model.StateHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Arek Jurasz
 */
@Repository("stateHistoryRepo")
public interface StateHistoryRepository extends JpaRepository<StateHistory, Long> {
    List<StateHistory> findAllByStateIdOrderByDateDesc(Long state_id);
    Page<StateHistory> findAllByStateIdOrderByDateDesc(Long state_id, Pageable pageable);
}
