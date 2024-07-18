package com.finalProject.SoftUniProject.service;

import com.finalProject.SoftUniProject.model.dto.UserRegistrationDTO;
import com.finalProject.SoftUniProject.model.entity.User;
import com.finalProject.SoftUniProject.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public void registerUser(UserRegistrationDTO userRegistrationDTO){
        userRepository.save(map(userRegistrationDTO));
    }

    private User map(UserRegistrationDTO userRegistrationDTO){
        User mappedEntity = modelMapper.map(userRegistrationDTO, User.class);
        mappedEntity.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));

        return mappedEntity;
    }
}
