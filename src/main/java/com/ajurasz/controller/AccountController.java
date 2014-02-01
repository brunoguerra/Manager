package com.ajurasz.controller;

import com.ajurasz.model.Company;
import com.ajurasz.model.Role;
import com.ajurasz.service.ManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Arrays;

/**
 * @author Arek Jurasz
 */
@Controller
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    ManagerService managerService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String initRegisterForm(Model model) {
        Company company = new Company();
        model.addAttribute("company", company);

        log.info("Send new company object to registry page " + company);
        return "account/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegisterForm(@Valid @ModelAttribute("company") Company company, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            log.debug("Form validation error during registration " + result.getFieldErrors());
            return "account/register";
        }
        managerService.saveCompany(company);
        return "redirect:authentication/login";
    }
}
