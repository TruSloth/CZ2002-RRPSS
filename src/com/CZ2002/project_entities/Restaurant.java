package com.CZ2002.project_entities;

public class Restaurant {
    private final int NUM_OF_TABLES = 40; // Num of tables in restaurant
    private String name;
    private int maxCapacity; // Should be a final as well
    private int numStaff;

    public Restaurant(String name, int maxCapacity, int numStaff) {
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.numStaff = numStaff;
    }

    // Default getters & setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getNumOfTables() {
        return NUM_OF_TABLES;
    }

    public int getNumStaff() {
        return numStaff;
    }

    public void setNumStaff(int numStaff) {
        this.numStaff = numStaff;
    }
}