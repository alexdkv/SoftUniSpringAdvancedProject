package com.finalProject.SoftUniProject.web;

import com.finalProject.SoftUniProject.model.dto.SupplementAddDTO;
import com.finalProject.SoftUniProject.service.SupplementService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/supplements")
    public ModelAndView supplementsPreview(@PageableDefault(
            size = 3,
            sort = "id"
    )Pageable pageable){
        ModelAndView modelAndView = new ModelAndView("supplements-all-preview");
        modelAndView.addObject("supplements", supplementService.getAllSupplements(pageable));
        return modelAndView;
    }

    @GetMapping("/supplement/details/{id}")
    public ModelAndView supplementDetails(@PathVariable("id")Long id){
        ModelAndView modelAndView = new ModelAndView("supplement-details");
        modelAndView.addObject("supplement",supplementService.getSupplementById(id));
        return modelAndView;
    }

    @DeleteMapping("/supplement/{id}")
    public ModelAndView supplementDelete(@PathVariable("id")Long id){
        supplementService.deleteSupplement(id);
        return new ModelAndView("redirect:/supplements");
    }
}
