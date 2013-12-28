package com.ajurasz.repository;

import com.ajurasz.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Arek Jurasz
 */
@Repository("orderRepo")
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(nativeQuery = true, value = "select * from orders order by id desc LIMIT 0,1")
    Order getLatestOrder();
}
