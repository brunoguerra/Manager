package com.ajurasz.controller;

import com.ajurasz.model.Customer;
import com.ajurasz.model.CustomerRegular;
import com.ajurasz.model.CustomerVat;
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
        CustomerRegular customer = new CustomerRegular();
        model.addAttribute("customer", customer);
        LOGGER.info("Customer object send to html form - " + customer);
        return "customer/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processCreationForm(@Valid @ModelAttribute(value = "customer") CustomerRegular customer, BindingResult result, RedirectAttributes redirectAttributes,
                                      HttpServletRequest request,
                                      @RequestParam(required = false) Boolean redirect,
                                      @RequestParam(required = false) String target){
        if(result.hasErrors()) {
            return "customer/add";
        }
        managerService.saveCustomer((CustomerRegular)customer);
        if(redirect != null && redirect == true) {
            HttpSession session = request.getSession();
            session.setAttribute("customer", customer);
            return "redirect:" + target;
        }
        redirectAttributes.addFlashAttribute("customerAdded", true);
        LOGGER.info("Customer object saved - " + customer);
        return "redirect:/customer/list";
    }

    @RequestMapping(value = "/add-vat", method = RequestMethod.GET)
    public String initCreationVatForm(Model model) {
        CustomerVat customer = new CustomerVat();
        model.addAttribute("customer", customer);
        LOGGER.info("CustomerVat object send to html form - " + customer);
        return "customer/addVat";
    }

    @RequestMapping(value = "/add-vat", method = RequestMethod.POST)
    public String processCreationVatForm(@Valid @ModelAttribute(value = "customer") CustomerVat customer, BindingResult result, RedirectAttributes redirectAttributes,
                                      HttpServletRequest request,
                                      @RequestParam(required = false) Boolean redirect,
                                      @RequestParam(required = false) String target){
        if(result.hasErrors()) {
            return "customer/addVat";
        }
        managerService.saveCustomerVat((CustomerVat)customer);
        if(redirect != null && redirect == true) {
            HttpSession session = request.getSession();
            session.setAttribute("customer", customer);
            return "redirect:" + target;
        }
        redirectAttributes.addFlashAttribute("customerVatAdded", true);
        LOGGER.info("CustomerVat object saved - " + customer);
        return "redirect:/customer/list-vat";
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String initCustomerList(Model model, Pageable pageable) {
        Page<CustomerRegular> customerPage = managerService.findAllCustomers(pageable);
        model.addAttribute("customersPage", customerPage);
        LOGGER.info("Customer list requested");
        return "customer/list";
    }

    @RequestMapping(value = "/list-vat", method = RequestMethod.GET)
    public String initCustomerVatList(Model model, Pageable pageable) {
        Page<CustomerVat> customerPage = managerService.findAllCustomersVat(pageable);
        model.addAttribute("customersPage", customerPage);
        LOGGER.info("CustomerVat list requested");
        return "customer/listVat";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String initCustomerEdit(@PathVariable Long id, Model model) {
        CustomerRegular customer =  managerService.getCustomer(id);
        model.addAttribute("customer",customer);
        LOGGER.info("Customer edit form - " + customer);
        return "customer/edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String processCustomerEdit(@Valid @ModelAttribute("customer") CustomerRegular customer, BindingResult result, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "customer/edit";
        }
        managerService.saveCustomer(customer);
        redirectAttributes.addFlashAttribute("customerUpdated", true);
        LOGGER.info("Customer edit form saved - " + customer);
        return "redirect:/customer/list";
    }

    @RequestMapping(value = "/edit-vat/{id}", method = RequestMethod.GET)
    public String initCustomerVatEdit(@PathVariable Long id, Model model) {
        model.addAttribute("customer", managerService.getCustomerVat(id));
        return "customer/editVat";
    }

    @RequestMapping(value = "/edit-vat/{id}", method = RequestMethod.POST)
    public String processCustomerVatEdit(@Valid @ModelAttribute("customer") CustomerVat customer, BindingResult result, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "customer/edit-vat";
        }
        managerService.saveCustomerVat(customer);
        redirectAttributes.addFlashAttribute("customerUpdated", true);
        return "redirect:/customer/list-vat";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String processCustomerDelete(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        CustomerRegular customer = managerService.getCustomer(id);
        managerService.deleteCustomer(customer);
        redirectAttributes.addFlashAttribute("customerDeleted", true);
        return "redirect:/customer/list";
    }

    @RequestMapping(value = "/delete-vat", method = RequestMethod.POST)
    public String processCustomerVatDelete(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        CustomerVat customer = managerService.getCustomerVat(id);
        managerService.deleteCustomerVat(customer);
        redirectAttributes.addFlashAttribute("customerVatDeleted", true);
        return "redirect:/customer/list-vat";
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
    public List<CustomerRegular> proccessCustomerSearchForQuery(@RequestParam("term") String query) {
        List<CustomerRegular> list = managerService.findAllByCustomerLastName(query);
        return list;
    }

    //todo: this need to be very secure as it expose access to customer table
    @RequestMapping(value = "getCustomersVatForQuery", method = RequestMethod.GET)
    @ResponseBody
    public List<CustomerVat> proccessCustomerVatSearchForQuery(@RequestParam("term") String query) {
        List<CustomerVat> list = managerService.findAllByCustomerVatName(query);
        return list;
    }
}
