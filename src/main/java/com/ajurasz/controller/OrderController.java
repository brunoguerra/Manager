package com.ajurasz.controller;

import com.ajurasz.model.Customer;
import com.ajurasz.model.Order;
import com.ajurasz.model.OrderDetails;
import com.ajurasz.model.Reason;
import com.ajurasz.service.ManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Arek Jurasz
 */
@Controller
@RequestMapping(value = "order")
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    private ManagerService managerService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

    @ModelAttribute("items")
    public Map<String, String> populateWithItems() {
        return managerService.findAllItemsMap();
    }

    @ModelAttribute("reasons")
    public List<Reason> populateWithReasons() {
        return managerService.findAllReasons();
    }

    @Autowired
    public OrderController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String initOrderForm(Model model, HttpServletRequest request) {
        Order order = new Order();
        //set init values
        order.setDocNumber(managerService.getNextDocNumnber());
        HttpSession session = request.getSession(false);
        if(session != null) {
            Customer customer = (Customer) session.getAttribute("customer");
            if(customer != null) {
                order.setCustomer(customer);
            }
        }
        model.addAttribute("order", order);
        return "order/add";
    }

    //todo: add validation
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processOrderForm(@Valid @ModelAttribute Order order, BindingResult result, HttpServletRequest request) {
        if(result.hasErrors()) {
            return "order/add";
        }
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.removeAttribute("customer");
        }
        managerService.saveOrder(order);
        return "home/home";
    }
}
