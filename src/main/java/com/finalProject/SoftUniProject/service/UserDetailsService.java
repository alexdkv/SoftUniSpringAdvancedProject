package com.finalProject.SoftUniProject.service;

import com.finalProject.SoftUniProject.model.entity.User;
import com.finalProject.SoftUniProject.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

         return userRepository
                .findByEmail(email)
                .map(UserDetailsService::map)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    private static UserDetails map(User user){
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                //.authorities()
                .disabled(false)
                .build();
    }
}
