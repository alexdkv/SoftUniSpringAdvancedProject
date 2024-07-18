package com.finalProject.SoftUniProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/home")
    public ModelAndView index(){

        return new ModelAndView("home");
    }
    @GetMapping("/register")
    public ModelAndView register(){

        return new ModelAndView("register");
    }
    @GetMapping("/login")
    public ModelAndView login(){

        return new ModelAndView("login");
    }
}
