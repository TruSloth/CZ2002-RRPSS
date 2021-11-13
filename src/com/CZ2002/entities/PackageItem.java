package com.CZ2002.entities;
import java.util.ArrayList;

/**
 * The {@code PackageItem} class represents a package menu item.
 * A package item must be specified by the name, description, price and the
 * list of items in the package.
 * <p>
 * The {@code PackageItem} class is designed to only hold all data related to
 * a package item and should not implement any logic in relation to package item.
 */
public class PackageItem extends MenuItem {
    private ArrayList<AlaCarteItem> itemsServed;

    /** Creates an AlaCarteItem with the arguments provided
     * @param name name of the PackageItem
     * @param description a string of text to describe the PackageItem
     * @param price the price of the item in terms of dollars
     * @param list an ArrayList of AlaCarteItem that are included inside the PackageItem
     */
    public PackageItem(String name, String description, double price, ArrayList<AlaCarteItem> list) {
        super();
        this.setName(name);
        this.setDescription(description);
        this.setPrice(price);
        itemsServed = new ArrayList<>(list);
    }

    /** Gets the ArrayList of AlaCarteItem
     * @return the ArrayList of AlaCarteItem
     */
    public ArrayList<AlaCarteItem> getList(){
        return itemsServed;
    }

    /** Adds an AlaCarteItem to the current ArrayList of AlaCarteItem
     * @param item an AlaCarteItem object to be added to the current ArrayList of AlaCarteItem
     */
    public void addItem(AlaCarteItem item) {
        itemsServed.add(item);
    }

    /** Removes an AlaCarteItem from the current ArrayList of AlaCarteItem
     * @param item an AlaCarteItem object to be removed from the current ArrayList of AlaCarteItem
     */
    public void removeItem(AlaCarteItem item) {
        itemsServed.remove(item);
    }


}