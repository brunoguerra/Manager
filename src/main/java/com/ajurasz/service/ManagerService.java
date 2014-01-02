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
    //--CUSTOMER METHODS
    Customer saveCustomer(Customer customer);
    List<Customer> findAllCustomers();
    Page<Customer> findAllCustomers(Pageable pageable);
    List<Customer> findAllByCustomerLastName(String lastName);
    Customer getCustomer(Long id);
    void deleteCustomer(Customer customer);

    //--ITEM METHODS
    Item saveItem(Item item);
    Item getItem(Long id);
    List<Item> findAllItems();
    Map<String, String> findAllItemsMap();
    void deleteItem(Item item);

    //--STATE METHODS
    State getState(Long id);
    State saveState(State state);

    //--STATE HISTORY METHODS
    StateHistory saveStateHistory(StateHistory stateHistory);
    List<StateHistory> findAllStateHistoryByStateIdDesc(Long id);
    Page<StateHistory> findAllStateHistoryByStateIdDesc(Long id, Pageable pageable);

    //--REASON METHODS
    Reason saveReason(Reason reason);
    List<Reason> findAllReasons();

    //--ORDER METHODS
    String getNextDocNumnber();
    Page<Order> findAllOrders(Pageable pageable);
    List<Order> findAllOrders();
    Order saveOrder(Order order);
    Order update(Order order);
    Order getOrder(Long id);
    void deleteOrder(Order order);

    //--COMPANY METHODS
    Company saveCompany(Company company);

    //--OTHER METHODS
    List<CityPostCode> findAllCitiesAndPostCodes(String cityName);


}
