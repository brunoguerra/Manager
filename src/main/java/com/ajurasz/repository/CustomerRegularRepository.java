package com.ajurasz.repository;

import com.ajurasz.model.Company;
import com.ajurasz.model.Customer;
import com.ajurasz.model.CustomerRegular;
import com.ajurasz.util.sql.mapper.CityPostCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("customerRepo")
public interface CustomerRegularRepository extends JpaRepository<CustomerRegular, Long> {

    @Query("select new com.ajurasz.util.sql.mapper.CityPostCode( address.city, address.postCode) from  Address address where address.city like %:cityName% group by city")
    List<CityPostCode> findAllCitiesAndPostCodes(@Param("cityName") String cityName);

    @Query("select customer from CustomerRegular customer where customer.lastName like %:lastName% and customer.company=:company")
    List<CustomerRegular> findAllByCustomerLastNameAndCompany(@Param("lastName") String lastName, @Param("company") Company company);

    List<CustomerRegular> findAllByCompany(Company company, Sort id);

    Page<CustomerRegular> findAllByCompany(Company company, Pageable pageable);

    CustomerRegular findCustomerByIdAndCompany(Long id, Company company);
}
