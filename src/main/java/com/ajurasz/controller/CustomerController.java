package com.ajurasz.controller;

import com.ajurasz.model.Customer;
import com.ajurasz.service.ManagerService;
import com.ajurasz.util.helper.JsonHelper;
import com.ajurasz.util.object.CityPostCode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ajurasz
 */
@RequestMapping(value = "/customer")
@Controller
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

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
    public String initCustomerList(Model model, Pageable pageable) {
        Page<Customer> customerPage = managerService.findAll(pageable);
        model.addAttribute("customersPage", customerPage);
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
            return "customer/edit";
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

    @RequestMapping(value = "/edit/getCities", method = RequestMethod.GET)
    @ResponseBody
    public List<CityPostCode> processCitiesSearch(@RequestParam("term") String cityName) throws JSONException {
        List<CityPostCode> list =  managerService.findAllCitiesAndPostCodes(cityName);
        return list;
    }
}
