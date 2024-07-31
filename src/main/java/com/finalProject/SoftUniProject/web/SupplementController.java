package com.finalProject.SoftUniProject.web;

import com.finalProject.SoftUniProject.model.dto.SupplementAddDTO;
import com.finalProject.SoftUniProject.service.SupplementService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SupplementController {
    private final SupplementService supplementService;

    public SupplementController(SupplementService supplementService) {
        this.supplementService = supplementService;
    }

    @GetMapping("/supplement-add")
    public ModelAndView supplementAddView(@ModelAttribute("supplementAddDTO") SupplementAddDTO supplementAddDTO){
        return new ModelAndView("supplement-add");
    }

    @PostMapping("/supplement-add")
    public ModelAndView supplementAdd(@ModelAttribute("supplementAddDTO") SupplementAddDTO supplementAddDTO){
        supplementService.createSupplement(supplementAddDTO);
        return new ModelAndView("redirect:/home");
    }
}
