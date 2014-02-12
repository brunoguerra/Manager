package com.ajurasz.controller;

import com.ajurasz.model.*;
import com.ajurasz.service.ManagerService;
import com.ajurasz.util.validator.OrderDetailsValidator;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
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
    private static final int MAX_ITEMS = 5;
    private ManagerService managerService;


    @ModelAttribute("items")
    public Map<String, String> populateWithItems() {
        LOGGER.debug("populate items");
        return managerService.findAllItemsMap();
    }

    @ModelAttribute("reasons")
    public List<Reason> populateWithReasons() {
        LOGGER.debug("populate reasons");
        return managerService.findAllReasons();
    }

    @Autowired
    public OrderController(ManagerService managerService) {
        this.managerService = managerService;
    }

    /***********************************/
    /********  ADD ORDERS  ************/
    /***********************************/
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String initOrderForm(Model model, HttpServletRequest request) {
        LOGGER.debug("order add init method called");
        Order order = new Order();
        order.getOrderDetails().add(new OrderDetails());
        order.setDocNumber(managerService.getNextDocNumnber());
        HttpSession session = request.getSession(false);
        if(session != null) {
            Customer customer = (Customer) session.getAttribute("customer");
            if(customer != null) {
                LOGGER.debug("customer found in session-{}", customer);
                order.setCustomer(customer);
                CustomerRegular cr = (CustomerRegular) customer;
                model.addAttribute("customerDetails", cr.getLastName() + " " + cr.getFirstName() + ", " + cr.getAddress().getPostCode() + " " +
                        cr.getAddress().getCity() + " " + cr.getAddress().getStreet() + " " + cr.getAddress().getNumber());
            }
        }
        model.addAttribute("order", order);
        return "order/add";
    }

    @RequestMapping(value = "/add", params = {"addRow"})
    public String addRow(@Validated({Order.Document.class}) @ModelAttribute Order order, BindingResult result, Model model,
                         @RequestParam(required = false, value = "customer-details") String custDetails,
                         @RequestParam(required = false, value = "customer.id") String custId) {
        //validate items and reason in custom way
        OrderDetailsValidator orderDetailsValidator = new OrderDetailsValidator();
        orderDetailsValidator.validate(order.getOrderDetails(), result);

        if(order.getOrderDetails().size() >= MAX_ITEMS) {
            LOGGER.debug("to many items in the order");
            model.addAttribute("orderToBig", true);
            return "order/add";
        }
        LOGGER.debug("adding new row to order");
        order.getOrderDetails().add(new OrderDetails());
        model.addAttribute("customer_details", custDetails);
        model.addAttribute("customer_id", custId);
        return "order/add";
    }

    @RequestMapping(value = "/add", params = {"removeRow"})
    public String removeRow(@Validated({Order.Document.class}) @ModelAttribute Order order, BindingResult result, Model model,
                         @RequestParam(value = "removeRow") Long rowId,
                         @RequestParam(required = false, value = "customer-details") String custDetails,
                         @RequestParam(required = false, value = "customer.id") String custId) {
        LOGGER.debug("deleting one row from order");
        order.getOrderDetails().remove(rowId.intValue());
        OrderDetailsValidator orderDetailsValidator = new OrderDetailsValidator();
        orderDetailsValidator.validate(order.getOrderDetails(), result);
        model.addAttribute("customer_details", custDetails);
        model.addAttribute("customer_id", custId);
        return "order/add";
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processOrderForm(@Validated({Order.Document.class}) @ModelAttribute Order order, BindingResult result,
                                   HttpServletRequest request, RedirectAttributes redirectAttributes) {
        OrderDetailsValidator orderDetailsValidator = new OrderDetailsValidator();
        orderDetailsValidator.validate(order.getOrderDetails(), result);
        if(result.hasErrors()) {
            LOGGER.debug("errors in order add form");
            return "order/add";
        }
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.removeAttribute("customer");
        }
        redirectAttributes.addFlashAttribute("orderAdded", true);
        managerService.saveOrder(order);
        return "redirect:/order/list";
    }

    /***********************************/
    /********  ADD ORDERS  ************/
    /***********************************/
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String initEditForm(@PathVariable Long id, Model model) {
        LOGGER.debug("order edit form called");
        Order order = managerService.getOrder(id);
        model.addAttribute("order", order);
        return "order/edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String processEditForm(@Valid @ModelAttribute Order order, BindingResult result,
                                  RedirectAttributes redirectAttributes) {
        LOGGER.debug("order edit form called-{}", order);
        if(result.hasErrors()) {
            LOGGER.debug("order edit form has got errors");
            return "order/edit";
        }
        managerService.update(order);
        redirectAttributes.addFlashAttribute("orderEdited", true);
        return "redirect:/order/list";
    }

    /***********************************/
    /********  LIST ORDERS  ************/
    /***********************************/
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String initOrderList(Model model) {
        LOGGER.debug("order list method called");
        List<Order> orderPage = managerService.findAllOrders();
        model.addAttribute("orders", orderPage);
        return "order/list";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String processOrderDelete(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        LOGGER.debug("order delete method called for id-{}", id);
        Order order = managerService.getOrder(id);
        managerService.deleteOrder(order);
        redirectAttributes.addFlashAttribute("orderDeleted", true);
        return "redirect:/order/list";
    }

    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public String processOrderShow(@PathVariable Long id) {
        Order order = managerService.getOrder(id);
        Company company = managerService.getCompany();
        return "redirect:/documents/" + company.getId() + File.separator + order.getDocumentName() + ".pdf";
    }
}
