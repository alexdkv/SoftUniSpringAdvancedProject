package com.finalProject.SoftUniProject.model.entity;

import com.finalProject.SoftUniProject.model.enums.SpecialtyName;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity{

    @Column(unique = true, nullable = false)
    @Length(min = 3, max = 20)
    private String username;

    @Column(nullable = false)
    private String password;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> role;

    @ManyToOne
    private User coach;

    @OneToMany(mappedBy = "coach", fetch = FetchType.EAGER)
    private List<User> trainees;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Exercise> exercises;

    @OneToMany(mappedBy = "coach", fetch = FetchType.EAGER)
    private List<Exercise> addedExercises;

    @URL
    private String photoUrl;

    private String bio;

    private String fullName;

    @Enumerated(EnumType.STRING)
    private SpecialtyName specialty;


    public User() {
        this.trainees = new ArrayList<>();
        this.exercises = new ArrayList<>();
        this.addedExercises = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public  String getEmail() {
        return email;
    }

    public void setEmail( String email) {
        this.email = email;
    }

    public User getCoach() {
        return coach;
    }

    public void setCoach(User coach) {
        this.coach = coach;
    }

    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }

    public List<User> getTrainees() {
        return trainees;
    }

    public void setTrainees(List<User> trainees) {
        this.trainees = trainees;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public List<Exercise> getAddedExercises() {
        return addedExercises;
    }

    public void setAddedExercises(List<Exercise> addedExercises) {
        this.addedExercises = addedExercises;
    }

    public @URL String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(@URL String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public  String getBio() {
        return bio;
    }

    public void setBio( String bio) {
        this.bio = bio;
    }

    public  String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public SpecialtyName getSpecialty() {
        return specialty;
    }

    public void setSpecialty(SpecialtyName specialty) {
        this.specialty = specialty;
    }
}
