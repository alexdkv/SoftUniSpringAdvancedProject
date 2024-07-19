package com.finalProject.SoftUniProject.config;

import com.finalProject.SoftUniProject.repository.UserRepository;
import com.finalProject.SoftUniProject.service.UserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.net.http.HttpClient;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .authorizeHttpRequests(
                        authorizeRequest ->
                            authorizeRequest
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers("/", "/index", "/register", "/login", "/home", "/login-error").permitAll()
                                .anyRequest()
                                .authenticated()
        )
        .formLogin(formLogin ->
                formLogin
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/login-error")
        )
        .logout(
                logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/")
                                .invalidateHttpSession(true)
        )
        .build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return new UserDetailsService(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
