package com.ajurasz.controller;

import com.ajurasz.model.CustomerRegular;
import com.ajurasz.model.CustomerVat;
import com.ajurasz.model.Order;
import com.ajurasz.model.Reason;
import com.ajurasz.service.ManagerService;
import com.ajurasz.util.forms.InvoiceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
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

    @ModelAttribute("reasons")
    public List<Reason> populateWithReasons() {
        return managerService.findAllReasons();
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String initCreateForm(Model model) {
        InvoiceForm invoiceForm = new InvoiceForm();
        invoiceForm.getOrder().setDocNumber(managerService.getNextDocNumnber());
        model.addAttribute("invoiceForm", invoiceForm);
        return "invoice/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processCreateForm(@ModelAttribute InvoiceForm invoiceForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "invocie/add";
        }
        managerService.saveInvoiceForm(invoiceForm);
        return "redirect:/invoice/list";
    }

    @RequestMapping(value = "/calculateInvoice", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> procesInvoiceCalculation(@RequestParam("id") Long id, BigDecimal quantity) {
        return managerService.calculateInvoice(id, quantity);
    }
}
