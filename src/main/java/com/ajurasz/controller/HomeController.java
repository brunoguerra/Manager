package com.ajurasz.controller;

import com.ajurasz.model.*;
import com.ajurasz.service.ManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ajurasz
 */
@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
    private ManagerService managerService;

    @Autowired
    public HomeController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "home/home";
    }

    @RequestMapping(value = "/")
    public String homePage() {
        return "home/home";
    }
}
