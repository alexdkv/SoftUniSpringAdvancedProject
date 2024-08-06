package com.finalProject.SoftUniProject.service;

import com.finalProject.SoftUniProject.model.entity.Exercise;
import com.finalProject.SoftUniProject.model.entity.Role;
import com.finalProject.SoftUniProject.model.entity.User;
import com.finalProject.SoftUniProject.model.enums.UserRoleENUM;
import com.finalProject.SoftUniProject.repository.ExerciseRepository;
import com.finalProject.SoftUniProject.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceIT {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Test
    public void testAssignCoach(){
        //A
        Role traineeRole = new Role();
        traineeRole.setId(1L);
        traineeRole.setName(UserRoleENUM.TRAINEE);

        Role coachRole = new Role();
        coachRole.setId(2L);
        coachRole.setName(UserRoleENUM.COACH);

        User user = new User();
        user.setUsername("testTrainee");
        user.setEmail("user@test.com");
        user.setPassword("test");
        user.setRole(List.of(traineeRole));

        user = userRepository.save(user);

        User coach = new User();
        coach.setUsername("testCoach");
        coach.setEmail("coach@test.com");
        coach.setPassword("test");
        coach.setRole(List.of(coachRole));

        Exercise exerciseTestOne = new Exercise();
        exerciseTestOne.setName("testOne");
        exerciseTestOne.setDescription("testOne");
        exerciseTestOne.setPhotoUrl("https://test/test");
        exerciseTestOne.setIntensity(3);
        exerciseTestOne.setCoach(coach);

        Exercise exerciseTestTwo= new Exercise();
        exerciseTestTwo.setName("testTwo");
        exerciseTestTwo.setDescription("testTwo");
        exerciseTestTwo.setIntensity(3);
        exerciseTestTwo.setPhotoUrl("https://test/test");
        exerciseTestTwo.setCoach(coach);

        coach.setAddedExercises(Arrays.asList(exerciseTestOne, exerciseTestTwo));
        coach = userRepository.save(coach);
        exerciseTestOne =  exerciseRepository.save(exerciseTestOne);
        exerciseTestTwo = exerciseRepository.save(exerciseTestTwo);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //A
        userService.assignCoach(coach);

        //A
        User updatedUser = userRepository.findById(user.getId()).orElseThrow();

        Assertions.assertEquals(coach.getId(), updatedUser.getCoach().getId());
        Assertions.assertTrue(updatedUser.getExercises()
                .stream()
                .anyMatch(exercise -> "testOne".equals(exercise.getName())));
        Assertions.assertTrue(updatedUser.getExercises()
                .stream()
                .anyMatch(exercise -> "testTwo".equals(exercise.getName())));

    }


}
