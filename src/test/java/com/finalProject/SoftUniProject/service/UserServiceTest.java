package com.finalProject.SoftUniProject.service;

import com.finalProject.SoftUniProject.model.dto.UserRegistrationDTO;
import com.finalProject.SoftUniProject.model.entity.Exercise;
import com.finalProject.SoftUniProject.model.entity.Role;
import com.finalProject.SoftUniProject.model.entity.User;
import com.finalProject.SoftUniProject.model.enums.SpecialtyName;
import com.finalProject.SoftUniProject.model.enums.UserRoleENUM;
import com.finalProject.SoftUniProject.repository.RoleRepository;
import com.finalProject.SoftUniProject.repository.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private UserService toTest;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private RoleRepository mockRoleRepository;

    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @BeforeEach
    void setUp(){
        toTest = new UserService(
                mockUserRepository,
                mockRoleRepository,
                mockPasswordEncoder,
                new ModelMapper()
        );
    }

    @Test
    void testUserRegistrationTrainee(){
        UserRegistrationDTO userRegistrationDTO = getUserRegistrationDTO();
        userRegistrationDTO.setRole(UserRoleENUM.TRAINEE);
        Role traineeRole = new Role();
        traineeRole.setId(1L);
        traineeRole.setName(UserRoleENUM.TRAINEE);

        when(mockRoleRepository.findByName(UserRoleENUM.TRAINEE))
                .thenReturn(Optional.of(traineeRole));

        when(mockPasswordEncoder.encode(userRegistrationDTO.getPassword()))
                .thenReturn(userRegistrationDTO.getPassword()+userRegistrationDTO.getPassword());


        toTest.registerUser(userRegistrationDTO);

        verify(mockUserRepository).save(userArgumentCaptor.capture());
        User actualSavedUser = userArgumentCaptor.getValue();

        Assertions.assertNotNull(actualSavedUser);
        Assertions.assertEquals(userRegistrationDTO.getUsername(), actualSavedUser.getUsername());
        Assertions.assertEquals(userRegistrationDTO.getEmail(), actualSavedUser.getEmail());
        Assertions.assertEquals(userRegistrationDTO.getRole(), actualSavedUser.getRole().getFirst().getName());
        Assertions.assertEquals(userRegistrationDTO.getPassword() + userRegistrationDTO.getPassword()
                , actualSavedUser.getPassword());

    }

    @Test
    void testUserRegistrationCoach(){
        UserRegistrationDTO userRegistrationDTO = getUserRegistrationDTO();
        userRegistrationDTO.setBio("test");
        userRegistrationDTO.setFullName("Alex Test");
        userRegistrationDTO.setPhotoUrl("https://picsum.photos/200");
        userRegistrationDTO.setSpecialty(SpecialtyName.FITNESS);
        userRegistrationDTO.setRole(UserRoleENUM.COACH);

        Role coachRole = new Role();
        coachRole.setId(2L);
        coachRole.setName(UserRoleENUM.COACH);

        when(mockRoleRepository.findByName(UserRoleENUM.COACH))
                .thenReturn(Optional.of(coachRole));

        when(mockPasswordEncoder.encode(userRegistrationDTO.getPassword()))
                .thenReturn(userRegistrationDTO.getPassword()+userRegistrationDTO.getPassword());


        toTest.registerUser(userRegistrationDTO);

        verify(mockUserRepository).save(userArgumentCaptor.capture());
        User actualSavedUser = userArgumentCaptor.getValue();

        Assertions.assertNotNull(actualSavedUser);
        Assertions.assertEquals(userRegistrationDTO.getUsername(), actualSavedUser.getUsername());
        Assertions.assertEquals(userRegistrationDTO.getEmail(), actualSavedUser.getEmail());
        Assertions.assertEquals(userRegistrationDTO.getRole(), actualSavedUser.getRole().getFirst().getName());
        Assertions.assertEquals(userRegistrationDTO.getPassword() + userRegistrationDTO.getPassword()
                , actualSavedUser.getPassword());
        Assertions.assertEquals(userRegistrationDTO.getBio(), actualSavedUser.getBio());
        Assertions.assertEquals(userRegistrationDTO.getFullName(), actualSavedUser.getFullName());
        Assertions.assertEquals(userRegistrationDTO.getSpecialty(), actualSavedUser.getSpecialty());
        Assertions.assertEquals(userRegistrationDTO.getPhotoUrl(), actualSavedUser.getPhotoUrl());

    }

    @Test
    void testRegistrationSameUsernameEmail(){
        UserRegistrationDTO userRegistrationDTO = getUserRegistrationDTO();
        userRegistrationDTO.setRole(UserRoleENUM.TRAINEE);

        UserRegistrationDTO userRegistrationDTOCopy = getUserRegistrationDTO();
        userRegistrationDTOCopy.setRole(UserRoleENUM.TRAINEE);

        Role traineeRole = new Role();
        traineeRole.setId(1L);
        traineeRole.setName(UserRoleENUM.TRAINEE);
        when(mockRoleRepository.findByName(UserRoleENUM.TRAINEE))
                .thenReturn(Optional.of(traineeRole));

        when(mockPasswordEncoder.encode(userRegistrationDTO.getPassword()))
                .thenReturn(userRegistrationDTO.getPassword()+userRegistrationDTO.getPassword());

        when(mockUserRepository.existsByUsernameOrEmail(userRegistrationDTO.getUsername(), userRegistrationDTO.getEmail()))
                .thenReturn(false)
                .thenReturn(true); // Second call will return true to simulate existing username/email

        boolean firstRegistrationResult = toTest.registerUser(userRegistrationDTO);
        boolean secondRegistrationResult = toTest.registerUser(userRegistrationDTOCopy);

        // Expect an exception on second registration attempt due to duplicate username/email
        assertTrue(firstRegistrationResult);
        assertFalse(secondRegistrationResult);
    }

    @Test
    void testGetAllCoaches(){
        Role coachRole = new Role();
        coachRole.setId(2L);
        coachRole.setName(UserRoleENUM.COACH);

        User coachTestOne = new User();
        coachTestOne.setEmail("testOne@test.com");
        coachTestOne.setRole(List.of(coachRole));

        User coachTestTwo = new User();
        coachTestTwo.setEmail("testTwo@test.com");
        coachTestTwo.setRole(List.of(coachRole));

        List<User> coaches = new ArrayList<>();
        coaches.add(coachTestOne);
        coaches.add(coachTestTwo);

        when(mockRoleRepository.findByName(UserRoleENUM.COACH)).thenReturn(Optional.of(coachRole));
        when(mockUserRepository.findAllByRole(coachRole)).thenReturn(coaches);

        List<User> actualCoaches = toTest.findAllCoaches();

        Assertions.assertFalse(actualCoaches.isEmpty());
        Assertions.assertEquals(coaches, actualCoaches);
    }


    private static UserRegistrationDTO getUserRegistrationDTO() {
        UserRegistrationDTO userRegistrationDTO;
        userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setUsername("alex");
        userRegistrationDTO.setEmail("alex@test.com");

        userRegistrationDTO.setPassword("test");
        userRegistrationDTO.setConfirmPassword("test");

        return userRegistrationDTO;
    }
}
