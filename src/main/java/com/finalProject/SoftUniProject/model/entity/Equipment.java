package com.finalProject.SoftUniProject.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "equipment")
public class Equipment extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @NotNull
    private String URL;

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

    public  String getURL() {
        return URL;
    }

    public void setURL( String URL) {
        this.URL = URL;
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
