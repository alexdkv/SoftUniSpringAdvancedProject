package com.finalProject.SoftUniProject.service;

import com.finalProject.SoftUniProject.model.dto.UserRegistrationDTO;
import com.finalProject.SoftUniProject.model.entity.Role;
import com.finalProject.SoftUniProject.model.entity.User;
import com.finalProject.SoftUniProject.model.enums.UserRoleENUM;
import com.finalProject.SoftUniProject.repository.RoleRepository;
import com.finalProject.SoftUniProject.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
