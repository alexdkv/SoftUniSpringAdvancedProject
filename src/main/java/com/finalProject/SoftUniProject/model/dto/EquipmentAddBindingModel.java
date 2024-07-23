package com.finalProject.SoftUniProject.model.dto;

import jakarta.validation.constraints.Size;

public class EquipmentAddBindingModel {
    @Size(min = 3, message = "Equipment name must be at least 3 letters")
    private String name;

    @org.hibernate.validator.constraints.URL(message = "Enter a valid photoUrl")
    private String photoUrl;

    private int weight;

    public String getName() {
        return name;
    }

    public void setName( String name) {
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

    public void setWeight( int weight) {
        this.weight = weight;
    }
}
