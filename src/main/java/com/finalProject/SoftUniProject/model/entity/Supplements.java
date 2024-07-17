package com.finalProject.SoftUniProject.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "supplements")
public class Supplements extends BaseEntity{
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double price;

    public Supplements() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
