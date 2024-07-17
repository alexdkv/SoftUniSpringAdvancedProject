package com.finalProject.SoftUniProject.model.entity;

import com.finalProject.SoftUniProject.model.enums.SpecialtyName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "coaches")
public class Coach extends BaseEntity{

    @Column(nullable = false)
    @Length(min = 4, max = 30)
    private String name;

    @Column(nullable = false)
    @Length(min = 8, max = 60)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private SpecialtyName specialty;

    @OneToMany(mappedBy = "coach")
    private List<User> trainees;

    @ManyToMany(mappedBy = "coaches")
    private List<Exercise> exercises;

    public Coach() {
        this.trainees = new ArrayList<>();
        this.exercises = new ArrayList<>();
    }

    public  String getName() {
        return name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

    public SpecialtyName getSpecialty() {
        return specialty;
    }

    public void setSpecialty(SpecialtyName specialty) {
        this.specialty = specialty;
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
