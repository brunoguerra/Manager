package com.ajurasz.service;

import com.ajurasz.model.*;
import com.ajurasz.util.sql.mapper.CityPostCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author ajurasz
 */
public interface ManagerService {
    Customer saveCustomer(Customer customer);
    List<Customer> findAllCustomers();
    Page<Customer> findAllCustomers(Pageable pageable);
    List<Customer> findAllByCustomerLastName(String lastName);
    Customer getCustomer(Long id);
    void deleteCustomer(Customer customer);

    Item saveItem(Item item);
    Item getItem(Long id);
    List<Item> findAllItems();
    Map<String, String> findAllItemsMap();
    void deleteItem(Item item);

    State getState(Long id);
    State saveState(State state);

    StateHistory saveStateHistory(StateHistory stateHistory);
    List<StateHistory> findAllStateHistoryByStateIdDesc(Long id);
    Page<StateHistory> findAllStateHistoryByStateIdDesc(Long id, Pageable pageable);

    Reason saveReason(Reason reason);
    List<Reason> findAllReasons();

    String getNextDocNumnber();
    Page<Order> findAllOrders(Pageable pageable);
    Order saveOrder(Order order);
    Order update(Order order);
    Order getOrder(Long id);
    void deleteOrder(Order order);

    List<CityPostCode> findAllCitiesAndPostCodes(String cityName);


}
