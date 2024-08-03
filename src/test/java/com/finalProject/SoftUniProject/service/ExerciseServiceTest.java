package com.finalProject.SoftUniProject.service;

import com.finalProject.SoftUniProject.model.dto.ExerciseAddBindingModel;
import com.finalProject.SoftUniProject.model.entity.Equipment;
import com.finalProject.SoftUniProject.model.entity.Exercise;
import com.finalProject.SoftUniProject.model.entity.User;
import com.finalProject.SoftUniProject.repository.ExerciseRepository;
import com.finalProject.SoftUniProject.repository.UserRepository;
import com.finalProject.SoftUniProject.service.exception.ResourceInUseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExerciseServiceTest {

    private ExerciseService toTest;

    @Mock
    private ExerciseRepository mockExerciseRepository;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private SecurityContext mockSecurityContext;

    @Mock
    private Authentication mockAuthentication;

    @Mock
    private UserDetails mockUserDetails;

    @Captor
    private ArgumentCaptor<Exercise> exerciseArgumentCaptor;

    @BeforeEach
    void setUp(){
        toTest = new ExerciseService(
                mockUserRepository,
                mockExerciseRepository
        );
        SecurityContextHolder.setContext(mockSecurityContext);
    }

    @Test
    void testAddExerciseNoUser(){
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getPrincipal()).thenReturn(mockUserDetails);
        when(mockUserDetails.getUsername()).thenReturn("email@test.com");
        when(mockUserRepository.findByEmail("email@test.com")).thenReturn(Optional.empty());
        ExerciseAddBindingModel exerciseAddBindingModel;
        exerciseAddBindingModel = new ExerciseAddBindingModel();
        assertThrows(UsernameNotFoundException.class, () -> {
            toTest.addExercise(exerciseAddBindingModel);
        });
    }
    @Test
    void testAddExerciseSuccessful(){
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getPrincipal()).thenReturn(mockUserDetails);
        when(mockUserDetails.getUsername()).thenReturn("email@test.com");
        User user = new User();
        when(mockUserRepository.findByEmail("email@test.com")).thenReturn(Optional.of(user));

        ExerciseAddBindingModel exerciseAddBindingModel;
        exerciseAddBindingModel = new ExerciseAddBindingModel();
        exerciseAddBindingModel.setName("test");
        exerciseAddBindingModel.setDescription("test");
        exerciseAddBindingModel.setIntensity(5);
        exerciseAddBindingModel.setPhotoUrl("https://picsum.photos/200");
        exerciseAddBindingModel.setEquipment(new Equipment());

        toTest.addExercise(exerciseAddBindingModel);
        verify(mockExerciseRepository).save(exerciseArgumentCaptor.capture());
        Exercise addedExercise = exerciseArgumentCaptor.getValue();

        Assertions.assertNotNull(addedExercise);
        assertEquals(exerciseAddBindingModel.getName(),addedExercise.getName());
        assertEquals(exerciseAddBindingModel.getDescription(),addedExercise.getDescription());
        assertEquals(exerciseAddBindingModel.getIntensity(),addedExercise.getIntensity());
        assertEquals(exerciseAddBindingModel.getPhotoUrl(),addedExercise.getPhotoUrl());

    }
    @Test
    void testDeleteSuccessful(){
        Long id = 1L;
        when(mockUserRepository.findAllByExercisesId(id)).thenReturn(Collections.emptyList());
        when(mockExerciseRepository.findById(id)).thenReturn(Optional.of(new Exercise()));

        toTest.deleteExercise(id);
        verify(mockUserRepository, times(1)).findAllByExercisesId(id);
        verify(mockExerciseRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteExerciseInUse(){
        Long id = 1L;
        User user = new User();
        List<User> users = List.of(user);
        when(mockUserRepository.findAllByExercisesId(id)).thenReturn(users);

        Assertions.assertThrows(ResourceInUseException.class,
                ()->{
            toTest.deleteExercise(id);
                });

        verify(mockUserRepository, times(1)).findAllByExercisesId(id);
        verify(mockExerciseRepository, never()).deleteById(id);
    }

    @Test
    void testFindByCoach() {
        User coach = new User();
        Exercise exercise1 = new Exercise();
        Exercise exercise2 = new Exercise();
        List<Exercise> exercises = Arrays.asList(exercise1, exercise2);

        when(mockExerciseRepository.findByCoach(coach)).thenReturn(exercises);

        List<Exercise> result = toTest.findByCoach(coach);

        assertEquals(exercises, result);
        verify(mockExerciseRepository, times(1)).findByCoach(coach);
    }

    @Test
    void testFindOthers() {
        User coach = new User();
        Exercise exercise1 = new Exercise();
        Exercise exercise2 = new Exercise();
        List<Exercise> exercises = Arrays.asList(exercise1, exercise2);

        when(mockExerciseRepository.findByCoachNot(coach)).thenReturn(exercises);

        List<Exercise> result = toTest.findOthers(coach);

        assertEquals(exercises, result);
        verify(mockExerciseRepository, times(1)).findByCoachNot(coach);
    }
}
