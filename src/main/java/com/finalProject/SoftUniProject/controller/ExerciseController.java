package com.finalProject.SoftUniProject.controller;

import com.finalProject.SoftUniProject.model.dto.ExerciseAddBindingModel;
import com.finalProject.SoftUniProject.model.entity.Exercise;
import com.finalProject.SoftUniProject.model.entity.User;
import com.finalProject.SoftUniProject.repository.ExerciseRepository;
import com.finalProject.SoftUniProject.repository.UserRepository;
import com.finalProject.SoftUniProject.service.EquipmentService;
import com.finalProject.SoftUniProject.service.ExerciseService;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
public class ExerciseController {
    private final ExerciseService exerciseService;
    private final EquipmentService equipmentService;
    private final UserRepository userRepository;


    public ExerciseController(ExerciseService exerciseService, EquipmentService equipmentService, UserRepository userRepository) {
        this.exerciseService = exerciseService;
        this.equipmentService = equipmentService;
        this.userRepository = userRepository;
    }

    @GetMapping("/exercise-add")
    public ModelAndView exerciseAddView(@ModelAttribute("exerciseAddBindingModel") ExerciseAddBindingModel exerciseAddBindingModel){

        ModelAndView modelAndView = new ModelAndView("exercise-add");
        modelAndView.addObject("equipmentList", equipmentService.getAllEquipment());
        return modelAndView;
    }

    @PostMapping("/exercise-add")
    public ModelAndView ExerciseAdd(@ModelAttribute("exerciseAddBindingModel") @Valid ExerciseAddBindingModel exerciseAddBindingModel,
                                    BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return new ModelAndView("exercise-add");
        }
        exerciseService.addExercise(exerciseAddBindingModel);

        return new ModelAndView("redirect:/home");
    }



    @GetMapping("/exercises-coach")
    public ModelAndView ExercisesCoach(Principal principal){
        ModelAndView modelAndView = new ModelAndView("exercises-coach");
        if (userRepository.findByEmail(principal.getName()).isEmpty()){
            throw new UsernameNotFoundException("User not found!");
        }
        User currentCoach = userRepository.findByEmail(principal.getName()).get();

        List<Exercise> myExercises = exerciseService.findByCoach(currentCoach);

        List<Exercise> otherExercises = exerciseService.findOthers(currentCoach);

        modelAndView.addObject("myExercises", myExercises);
        modelAndView.addObject("otherExercises", otherExercises);

        return modelAndView;
    }
}
