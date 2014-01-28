package com.ajurasz.controller;

import com.ajurasz.model.Company;
import com.ajurasz.model.Role;
import com.ajurasz.service.ManagerService;
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

    @Autowired
    ManagerService managerService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String initRegisterForm(Model model) {
        Company company = new Company();
        model.addAttribute("company", company);
        return "account/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegisterForm(@Valid @ModelAttribute("company") Company company, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "account/register";
        }
        if(!company.getPassword().equals(company.getConfirmPassword())) {
            model.addAttribute("passwordDontMatch", true);
            return "account/register";
        }
        //set user role
        Role role = managerService.getRoleByName("ROLE_USER");
        Role[] roles = {role};
        //disable account by default
        company.setEnabled(false);

        //set other properties
        company.setAccountNonExpired(true);
        company.setAccountNonLocked(true);
        company.setCredentialsNonExpired(true);

        //encrypt password
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        String encodedPassword = new BCryptPasswordEncoder().encode(company.getPassword());
        company.setPassword(encodedPassword);
        company.setConfirmPassword(encodedPassword);

        //save company
        managerService.saveCompany(company);

        //todo: need to fix this:
        company.setRoles(Arrays.asList(roles));
        managerService.saveCompany(company);
        redirectAttributes.addFlashAttribute("accountCreated", true);
        return "redirect:/login";
    }
}
