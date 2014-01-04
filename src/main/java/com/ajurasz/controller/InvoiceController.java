package com.ajurasz.controller;

import com.ajurasz.model.Order;
import com.ajurasz.service.ManagerService;
import com.ajurasz.util.forms.InvoiceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @author Arek Jurasz
 */
@Controller
@RequestMapping(value = "/invoice")
public class InvoiceController {

    @Autowired
    private ManagerService managerService;

    @ModelAttribute("items")
    public Map<String, String> populateWithItems() {
        return managerService.findAllItemsMap();
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String initCreateForm(Model model) {
        InvoiceForm invoiceForm = new InvoiceForm();
        //invoiceForm.getOrder().setDocNumber(managerService.getNextDocNumnber());
        model.addAttribute("invoiceForm", invoiceForm);
        return "invoice/add";
    }
}
