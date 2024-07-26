package com.finalProject.SoftUniProject.controller;

import com.finalProject.SoftUniProject.model.dto.UserRegistrationDTO;
import com.finalProject.SoftUniProject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("registerDTO")
    public UserRegistrationDTO registerDTO() {
        return new UserRegistrationDTO();
    }

    @GetMapping("/register")
    public ModelAndView registerView(){

        return new ModelAndView("register");
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("registerDTO") @Valid UserRegistrationDTO registerDTO,
                           BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return "register";
        }
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword()) ){
            return "register";
        }

        userService.registerUser(registerDTO);
        return "redirect:/login";
    }


    @GetMapping("/login")
    public ModelAndView login(){

        return new ModelAndView("login");
    }

    @GetMapping("/login-error")
    public ModelAndView loginError(){

        return new ModelAndView("login-error");
    }






}
