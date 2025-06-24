package com.finalProject.SoftUniProject.web;

import com.finalProject.SoftUniProject.model.entity.User;
import com.finalProject.SoftUniProject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {
    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("home");
        List<User> randomCoaches = userService.getRandomCoaches();
        modelAndView.addObject("randomCoaches", randomCoaches);
        return modelAndView;
    }

}
