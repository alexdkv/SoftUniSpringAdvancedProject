package com.finalProject.SoftUniProject.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;

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

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne
    private User coach;

    @OneToMany(mappedBy = "coach")
    private List<User> trainees;

    @ManyToMany(mappedBy = "users")
    private List<Exercise> exercises;

    @OneToMany(mappedBy = "coach")
    private List<Exercise> addedExercises;





    public User() {
        this.trainees = new ArrayList<>();
        this.exercises = new ArrayList<>();
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
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
}
