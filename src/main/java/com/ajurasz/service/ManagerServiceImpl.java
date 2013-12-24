package com.ajurasz.service;

import com.ajurasz.model.Customer;
import com.ajurasz.model.Item;
import com.ajurasz.model.State;
import com.ajurasz.model.StateHistory;
import com.ajurasz.repository.CustomerRepository;
import com.ajurasz.repository.ItemRepository;
import com.ajurasz.repository.StateHistoryRepository;
import com.ajurasz.repository.StateRepository;
import com.ajurasz.util.sql.mapper.CityPostCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ajurasz
 */
@Service("managerService")
public class ManagerServiceImpl implements ManagerService {

    public final static BigDecimal VAT = new BigDecimal(23);

    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private ItemRepository itemRepo;
    @Autowired
    private StateHistoryRepository stateHistoryRepo;
    @Autowired
    private StateRepository stateRepo;

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
}
