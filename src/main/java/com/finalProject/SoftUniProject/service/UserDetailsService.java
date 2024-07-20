package com.finalProject.SoftUniProject.service;

import com.finalProject.SoftUniProject.model.entity.Role;
import com.finalProject.SoftUniProject.model.entity.User;
import com.finalProject.SoftUniProject.model.enums.UserRoleENUM;
import com.finalProject.SoftUniProject.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

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

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().getName());

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(authority)
                .disabled(false)
                .build();

        /*
        return new com.finalProject.SoftUniProject.model.UserDetails(
                user.getEmail(),
                user.getPassword(),
                user.getRole().stream().map(r -> r.).map(UserDetailsService::map).toList());

         */
    }
/*
    private static GrantedAuthority map(UserRoleENUM role) {
        return new SimpleGrantedAuthority(
                "ROLE_" + role
        );
    }

 */
}
