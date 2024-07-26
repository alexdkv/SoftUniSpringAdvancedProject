package com.finalProject.SoftUniProject.model.dto;

import com.finalProject.SoftUniProject.model.enums.UserRoleENUM;
import jakarta.validation.constraints.*;

public class UserRegistrationDTO {

    @NotBlank(message = "Must not be empty")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters!")
    private String username;

    @NotBlank(message = "Must not be empty")
    @Size(min = 3, max = 20, message = "Password must be between 3 and 20 characters!")
    private String password;

    @NotBlank(message = "Must not be empty")
    @Size(min = 3, max = 20, message = "Password must be between 3 and 20 characters!")
    private String confirmPassword;

    @NotBlank(message = "Email must not be empty!")
    @Email
    private String email;

    @NotNull(message = "Role must be selected!")
    private UserRoleENUM role;

    public  String getUsername() {
        return username;
    }

    public void setUsername( String username) {
        this.username = username;
    }

    public   String getPassword() {
        return password;
    }

    public void setPassword( String password) {
        this.password = password;
    }

    public  String getEmail() {
        return email;
    }

    public void setEmail( String email) {
        this.email = email;
    }

    public @NotEmpty String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(@NotEmpty String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public  UserRoleENUM getRole() {
        return role;
    }

    public void setRole( UserRoleENUM role) {
        this.role = role;
    }
}
