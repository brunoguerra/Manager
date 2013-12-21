package com.ajurasz.controller;

import com.ajurasz.model.Customer;
import com.ajurasz.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author Arek Jurasz
 */
@RequestMapping(value = "/customer")
@Controller
public class CustomerController {

    @Autowired
    private ManagerService managerService;

//    @InitBinder
//    public void setAllowedFields(WebDataBinder dataBinder) {
//        dataBinder.setDisallowedFields("id");
//    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String initCreationForm(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "customer/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processCreationForm(@Valid @ModelAttribute Customer customer, BindingResult result, RedirectAttributes redirectAttributes){
        if(result.hasErrors()) {
            return "customer/add";
        }
        managerService.save(customer);
        redirectAttributes.addFlashAttribute("customerAdded", true);
        return "redirect:/customer/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String initCustomerList(Model model) {
        model.addAttribute("customers", managerService.findAll());
        return "customer/list";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String initCustomerEdit(@PathVariable Long id, Model model) {
        model.addAttribute("customer", managerService.get(id));
        return "customer/edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String processCustomerEdit(@Valid @ModelAttribute Customer customer, BindingResult result, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "/customer/edit/" + customer.getId();
        }
        managerService.save(customer);
        redirectAttributes.addFlashAttribute("customerUpdated", true);
        return "redirect:/customer/list";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String processCustomerDelete(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        Customer customer = managerService.get(id);
        managerService.delete(customer);
        redirectAttributes.addFlashAttribute("customerDeleted", true);
        return "redirect:/customer/list";
    }
}
