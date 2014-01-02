package com.ajurasz.repository;

import com.ajurasz.model.Company;
import com.ajurasz.model.Order;
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
@Repository("orderRepo")
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(nativeQuery = true, value = "select * from orders where orders.company_id=:id order by id desc LIMIT 0,1")
    Order getLatestOrder(@Param("id") Long id);

    Page<Order> findAllByCompany(Company company, Pageable pageable);
    List<Order> findAllByCompany(Company company);

    Order findByIdAndCompany(Long id, Company company);
}
