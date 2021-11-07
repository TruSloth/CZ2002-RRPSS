package com.CZ2002.project_entities;
import java.util.ArrayList;

/**
 * An Entity class representing a unique PackageItem consisting of AlaCarteItems
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
        itemsServed = new ArrayList<AlaCarteItem>(list);
    }

    /**
     * Prints the name of the AlaCarteItem included in one line
     */
    public void printItems() {
        System.out.print("Includes: ");
        for (AlaCarteItem item: itemsServed) {
            System.out.print(item.getName() + ", ");
        }
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