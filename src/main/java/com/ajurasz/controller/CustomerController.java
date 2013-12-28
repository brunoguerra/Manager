package com.ajurasz.controller;

import com.ajurasz.model.Customer;
import com.ajurasz.service.ManagerService;
import com.ajurasz.util.sql.mapper.CityPostCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * @author ajurasz
 */
@RequestMapping(value = "/customer")
@Controller
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    private ManagerService managerService;

    @Autowired
    public CustomerController(ManagerService managerService) {
        this.managerService = managerService;
        LOGGER.info("CustomerController called");
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String initCreationForm(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        LOGGER.info("Customer object send to html form - " + customer);
        return "customer/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processCreationForm(@Valid @ModelAttribute Customer customer, BindingResult result, RedirectAttributes redirectAttributes,
                                      HttpServletRequest request,
                                      @RequestParam(required = false) Boolean redirect,
                                      @RequestParam(required = false) String target){
        if(result.hasErrors()) {
            return "customer/add";
        }
        managerService.saveCustomer(customer);
        if(redirect != null && redirect == true) {
            HttpSession session = request.getSession();
            session.setAttribute("customer", customer);
            return "redirect:" + target;
        }
        redirectAttributes.addFlashAttribute("customerAdded", true);
        return "redirect:/customer/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String initCustomerList(Model model, Pageable pageable) {
        Page<Customer> customerPage = managerService.findAllCustomers(pageable);
        model.addAttribute("customersPage", customerPage);
        return "customer/list";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String initCustomerEdit(@PathVariable Long id, Model model) {
        model.addAttribute("customer", managerService.getCustomer(id));
        return "customer/edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String processCustomerEdit(@Valid @ModelAttribute Customer customer, BindingResult result, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "customer/edit";
        }
        managerService.saveCustomer(customer);
        redirectAttributes.addFlashAttribute("customerUpdated", true);
        return "redirect:/customer/list";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String processCustomerDelete(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        Customer customer = managerService.getCustomer(id);
        managerService.deleteCustomer(customer);
        redirectAttributes.addFlashAttribute("customerDeleted", true);
        return "redirect:/customer/list";
    }

    @RequestMapping(value = "/edit/getCities", method = RequestMethod.GET)
    @ResponseBody
    public List<CityPostCode> processCitiesSearch(@RequestParam("term") String cityName) {
        List<CityPostCode> list =  managerService.findAllCitiesAndPostCodes(cityName);
        return list;
    }

    //todo: this need to be very secure as it expose access to customer table
    @RequestMapping(value = "getCustomersForQuery", method = RequestMethod.GET)
    @ResponseBody
    public List<Customer> proccessCustomerSearchForQuery(@RequestParam("term") String query) {
        List<Customer> list = managerService.findAllByCustomerLastName(query);
        return list;
    }
}
