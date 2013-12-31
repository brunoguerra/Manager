package com.ajurasz.controller;

import com.ajurasz.model.*;
import com.ajurasz.service.ManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Company company = (Company)auth.getPrincipal();

        HttpSession session = request.getSession();
        session.setAttribute("company", company);

        return "home/home";
    }

    @RequestMapping(value = "/test")
    public String test(HttpServletRequest request) {

        //Create reason
        Reason reason = new Reason();
        reason.setDescription("Na gospodarstwo domowe");
        reason.setExcise(true);
        managerService.saveReason(reason);

        //Create items , states and history_states
        Item flot = new Item("Węgiel - Flot", 911, 23.8, new BigDecimal("500.00"), new BigDecimal("500.00"), new BigDecimal("500.00"));
        Item mul = new Item("Węgiel - Muł", 911, 23.8, new BigDecimal("510.00"), new BigDecimal("510.00"), new BigDecimal("510.00"));

        State flotState = new State();
        flotState.setCurrentState(new BigDecimal("1000.00"));
        flot.setState(flotState);

        State mulState = new State();
        mulState.setCurrentState(new BigDecimal("1000.00"));
        mul.setState(mulState);

        managerService.saveItem(flot);
        managerService.saveItem(mul);

        //Create customers and addresses
        Customer arek = new Customer();
        arek.setFirstName("Arkadiusz");
        arek.setLastName("Jurasz");
        arek.setEmail("arkadiusz.jurasz@gmail.com");
        arek.setPhoneNumber("338620506");
        arek.setPesel("123456789");

        Address address = new Address();
        address.setCity("Żywiec");
        address.setStreet("Długa");
        address.setNumber("12A");
        address.setPostCode("34-300");
        address.setCustomer(arek);
        arek.setAddress(address);

        Customer darek = new Customer();
        darek.setFirstName("Darek");
        darek.setLastName("Jurasz");
        darek.setEmail("darek.jurasz@gmail.com");
        darek.setPhoneNumber("338620506");
        darek.setPesel("123456789");

        Address address1 = new Address();
        address1.setCity("Żywiec");
        address1.setStreet("Długa");
        address1.setNumber("12B");
        address1.setPostCode("34-300");
        address1.setCustomer(darek);
        darek.setAddress(address1);

        Customer marek = new Customer();
        marek.setFirstName("Marek");
        marek.setLastName("Kowalski");
        marek.setEmail("marek.kowalski@gmail.com");
        marek.setPhoneNumber("338620506");
        marek.setPesel("123456789");

        Address address2 = new Address();
        address2.setCity("Żywiec");
        address2.setStreet("Długa");
        address2.setNumber("12C");
        address2.setPostCode("34-300");
        address2.setCustomer(marek);
        marek.setAddress(address2);

        managerService.saveCustomer(arek);
        managerService.saveCustomer(darek);
        managerService.saveCustomer(marek);


        //create orders , order details
//        Order order = new Order();
//        order.setDocNumber("1/01/2014");
//        order.setDate(new Date());
//        order.setCustomer(managerService.getCustomer(1L));
//
//        OrderDetails orderDetails = new OrderDetails();
//        orderDetails.setOrder(order);
//        orderDetails.setPriceExcise(new BigDecimal("500.00"));
//        orderDetails.setPriceNet(new BigDecimal("500.00"));
//        orderDetails.setPriceGross(new BigDecimal("500.00"));
//        orderDetails.setQuantity(new BigDecimal("1.56"));
//        orderDetails.setItem(managerService.getItem(1L));
//
//        OrderDetails orderDetails1 = new OrderDetails();
//        orderDetails1.setOrder(order);
//        orderDetails1.setPriceExcise(new BigDecimal("500.00"));
//        orderDetails1.setPriceNet(new BigDecimal("500.00"));
//        orderDetails1.setPriceGross(new BigDecimal("500.00"));
//        orderDetails1.setQuantity(new BigDecimal("2.56"));
//        orderDetails1.setItem(managerService.getItem(2L));
//
//        OrderDetails orderDetails2 = new OrderDetails();
//        orderDetails2.setOrder(order);
//        orderDetails2.setPriceExcise(new BigDecimal("500.00"));
//        orderDetails2.setPriceNet(new BigDecimal("500.00"));
//        orderDetails2.setPriceGross(new BigDecimal("500.00"));
//        orderDetails2.setQuantity(new BigDecimal("3.56"));
//        orderDetails2.setItem(managerService.getItem(1L));
//
//        ArrayList<OrderDetails> orders = new ArrayList<OrderDetails>();
//        orders.add(orderDetails);
//        orders.add(orderDetails1);
//        orders.add(orderDetails2);
//
//        order.setOrderDetails(orders);
//
//        managerService.saveOrder(order);
//
//        managerService.deleteOrder(order);

        return "home/home";
    }
}
