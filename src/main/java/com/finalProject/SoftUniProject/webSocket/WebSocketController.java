package com.finalProject.SoftUniProject.webSocket;

import com.finalProject.SoftUniProject.model.dto.ExerciseAddBindingModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebSocketController {
    @GetMapping("/socketTest")
    public ModelAndView exerciseAddView(){

        ModelAndView modelAndView = new ModelAndView("socketTest");
        return modelAndView;
    }
}
