package com.finalProject.SoftUniProject.web;

import com.finalProject.SoftUniProject.model.dto.SupplementAddDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SupplementController {

    @GetMapping("/supplement-add")
    public ModelAndView supplementAddView(@ModelAttribute("supplementAddDTO") SupplementAddDTO supplementAddDTO){
        return new ModelAndView("supplement-add");
    }
}
