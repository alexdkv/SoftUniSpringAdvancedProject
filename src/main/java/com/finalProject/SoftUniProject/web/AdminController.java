package com.finalProject.SoftUniProject.web;

import com.finalProject.SoftUniProject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {
    private final UserService userService;

    public AdminController(  UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/all-users")
    public ModelAndView viewAllUsers(){
        ModelAndView modelAndView = new ModelAndView("all-users");
        modelAndView.addObject("users", userService.getAllUsers());
        return modelAndView;
    }

    @PostMapping("/admin/role-change/{id}")
    public String changeUserRole(@PathVariable Long id) {
        userService.changeUserRole(id);
        return "redirect:/admin/all-users";
    }

    @PostMapping("/admin/promote/{id}")
    public String promoteToAdmin(@PathVariable Long id) {
        userService.promoteToAdmin(id);
        return "redirect:/admin/all-users";
    }
}
