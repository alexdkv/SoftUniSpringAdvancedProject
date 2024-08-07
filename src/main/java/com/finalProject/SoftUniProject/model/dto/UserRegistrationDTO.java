package com.finalProject.SoftUniProject.model.dto;

import com.finalProject.SoftUniProject.model.enums.SpecialtyName;
import com.finalProject.SoftUniProject.model.enums.UserRoleENUM;
import jakarta.annotation.Nullable;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

public class UserRegistrationDTO {

    @NotBlank(message = "{not_empty}")
    @Size(min = 3, max = 20, message = "{register_username_error}")
    private String username;

    @NotBlank(message = "{not_empty}")
    @Size(min = 3, max = 20, message = "{register_password_error}")
    private String password;

    @NotBlank()
    @Size(min = 3, max = 20, message = "{register_password_error}")
    private String confirmPassword;

    @NotBlank(message = "{not_empty}")
    @Email
    private String email;

    @NotNull(message = "{register_role_error}")
    private UserRoleENUM role;

    @URL(message = "{register_photo_url_error}")
    @NotNull(message = "{not_empty}")
    private String photoUrl;

    private SpecialtyName specialty;

    @NotBlank(message = "{not_empty}")
    @Size(min = 3,  message = "{register_biography_error}")
    private String bio;

    @NotBlank(message = "{not_empty}")
    @Size(min = 3, message = "{register_full_name_error}")
    private String fullName;

    public UserRegistrationDTO() {
    }

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
