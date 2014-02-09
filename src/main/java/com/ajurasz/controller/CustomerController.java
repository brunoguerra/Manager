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

    /***********************************/
    /********  ADD CUSTOMER  ***********/
    /***********************************/
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String initCreationForm(Model model) {
        LOGGER.debug("add customer init method called");
        CustomerRegular customer = new CustomerRegular();
        model.addAttribute("customer", customer);
        return "customer/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processCreationForm(@Valid @ModelAttribute(value = "customer") CustomerRegular customer, BindingResult result, RedirectAttributes redirectAttributes,
                                      HttpServletRequest request,
                                      @RequestParam(required = false) Boolean redirect,
                                      @RequestParam(required = false) String target){
        if(result.hasErrors()) {
            LOGGER.debug("error in customer add form");
            return "customer/add";
        }
        managerService.saveCustomer((CustomerRegular)customer);
        if(redirect != null && redirect == true) {
            HttpSession session = request.getSession();
            session.setAttribute("customer", customer);
            return "redirect:" + target;
        }
        redirectAttributes.addFlashAttribute("customerAdded", true);
        return "redirect:/customer/list";
    }


    @RequestMapping(value = "/add-vat", method = RequestMethod.GET)
    public String initCreationVatForm(Model model) {
        LOGGER.debug("add customer-vat init method called");
        CustomerVat customer = new CustomerVat();
        model.addAttribute("customer", customer);
        return "customer/addVat";
    }

    @RequestMapping(value = "/add-vat", method = RequestMethod.POST)
    public String processCreationVatForm(@Valid @ModelAttribute(value = "customer") CustomerVat customer, BindingResult result, RedirectAttributes redirectAttributes,
                                      HttpServletRequest request,
                                      @RequestParam(required = false) Boolean redirect,
                                      @RequestParam(required = false) String target){
        if(result.hasErrors()) {
            LOGGER.debug("error in customer-vat add form");
            return "customer/addVat";
        }
        managerService.saveCustomerVat((CustomerVat)customer);
        if(redirect != null && redirect == true) {
            HttpSession session = request.getSession();
            session.setAttribute("customer", customer);
            return "redirect:" + target;
        }
        redirectAttributes.addFlashAttribute("customerVatAdded", true);
        return "redirect:/customer/list-vat";
    }


    /***********************************/
    /********  LIST CUSTOMER  **********/
    /***********************************/
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String initCustomerList(Model model) {
        LOGGER.debug("customer list method called");
        List<CustomerRegular> customers = managerService.findAllCustomers();
        model.addAttribute("customers", customers);
        return "customer/list";
    }

    @RequestMapping(value = "/list-vat", method = RequestMethod.GET)
    public String initCustomerVatList(Model model) {
        LOGGER.debug("customer-vat list method called");
        List<CustomerVat> customers = managerService.findAllCustomersVat();
        model.addAttribute("customers", customers);
        return "customer/listVat";
    }


    /***********************************/
    /********  EDIT CUSTOMER  **********/
    /***********************************/
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String initCustomerEdit(@PathVariable Long id, Model model) {
        CustomerRegular customer =  managerService.getCustomer(id);
        model.addAttribute("customer",customer);
        LOGGER.info("customer edit form-{}", customer);
        return "customer/edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String processCustomerEdit(@Valid @ModelAttribute("customer") CustomerRegular customer, BindingResult result, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            LOGGER.debug("error in customer edit form");
            return "customer/edit";
        }
        managerService.saveCustomer(customer);
        redirectAttributes.addFlashAttribute("customerUpdated", true);
        return "redirect:/customer/list";
    }

    @RequestMapping(value = "/edit-vat/{id}", method = RequestMethod.GET)
    public String initCustomerVatEdit(@PathVariable Long id, Model model) {
        CustomerVat customer = managerService.getCustomerVat(id);
        model.addAttribute("customer", customer);
        LOGGER.info("customer-vat edit form-{}", customer);
        return "customer/editVat";
    }

    @RequestMapping(value = "/edit-vat/{id}", method = RequestMethod.POST)
    public String processCustomerVatEdit(@Valid @ModelAttribute("customer") CustomerVat customer, BindingResult result, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            LOGGER.debug("error in customer-vat edit form");
            return "customer/editVat";
        }
        managerService.saveCustomerVat(customer);
        redirectAttributes.addFlashAttribute("customerUpdated", true);
        return "redirect:/customer/list-vat";
    }


    /***********************************/
    /********  DELETE CUSTOMER  ********/
    /***********************************/
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

    @RequestMapping(value = "getCustomersForQuery", method = RequestMethod.GET)
    @ResponseBody
    public List<CustomerRegular> proccessCustomerSearchForQuery(@RequestParam("term") String query) {
        List<CustomerRegular> list = managerService.findAllByCustomerLastName(query);
        return list;
    }

    @RequestMapping(value = "getCustomersVatForQuery", method = RequestMethod.GET)
    @ResponseBody
    public List<CustomerVat> proccessCustomerVatSearchForQuery(@RequestParam("term") String query) {
        List<CustomerVat> list = managerService.findAllByCustomerVatName(query);
        return list;
    }
}
