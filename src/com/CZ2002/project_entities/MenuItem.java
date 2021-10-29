package com.CZ2002.project_entities;

public class MenuItem {
    // Attributes
    private String name;
    private String description;
    private double price;

    // Constructors
    public MenuItem(String name, String description, double price){
        this.name = name;
        this.description = description;
        this.price = price;
    }

    // Mutators
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Accessors

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public double getPrice() {
        return this.price;
    }
}
