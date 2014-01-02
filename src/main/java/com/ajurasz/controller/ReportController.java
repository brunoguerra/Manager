package com.ajurasz.controller;

import com.ajurasz.model.Company;
import com.ajurasz.model.Order;
import com.ajurasz.service.ManagerService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
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

    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public String getPdf(ModelAndView modelAndView, HttpSession session) throws JRException {
        Company company = (Company) session.getAttribute("company");
        Map<String, Object> mapParameters = new HashMap<String, Object>();
        List<Order> orders = managerService.findAllOrders();

        JRDataSource jrDataSource = new JRBeanCollectionDataSource(orders);

        mapParameters.put("datasource", jrDataSource);
        mapParameters.put("CompanyName", company.getFullName());
        mapParameters.put("ComapnyAddress", company.getAddress().getAddress());

        JasperDesign jDesign = JRXmlLoader.load(new FileSystemResource("ZestawienieNew.jrxml").getPath());
        JasperReport jReport = JasperCompileManager.compileReport(jDesign);
        JasperPrint jPrint = JasperFillManager.fillReport(jReport, mapParameters, jrDataSource);

        JasperExportManager.exportReportToPdfFile(jPrint, "C:\\test.pdf");
        modelAndView = new ModelAndView("jasperReportsPdfView", mapParameters);
        return "home/home";
    }
}
