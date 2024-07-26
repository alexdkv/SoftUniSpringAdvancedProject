package com.finalProject.SoftUniProject.model.dto;

import com.finalProject.SoftUniProject.model.enums.SpecialtyName;
import com.finalProject.SoftUniProject.model.enums.UserRoleENUM;
import jakarta.annotation.Nullable;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

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

    @URL
    private String photoUrl;

    private SpecialtyName specialty;

    private String bio;

    private String fullName;

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

    public @URL String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(@URL String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public SpecialtyName getSpecialty() {
        return specialty;
    }

    public void setSpecialty(SpecialtyName specialty) {
        this.specialty = specialty;
    }

    public  String getBio() {
        return bio;
    }

    public void setBio( String bio) {
        this.bio = bio;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName( String fullName) {
        this.fullName = fullName;
    }
}
