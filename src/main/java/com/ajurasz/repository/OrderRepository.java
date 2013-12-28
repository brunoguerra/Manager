package com.ajurasz.repository;

import com.ajurasz.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Arek Jurasz
 */
@Repository("orderRepo")
public interface OrderRepository extends JpaRepository<Order, Long> {
}
