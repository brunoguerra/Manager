package com.ajurasz.service;

import com.ajurasz.model.*;
import com.ajurasz.util.forms.InvoiceForm;
import com.ajurasz.util.sql.mapper.CityPostCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author ajurasz
 */
public interface ManagerService {
    //--CUSTOMER METHODS
    CustomerRegular saveCustomer(CustomerRegular customer);
    List<CustomerRegular> findAllCustomers();
    Page<CustomerRegular> findAllCustomers(Pageable pageable);
    List<CustomerRegular> findAllByCustomerLastName(String lastName);
    CustomerRegular getCustomer(Long id);
    void deleteCustomer(CustomerRegular customer);

    //--CUSTOMER VAT METHODS
    CustomerVat saveCustomerVat(CustomerVat customerVat);
    Page<CustomerVat> findAllCustomersVat(Pageable pageable);
    List<CustomerVat> findAllByCustomerVatName(String name);
    CustomerVat getCustomerVat(Long id);
    void deleteCustomerVat(CustomerVat customer);

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
    String getNextInvoiceNumnber();
    Page<Order> findAllOrders(Pageable pageable);
    Page<Order> findAllInvoices(Pageable pageable);
    List<Order> findAllOrders();
    Order saveOrder(Order order);
    Order update(Order order);
    Order getOrder(Long id);
    void deleteOrder(Order order);

    //--COMPANY METHODS
    Company saveCompany(Company company);

    //--REPORT METHODS
    Page<Report> findAllReports(Pageable pageable);
    Report saveReport(Report report);
    Report getReport(Long id);
    void deleteReport(Report report);

    //--OTHER METHODS
    List<CityPostCode> findAllCitiesAndPostCodes(String cityName);
    Company getCompany();

    Map<String, String> calculateInvoice(Long id, BigDecimal quantity);

    void saveInvoiceForm(InvoiceForm invoiceForm);
}
