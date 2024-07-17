package com.finalProject.SoftUniProject.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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

    @ManyToMany(mappedBy = "equipment")
    private List<Exercise> usedInExercise;

    public Equipment() {
        this.usedInExercise = new ArrayList<>();
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

    public List<Exercise> getUsedInExercise() {
        return usedInExercise;
    }

    public void setUsedInExercise(List<Exercise> usedInExercise) {
        this.usedInExercise = usedInExercise;
    }
}
