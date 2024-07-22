package com.finalProject.SoftUniProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExerciseController {

    @GetMapping("/exercise-add")
    public ModelAndView index(){

        return new ModelAndView("exercise-add");
    }
}
