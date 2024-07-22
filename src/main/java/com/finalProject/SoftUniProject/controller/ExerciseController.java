package com.finalProject.SoftUniProject.controller;

import com.finalProject.SoftUniProject.model.dto.ExerciseAddBindingModel;
import com.finalProject.SoftUniProject.repository.ExerciseRepository;
import com.finalProject.SoftUniProject.repository.UserRepository;
import com.finalProject.SoftUniProject.service.ExerciseService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExerciseController {
    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping("/exercise-add")
    public ModelAndView exerciseAddView(@ModelAttribute("exerciseAddBindingModel") ExerciseAddBindingModel exerciseAddBindingModel){

        return new ModelAndView("exercise-add");
    }

    @PostMapping("/exercise-add")
    public ModelAndView ExerciseAdd(@ModelAttribute("exerciseAddBindingModel") @Valid ExerciseAddBindingModel exerciseAddBindingModel,
                                    BindingResult bindingResult){

        exerciseService.addExercise(exerciseAddBindingModel);

        return new ModelAndView("redirect:/home");
    }
}
