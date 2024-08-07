package com.finalProject.SoftUniProject.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exercises")
public class Exercise extends BaseEntity{

    @Column(nullable = false)
    @Length(min = 3, max = 50)
    private String name;

    @Column(nullable = false)
    @Length(min = 3)
    private String description;

    @Column(nullable = false)
    private int intensity;

    @NotNull
    @org.hibernate.validator.constraints.URL
    private String photoUrl;

    @ManyToOne
    private Equipment equipment;

    @ManyToOne
    private User coach;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_exercises",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    public Exercise() {
        this.users = new ArrayList<>();

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

    public @NotNull String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(@NotNull String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public User getCoach() {
        return coach;
    }

    public void setCoach(User coach) {
        this.coach = coach;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
