package com.finalProject.SoftUniProject.controller;

import com.finalProject.SoftUniProject.model.dto.EquipmentAddBindingModel;
import com.finalProject.SoftUniProject.model.dto.ExerciseAddBindingModel;
import com.finalProject.SoftUniProject.service.EquipmentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EquipmentController {
    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping("/equipment-add")
    public ModelAndView equipmentAddView(@ModelAttribute("equipmentAddBindingModel") EquipmentAddBindingModel equipmentAddBindingModel){

        return new ModelAndView("equipment-add");
    }

    @PostMapping("/equipment-add")
    public ModelAndView ExerciseAdd(@ModelAttribute("equipmentAddBindingModel") @Valid EquipmentAddBindingModel equipmentAddBindingModel,
                                    BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ModelAndView("equipment-add");
        }

          boolean hasAddedEquipment =  equipmentService.addEquipment(equipmentAddBindingModel);
          if (!hasAddedEquipment){
              ModelAndView modelAndView = new ModelAndView("equipment-add");
              modelAndView.addObject("hasErrors", true);
              modelAndView.addObject("errorMessage", "This item already exists!");
              return modelAndView;
          }

        return new ModelAndView("redirect:/home");
    }
}
