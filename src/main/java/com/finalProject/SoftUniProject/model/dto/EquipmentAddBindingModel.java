package com.finalProject.SoftUniProject.model.dto;

import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.UniqueElements;

public class EquipmentAddBindingModel {
    @Size(min = 3, message = "Equipment name must be at least 3 letters")
    private String name;

    @URL(message = "Please enter valid URL address!")
    private String url;

    private int weight;

    public String getName() {
        return name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public  String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public int getWeight() {
        return weight;
    }

    public void setWeight( int weight) {
        this.weight = weight;
    }
}
