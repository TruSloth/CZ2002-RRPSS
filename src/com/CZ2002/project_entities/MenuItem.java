package com.CZ2002.project_entities;

/**
 * An Entity class representing a MenuItem
 */

public class MenuItem {
    private String name;
    private String description;
    private double price;

    /** Creates a MenuItem object
     */
    public MenuItem() {
        name = "None";
        description = "None";
        price = 0.00;
    }

    /** Gets the name of the item
     * @returns A String object of the items' name
     */
    public String getName() {
        return name;
    }

    /** Sets the name of the item
     * @param name the new name to be assigned to the item
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Gets the description of the item
     * @returns A String of the item's description
     */
    public String getDescription() {
        return description;
    }

    /** Sets the description of the item
     * @param d the new description to be assigned to the item
     */
    public void setDescription(String d) {
        this.description = d;
    }

    /** Gets the price of the item
     * @returns A double value of the item's price
     */
    public double getPrice() {
        return price;
    }

    /** Sets the price of the item
     * @param p the new price to be assigned to the person
     */
    public void setPrice(double p) {
        this.price = p;
    }
}