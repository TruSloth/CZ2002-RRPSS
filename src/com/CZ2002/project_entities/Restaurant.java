package com.CZ2002.project_entities;

/**
 * The {@code Restaurant} class represents the restaurant.
 * The restaurant consists of max number of tables (40), the name of the restaurant,
 * max capacity and the number of staffs employed in the restaurant.
 * <p>
 * The {@code Restaurant} class is designed to only hold all data related to
 * the one and only restaurant and should not implement any logic in relation to restaurant.
 */
public class Restaurant {
    private final int NUM_OF_TABLES = 40; // TODO: CHANGE BACK TO 40
    private String name;
    private int maxCapacity;
    private int numStaff;

    /**
     * Constructs a new restaurant that represents... the restaurant!
     * @param name String representing the name of the restaurant
     * @param numStaff Number representing the number of staffs employed by the restaurant
     * @param maxCapacity Number representing the number of capacity in the restaurant
     * */
    public Restaurant(String name, int maxCapacity, int numStaff) {
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.numStaff = numStaff;
    }

    /**
     * Gets the name of the restaurant
     * @return name of the restaurant
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the max capacity of the restaurant
     * @return number of capacity in the restaurant
     */
    public int getMaxCapacity() {
        return this.maxCapacity;
    }

    /**
     * Gets the number of tables in the restaurant. This is a {readonly} attribute.
     * @return number of tables in the restaurant
     */
    public int getNumOfTables() {
        return NUM_OF_TABLES;
    }

    /**
     * Gets the name of the restaurant
     * @return name of the restaurant
     */
    public int getNumStaff() {
        return numStaff;
    }

    /**
     * Sets the Integer representation of the number of capacity of the restaurant.
     * @param maxCapacity the max capacity of the restaurant
     */
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    /**
     * Updates the Integer representation of the number of staffs employed by the restaurant.
     * @param numStaff the max capacity of the restaurant
     */
    public void setNumStaff(int numStaff) {
        this.numStaff = numStaff;
    }

    /**
     * Updates the String representation of the restaurant's name.
     * @param name the max capacity of the restaurant
     */
    public void setName(String name) {
        this.name = name;
    }
}