package com.ajurasz.controller;

import com.ajurasz.model.*;
import com.ajurasz.service.ManagerService;
import com.ajurasz.util.forms.InvoiceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
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
        invoiceForm.getOrder().setInvoiceNumber(managerService.getNextInvoiceNumnber());

        model.addAttribute("invoiceForm", invoiceForm);
        model.addAttribute("payments", InvoiceForm.Payment.values());
        return "invoice/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processCreateForm(@ModelAttribute InvoiceForm invoiceForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "invocie/add";
        }
        managerService.saveInvoiceForm(invoiceForm);
        redirectAttributes.addFlashAttribute("invoiceAdded", true);
        return "redirect:/invoice/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String initOrderList(Model model, Pageable pageable) {
        Page<Order> orderPage = managerService.findAllInvoices(pageable);
        model.addAttribute("orderPage", orderPage);
        return "invoice/list";
    }

    @RequestMapping(value = "/show-vat/{id}", method = RequestMethod.GET)
    public String processInvoiceShow(@PathVariable Long id) {
        Order order = managerService.getOrder(id);
        Company company = managerService.getCompany();
        return "redirect:/invoices/" + company.getId() + File.separator + order.getDocumentInvoiceName() + ".pdf";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String processOrderDelete(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        Order order = managerService.getOrder(id);
        managerService.deleteOrder(order);
        redirectAttributes.addFlashAttribute("invoiceDeleted", true);
        return "redirect:/invoice/list";
    }

    @RequestMapping(value = "/calculateInvoice", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> procesInvoiceCalculation(@RequestParam("id") Long id, BigDecimal quantity) {
        return managerService.calculateInvoice(id, quantity);
    }
}
