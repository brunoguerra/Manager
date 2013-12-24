package com.ajurasz.repository;

import com.ajurasz.model.Customer;
import com.ajurasz.util.sql.mapper.CityPostCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("customerRepo")
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select new com.ajurasz.util.sql.mapper.CityPostCode( address.city, address.postCode) from  Address address where address.city like %:cityName% group by city")
    List<CityPostCode> findAllCitiesAndPostCodes(@Param("cityName") String cityName);
}
