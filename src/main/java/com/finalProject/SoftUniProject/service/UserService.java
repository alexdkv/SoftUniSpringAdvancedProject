package com.finalProject.SoftUniProject.service;

import com.finalProject.SoftUniProject.model.dto.UserRegistrationDTO;
import com.finalProject.SoftUniProject.model.entity.Exercise;
import com.finalProject.SoftUniProject.model.entity.Role;
import com.finalProject.SoftUniProject.model.entity.User;
import com.finalProject.SoftUniProject.model.enums.UserRoleENUM;
import com.finalProject.SoftUniProject.repository.RoleRepository;
import com.finalProject.SoftUniProject.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public void registerUser(UserRegistrationDTO userRegistrationDTO){
        userRepository.save(map(userRegistrationDTO));
    }

    private User map(UserRegistrationDTO userRegistrationDTO){
        User mappedEntity = modelMapper.map(userRegistrationDTO, User.class);
        mappedEntity.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));

        Role role = roleRepository.findByName(userRegistrationDTO.getRole())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Role"));

        mappedEntity.setRole(role);

        return mappedEntity;
    }

    public List<User> findAllCoaches(){
        Role role = roleRepository.findByName(UserRoleENUM.COACH).get();
        return userRepository.findAllByRole(role);
    }

    public User findById(long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Exercise with ID " + id + " not found"));
    }





    @Transactional
    public void assignCoach(User coach){
        String email = getEmail();
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new IllegalStateException("User not found.");
        }

        if (user.get().getCoach() != null){
            throw new IllegalStateException("User already has a coach!");
        }
        user.get().setCoach(coach);

        List<Exercise> coachExercises = coach.getAddedExercises();
        List<Exercise> userExercises = user.get().getExercises();

        for (Exercise exercise : userExercises) {
            exercise.getUsers().remove(user.get());
        }
        userExercises.clear();

        for (Exercise exercise : coachExercises) {
            userExercises.add(exercise);
            exercise.getUsers().add(user.get());
        }

        userRepository.save(user.get());
    }


    protected static String getEmail() {
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
}
