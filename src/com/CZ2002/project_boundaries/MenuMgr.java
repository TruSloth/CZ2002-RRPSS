package com.CZ2002.project_boundaries;
import com.CZ2002.project_entities.AlaCarteItem;
import com.CZ2002.project_entities.MenuItem;
import com.CZ2002.project_entities.PackageItem;
import com.CZ2002.project_enums.Type;

import java.util.ArrayList;
import java.util.Locale;

public class MenuMgr {
    // Attributes
    private ArrayList<MenuItem> menu; // TODO: Can we have a AlaCarteItem Menu and PackageItem Menu? Single menu seems a bit confusing.......

    // Constructor
    public MenuMgr(){};

    // Mutators
    public void setMenu(ArrayList<MenuItem> menu) {
        this.menu = menu;
    }

    // Accessors
    public ArrayList<MenuItem> getMenu() {
        return this.menu;
    }

    // Methods
    public MenuItem getItem(String name) {
        for (int i=0; i<this.menu.size(); i++){
            MenuItem item = this.menu.get(i);
            String itemName = item.getName();
            if (itemName.toLowerCase().equals(name.toLowerCase())){
                return item;
            }
        }
        return null;
    }

    public void addAlaCarteItem(String name, String description, double price, Type type){
        AlaCarteItem newItem = new AlaCarteItem(name, description, price, type);
        this.menu.add(newItem);
    }

    public void addPackageItem(String name, String description, double price, ArrayList<AlaCarteItem> items){
        PackageItem newItem = new PackageItem(name, description, price, items);
        this.menu.add(newItem);
    }

    public void updateItem(String name, String newName, String description, double price){
        for (int i=0; i<this.menu.size(); i++){
            MenuItem item = this.menu.get(i);
            String itemName = item.getName();
            if (itemName.toLowerCase().equals(name.toLowerCase())){
                item.setName(newName);
                item.setDescription(description);
                item.setPrice(price);
            }
        }
    }

    public void removeItem(String name){
        for (int i=0; i<this.menu.size(); i++){
            MenuItem item = this.menu.get(i);
            String itemName = item.getName();
            if (itemName.toLowerCase().equals(name.toLowerCase())){
                this.menu.remove(item);
                break;
            }
        }
    }

    public void addItemToPackage(String packageName, String alaCartItemName){
        // TODO: HOW DOES THIS WORK?
    }

    public void removeItemFromPackage(String packageName, String alaCartItemName){
        // TODO: HOW DOES THIS WORK?
    }

    public void printMenu(){
        for (int i=0; i<this.menu.size(); i++){
            MenuItem item = this.menu.get(i);
            String itemName = item.getName();
            String itemDescription = item.getDescription();
            double itemPrice = item.getPrice();
            System.out.println(itemName + "\t" + itemDescription + "\t$" + itemPrice);
            // TODO: Huh?
        }
    }
}
