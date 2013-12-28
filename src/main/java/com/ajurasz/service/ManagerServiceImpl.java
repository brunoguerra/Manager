package com.ajurasz.service;

import com.ajurasz.model.*;
import com.ajurasz.repository.*;
import com.ajurasz.util.sql.mapper.CityPostCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ajurasz
 */
@Service("managerService")
public class ManagerServiceImpl implements ManagerService {

    public final static BigDecimal VAT = new BigDecimal(23);

    private CustomerRepository customerRepo;
    private ItemRepository itemRepo;
    private StateHistoryRepository stateHistoryRepo;
    private StateRepository stateRepo;
    private OrderRepository orderRepo;
    private ReasonRepository reasonRepo;

    @Autowired
    public ManagerServiceImpl(CustomerRepository customerRepo, ItemRepository itemRepo,
                              StateHistoryRepository stateHistoryRepo,StateRepository stateRepo,
                              OrderRepository orderRepo, ReasonRepository reasonRepo) {
        this.customerRepo = customerRepo;
        this.itemRepo = itemRepo;
        this.stateHistoryRepo = stateHistoryRepo;
        this.stateRepo = stateRepo;
        this.orderRepo = orderRepo;
        this.reasonRepo = reasonRepo;
    }

    //CUSTOMERS
    @Override
    @Transactional
    public Customer saveCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findAllCustomers() {
        return customerRepo.findAll(new Sort(Sort.Direction.DESC, "id"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Customer> findAllCustomers(Pageable pageable) {
        return customerRepo.findAll(pageable);
    }

    @Override
    public List<Customer> findAllByCustomerLastName(String lastName) {
        return customerRepo.findAllByCustomerLastName(lastName);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getCustomer(Long id) {
        Customer c = customerRepo.findOne(id);
        return c;
    }

    @Override
    @Transactional
    public void deleteCustomer(Customer customer) {
        customerRepo.delete(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CityPostCode> findAllCitiesAndPostCodes(String cityName) {
        return customerRepo.findAllCitiesAndPostCodes(cityName);
    }

    //ITEMS


    //todo: Create logic for filling all fields
    @Override
    @Transactional
    public Item saveItem(Item item) {
        boolean isNew = false;
        if(item.getId() == null) {
            isNew = true;
        }
        //PriceNet = PriceGross - ( (PriceGross * 23)/100 )
        item.setPriceNet( item.getPriceGross().subtract( item.getPriceGross().multiply(VAT).divide(new BigDecimal(100)) ) );
        //PriceExcise = PriceGross
        item.setPriceExcise(item.getPriceGross().add(item.getPriceGross().multiply(new BigDecimal(2))));
        itemRepo.save(item);

        if(isNew) {
            StateHistory stateHistory = null;
            if(item.getState() != null) {
                stateHistory = new StateHistory();
                stateHistory.setState(item.getState());
                stateHistory.setValue(item.getState().getCurrentState());
                stateHistoryRepo.save(stateHistory);
            }
        }

        return item;
    }

    @Override
    @Transactional(readOnly = true)
    public Item getItem(Long id) {
        Item item = itemRepo.findOne(id);
        return item;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> findAllItems() {
        return itemRepo.findAll();
    }

    @Override
    public Map<String, String> findAllItemsMap() {
        Map<String, String> result = new HashMap<String, String>();
        List<Item> items = itemRepo.findAll();
        if(items != null && !items.isEmpty()) {
            for(Item item : items) {
                result.put(item.getId().toString(), item.getName());
            }
            return result;
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteItem(Item item) {
        itemRepo.delete(item);
    }

    @Override
    @Transactional
    public StateHistory saveStateHistory(StateHistory stateHistory) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = true)
    public List<StateHistory> findAllStateHistoryByStateIdDesc(Long id) {
        List<StateHistory> stateHistories = stateHistoryRepo.findAllByStateIdOrderByDateDesc(id);
        return stateHistories;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StateHistory> findAllStateHistoryByStateIdDesc(Long id, Pageable pageable) {
        return stateHistoryRepo.findAllByStateIdOrderByDateDesc(id, pageable);
    }

    @Override
    @Transactional
    public Reason saveReason(Reason reason) {
        return reasonRepo.save(reason);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reason> findAllReasons() {
        return reasonRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public String getNextDocNumnber() {
        String docNumber = null;
        //Get current time is expected format
        Calendar calendar = Calendar.getInstance();
        String currentMonth = new SimpleDateFormat("MM").format(calendar.getTime());
        String currentYear = new SimpleDateFormat("YYYY").format(calendar.getTime());

        Order order = orderRepo.getLatestOrder();

        //First entry in db
        if(order == null) {
            return "1/" + currentMonth + "/" + currentYear;
        }

        //docNumber exist but new month or year is present
        String[] latestDocNumber = order.getDocNumber().split("/");
        if(!latestDocNumber[1].equals(currentMonth) || !latestDocNumber[2].equals(currentYear)) {
            return "1/" + currentMonth + "/" + currentYear;
        }

        //just increment first number
        int id = Integer.parseInt(latestDocNumber[0]);
        id++;
        return id + "/" + currentMonth + "/" + currentYear;
    }

    @Override
    @Transactional(readOnly = true)
    public State getState(Long id) {
        return stateRepo.findOne(id);
    }

    @Override
    @Transactional
    public State saveState(State state) {
        stateRepo.save(state);
        StateHistory stateHistory = new StateHistory();
        stateHistory.setState(state);
        stateHistory.setValue(state.getLastValue());
        stateHistoryRepo.save(stateHistory);
        return state;
    }

    @Override
    @Transactional
    public Order saveOrder(Order order) {
        Customer customer = customerRepo.findOne(order.getCustomer().getId());
        List<OrderDetails> orderDetailses = order.getOrderDetails();
        List<OrderDetails> resultList = new ArrayList<OrderDetails>();
        for (OrderDetails orderDetails : orderDetailses) {
            if(orderDetails.getItem() == null || orderDetails.getReason() == null || (orderDetails.getQuantity() == new BigDecimal(0)) ) {
                continue;
            }
            Item item = itemRepo.findOne(orderDetails.getItem().getId());
            Reason reason = reasonRepo.findOne(orderDetails.getReason().getId());
            BigDecimal quantity = orderDetails.getQuantity().divide(new BigDecimal(1000));

            //set orderDetails
            orderDetails.setOrder(order);
            orderDetails.setItem(item);
            orderDetails.setReason(reason);

            //set prices
            orderDetails.setPriceGross( item.getPriceGross().multiply(quantity) );
            orderDetails.setPriceNet(item.getPriceNet().multiply(quantity));
            orderDetails.setPriceExcise(item.getPriceExcise().multiply(quantity));

            //set state history
            State state = item.getState();
            state.setCurrentState(orderDetails.getQuantity().multiply(new BigDecimal(-1)));
            stateRepo.save(state);

            StateHistory history = new StateHistory();
            history.setState(state);
            history.setValue(orderDetails.getQuantity().multiply(new BigDecimal(-1)));
            stateHistoryRepo.save(history);

            resultList.add(orderDetails);
        }


        //set order
        if (order.getDocNumber() == null)
            order.setDocNumber(getNextDocNumnber());
        order.setDate(new Date());
        order.setCustomer(customer);
        order.setOrderDetails(resultList);
        return orderRepo.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrder(Long id) {
        return orderRepo.findOne(id);
    }

    @Override
    @Transactional
    public void deleteOrder(Order order) {
        orderRepo.delete(order);
    }
}
