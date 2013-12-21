package com.ajurasz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Arek Jurasz
 */
@Controller
public class HomeController {

    @RequestMapping(value = "/index")
    public String index() {
        return "home/home";
    }
}
