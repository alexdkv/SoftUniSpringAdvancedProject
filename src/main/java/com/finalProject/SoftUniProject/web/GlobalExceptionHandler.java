package com.finalProject.SoftUniProject.web;

import com.finalProject.SoftUniProject.service.exception.IllegalArgumentException;
import com.finalProject.SoftUniProject.service.exception.IllegalStateException;
import com.finalProject.SoftUniProject.service.exception.ResourceInUseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResourceInUseException.class)
    public ModelAndView handleIllegalStateExceptionExercise(ResourceInUseException resourceInUseException){
        ModelAndView modelAndView = new ModelAndView("illegal-state-exercise-in-use");
        modelAndView.addObject("objectId", resourceInUseException.getId());
        return modelAndView;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public ModelAndView handleIllegalStateExceptionCoach(IllegalStateException illegalStateException){
        ModelAndView modelAndView = new ModelAndView("illegal-state-coach-assigned");
        modelAndView.addObject("objectId", illegalStateException.getId());
        return modelAndView;
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView handleIllegalArgumentExceptionTraineeToCoach(IllegalArgumentException illegalArgumentException){
        return new ModelAndView("illegal-argument-trainee-to-coach-exception");
    }
}
