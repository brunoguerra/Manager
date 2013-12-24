package com.ajurasz.repository;

import com.ajurasz.model.State;
import com.ajurasz.model.StateHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Arek Jurasz
 */
@Repository("stateRepo")
public interface StateRepository extends JpaRepository<State, Long> {
}
