package com.ajurasz.service;

import com.ajurasz.model.Customer;
import com.ajurasz.model.Item;
import com.ajurasz.model.State;
import com.ajurasz.model.StateHistory;
import com.ajurasz.util.sql.mapper.CityPostCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author ajurasz
 */
public interface ManagerService {
    Customer saveCustomer(Customer customer);
    List<Customer> findAllCustomers();
    Page<Customer> findAllCustomers(Pageable pageable);
    Customer getCustomer(Long id);
    void deleteCustomer(Customer customer);

    Item saveItem(Item item);
    Item getItem(Long id);
    List<Item> findAllItems();
    void deleteItem(Item item);

    State getState(Long id);
    State saveState(State state);

    StateHistory saveStateHistory(StateHistory stateHistory);
    List<StateHistory> findAllStateHistoryByStateIdDesc(Long id);
    Page<StateHistory> findAllStateHistoryByStateIdDesc(Long id, Pageable pageable);

    List<CityPostCode> findAllCitiesAndPostCodes(String cityName);
}
