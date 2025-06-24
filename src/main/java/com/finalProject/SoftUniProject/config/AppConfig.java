package com.finalProject.SoftUniProject.config;

import com.finalProject.SoftUniProject.repository.UserRepository;
import com.finalProject.SoftUniProject.service.LoginDetailsServiceImpl;
import com.finalProject.SoftUniProject.service.UserDetailsService;
import com.finalProject.SoftUniProject.service.jwt.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class AppConfig {

    @Bean
    public WebMvcConfigurer corsConfig(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost", "http://localhost:8080")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }



    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /*
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return new UserDetailsService(userRepository);
    }

     */

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LoginDetailsServiceImpl userDetailsService(UserRepository userRepository, JwtService jwtService, HttpServletResponse response) {
        return new LoginDetailsServiceImpl(jwtService, userRepository, response);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserRepository userRepository, JwtService jwtService, HttpServletResponse response) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService(userRepository, jwtService, response));
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
