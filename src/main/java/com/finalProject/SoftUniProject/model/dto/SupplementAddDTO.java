package com.finalProject.SoftUniProject.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public class SupplementAddDTO {
    @Size(min = 3, message = "Supplement name must be at least 3 letters")
    private String name;

    @Size(min = 3, message = "Supplement description must be at least 3 letters")
    private String description;

    private Double caloriesPerServing;

    @URL(message = "Enter valid url")
    private String photoUrl;

    public Double getCaloriesPerServing() {
        return caloriesPerServing;
    }

    public void setCaloriesPerServing(Double caloriesPerServing) {
        this.caloriesPerServing = caloriesPerServing;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
