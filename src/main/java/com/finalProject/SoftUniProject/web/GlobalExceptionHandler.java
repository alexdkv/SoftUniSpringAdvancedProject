package com.finalProject.SoftUniProject.web;

import com.finalProject.SoftUniProject.service.exception.IllegalStateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(IllegalStateException.class)
    public ModelAndView handleIllegalStateException(IllegalStateException illegalStateException){
        ModelAndView modelAndView = new ModelAndView("illegal-state");
        modelAndView.addObject("objectId", illegalStateException.getId());
        return modelAndView;
    }
}
