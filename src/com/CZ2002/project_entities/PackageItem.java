package com.CZ2002.project_entities;
import com.CZ2002.project_enums.Type;
import java.util.ArrayList;

public class PackageItem extends MenuItem {
    // Attributes
    private ArrayList<AlaCarteItem> itemsServed;

    // Constructor
    public PackageItem(String name, String description, double price, ArrayList<AlaCarteItem> itemsServed){
        super(name, description, price);
        this.itemsServed = itemsServed;
    }

    // Mutators
    public void setItemsServed(ArrayList<AlaCarteItem> itemsServed) {
        this.itemsServed = itemsServed;
    }

    // Accessors
    public ArrayList<AlaCarteItem> getItemsServed() {
        return this.itemsServed;
    }

    // Methods
    public void addItem(AlaCarteItem item){
        itemsServed.add(item);
    }

    public void removeItem(AlaCarteItem item){
        itemsServed.remove(item);
    }

    public void printItems() {
        System.out.print("Package Includes: ");
        for (int i=0; i<this.itemsServed.size(); i++) {
            AlaCarteItem item = this.itemsServed.get(i);
            System.out.print(item.getName() + ", ");
        }
    }
}
