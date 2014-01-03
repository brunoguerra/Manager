package com.ajurasz.controller;

import com.ajurasz.model.Company;
import com.ajurasz.model.Order;
import com.ajurasz.model.Report;
import com.ajurasz.service.ManagerService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRProperties;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Arek Jurasz
 */
@Controller
@RequestMapping(value = "/report")
public class ReportController {

    @Autowired
    private ManagerService managerService;


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String initCreateForm(Model model) {
        Report report = new Report();
        model.addAttribute("report", report);
        return "report/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processCreateForm(@Valid @ModelAttribute Report report, BindingResult result, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "report/add";
        }
        managerService.saveReport(report);
        redirectAttributes.addFlashAttribute("reportAdded", true);
        return "redirect:/report/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String initReportList(Model model, Pageable pageable) {
        Page<Report> reportPage = managerService.findAllReports(pageable);
        model.addAttribute("reportPage", reportPage);
        return "report/list";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String processOrderDelete(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        Report report = managerService.getReport(id);
        managerService.deleteReport(report);
        redirectAttributes.addFlashAttribute("reportDeleted", true);
        return "redirect:/report/list";
    }

    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public String processOrderShow(@PathVariable Long id) {
        Report report = managerService.getReport(id);
        Company company = managerService.getCompany();
        return "redirect:/reports/" + company.getId() + File.separator + report.getReportName() + ".pdf";
    }

}
