package com.CZ2002.project_entities;

/**
 * The {@code MenuItem} class represents a package menu item.
 * A menu item must be specified by the name, description and price.
 * <p>
 * The {@code PackageItem} class is designed to only hold all data related to
 * a menu item and should not implement any logic in relation to menu item.
 */
public class MenuItem extends RestaurantEntity {
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
     * @return A String object of the items' name
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
     * @return A String of the item's description
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
     * @return A double value of the item's price
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