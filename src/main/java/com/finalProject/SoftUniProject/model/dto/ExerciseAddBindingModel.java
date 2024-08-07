package com.finalProject.SoftUniProject.model.dto;

import com.finalProject.SoftUniProject.model.entity.Equipment;
import jakarta.validation.constraints.Size;

public class ExerciseAddBindingModel {
    @Size(min = 3, max = 50, message = "Exercise name must be between 3 and 50 characters!")
    private String name;

    @Size(min = 3, message = "Description must be more than 3 characters!")
    private String description;

    private int intensity;

    @org.hibernate.validator.constraints.URL(message = "Please enter valid image photoUrl!")
    private String photoUrl;

    private Equipment equipment;

    public  String getName() {
        return name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public  String getDescription() {
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

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
