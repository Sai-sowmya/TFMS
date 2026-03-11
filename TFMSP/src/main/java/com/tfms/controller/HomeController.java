package com.tfms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("")
    public String showDashboard() {
        return "dashboard";
    }
    @GetMapping("/contact")
    public String showContactPage() {
        return "contact"; // This maps to contact.html in src/main/resources/templates
    }

    @GetMapping("/help")
    public String showHelpPage() {
        return "help"; // This maps to help.html in src/main/resources/templates
    }
}
