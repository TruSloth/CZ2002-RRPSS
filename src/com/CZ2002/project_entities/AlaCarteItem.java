package com.CZ2002.project_entities;
import com.CZ2002.project_enums.Type;

/**
 * The {@code AlaCarteItem} class represents a ala carte menu item.
 * A ala carte item must be specified by the name, description, price and type of item.
 * <p>
 * The {@code AlaCarteItem} class is designed to only hold all data related to
 * a ala carte item and should not implement any logic in relation to ala carte item.
 */
public class AlaCarteItem extends MenuItem {
    private Type type;

    /** Creates an AlaCarteItem with the arguments provided
     * @param name name of the AlaCarteItem
     * @param description a string of text to describe the AlaCarteItem
     * @param price the price of the item in terms of dollars
     * @param type one of three types of AlaCarteItem
     */
    public AlaCarteItem(String name, String description, double price, Type type) {
        super();
        this.setName(name);
        this.setDescription(description);
        this.setPrice(price);
        this.type = type;

    }

    /** Gets the type of the AlaCarteItem.
     * @returns A Type enum of the AlaCarteItem
     */
    public Type getType() {
        return this.type;
    }

    /** Sets the type of the AlaCarteItem.
     * @param type one of three types of AlaCarteItem
     */
    public void setType(Type type) {
        this.type = type;
    }

}