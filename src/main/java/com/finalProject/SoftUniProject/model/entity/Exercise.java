package com.finalProject.SoftUniProject.model.entity;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exercises")
public class Exercise extends BaseEntity{

    @Column(nullable = false)
    @Length(min = 3, max = 20)
    private String name;

    @Column(nullable = false)
    @Length(min = 3, max = 50)
    private String description;

    @Column(nullable = false)
    private int intensity;

    @ManyToMany
    @JoinTable(
            name = "exercise_equipment",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id")
    )
    private List<Equipment> equipment;

    @ManyToMany
    @JoinTable(
            name = "coach_exercise",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> coaches;

    @ManyToMany
    @JoinTable(
            name = "user_exercises",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    public Exercise() {
        this.equipment = new ArrayList<>();
    }

    public String getName() {
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

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public List<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
    }

    public List<User> getCoaches() {
        return coaches;
    }

    public void setCoaches(List<User> coaches) {
        this.coaches = coaches;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
