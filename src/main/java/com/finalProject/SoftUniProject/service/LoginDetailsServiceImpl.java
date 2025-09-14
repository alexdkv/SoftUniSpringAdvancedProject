package com.finalProject.SoftUniProject.service;

import com.finalProject.SoftUniProject.model.entity.Role;
import com.finalProject.SoftUniProject.model.entity.User;
import com.finalProject.SoftUniProject.repository.UserRepository;
import com.finalProject.SoftUniProject.service.jwt.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.stream.Collectors;

public class LoginDetailsServiceImpl implements UserDetailsService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final HttpServletResponse response;

    public LoginDetailsServiceImpl(JwtService jwtService, UserRepository userRepository, HttpServletResponse response) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.response = response;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        UserDetails userDetails = user.map(LoginDetailsServiceImpl::map)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " does not exist."));
        Cookie cookie = new Cookie("jwt", jwtService.generateToken(user.get()));
        cookie.setMaxAge(60*60*24*1000);
        //cookie.setDomain("localhost");
        //cookie.setDomain("192.168.0.151");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        //cookie.setSecure(true);
        response.addCookie(cookie);
        return userDetails;
    }

    private static UserDetails map(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRole().stream().map(LoginDetailsServiceImpl::map).collect(Collectors.toList())
        );
    }

    private static GrantedAuthority map(Role role) {
        return new SimpleGrantedAuthority(
                "ROLE_" + role.getName().name()
        );
    }
}
