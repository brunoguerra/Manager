package com.ajurasz.controller;

import com.ajurasz.model.Item;
import com.ajurasz.model.State;
import com.ajurasz.model.StateHistory;
import com.ajurasz.service.ManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ajurasz
 */
@Controller
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    private ManagerService managerService;

    @Autowired
    public HomeController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @RequestMapping(value = "/index")
    public String index() {
//
//        Item item = new Item("Flot", 911, 23.8, new BigDecimal("500.00"), new BigDecimal("500.00"), new BigDecimal("500.00"));
//        State state = new State();
//        state.setCurrentState(new BigDecimal("1000.00"));
//        item.setState(state);
//
//        managerService.saveItem(item);
//
//        Item newItem = managerService.getItem(new Long(1));
//
//        StateHistory stateHistory = new StateHistory();
//        StateHistory stateHistory1 = new StateHistory();
//
//        stateHistory.setValue(new BigDecimal("-1000.00"));
//        stateHistory1.setValue(new BigDecimal("-2000.00"));
//
//        stateHistory.setState();

        //

//
//        Item newItem = managerService.getItem(new Long(1));
//        newItem.setState(state);
//
//        managerService.saveItem(newItem);
//
//        //
//
//        Item newerItem = managerService.getItem(new Long(1));
//        StateHistory stateHistory = new StateHistory();
//        StateHistory stateHistory1 = new StateHistory();
//
//        stateHistory.setValue(new BigDecimal("-1000.00"));
//        stateHistory1.setValue(new BigDecimal("-2000.00"));
//
//        List<StateHistory> list = new ArrayList<StateHistory>();
//        list.add(stateHistory); list.add(stateHistory1);
//
//        newerItem.getState().setStateHistories(list);

//        managerService.saveItem(newerItem);


        return "home/home";
    }
}
