package com.finalProject.SoftUniProject.service;

import com.finalProject.SoftUniProject.model.dto.ExerciseAddBindingModel;
import com.finalProject.SoftUniProject.model.entity.Exercise;
import com.finalProject.SoftUniProject.model.entity.User;
import com.finalProject.SoftUniProject.repository.ExerciseRepository;
import com.finalProject.SoftUniProject.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {
    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;

    public ExerciseService(UserRepository userRepository, ExerciseRepository exerciseRepository) {
        this.userRepository = userRepository;
        this.exerciseRepository = exerciseRepository;
    }

    public void addExercise(ExerciseAddBindingModel exerciseAddBindingModel) {

        String email = getEmail();
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new IllegalStateException("User not found.");
        }

        Exercise exercise = new Exercise();
        exercise.setName(exerciseAddBindingModel.getName());
        exercise.setDescription(exerciseAddBindingModel.getDescription());
        exercise.setIntensity(exerciseAddBindingModel.getIntensity());
        exercise.setPhotoUrl(exerciseAddBindingModel.getPhotoUrl());
        exercise.setEquipment(exerciseAddBindingModel.getEquipment());

        exercise.setCoach(user.get());
        exerciseRepository.save(exercise);
    }

    private static String getEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = null;

        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                email = ((UserDetails) principal).getUsername();
            } else {
                email = principal.toString();
            }
        }

        if (email == null) {
            throw new IllegalStateException("User is not logged in.");

        }
        return email;
    }

    public List<Exercise> findByCoach(User coach) {
        return exerciseRepository.findByCoach(coach);
    }

    public List<Exercise> findOthers(User coach){
        return exerciseRepository.findByCoachNot(coach);
    }
}