package com.finalProject.SoftUniProject.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "equipment")
public class Equipment extends BaseEntity{

    @Column(nullable = false, unique = true)
    @Length(min = 3)
    private String name;

    @NotNull
    @URL
    private String photoUrl;

    @Column
    private int weight;

    @OneToMany(mappedBy = "equipment")
    private List<Exercise> exercises;

    public Equipment() {
        this.exercises = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
