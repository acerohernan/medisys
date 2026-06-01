package com.medisys.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String admin(Authentication authentication, Model model) {
        model.addAttribute("username", authentication.getName());
        return "admin";
    }
}
