package com.CZ2002.project_boundaries;
import com.CZ2002.project_entities.AlaCarteItem;
import com.CZ2002.project_entities.MenuItem;
import com.CZ2002.project_entities.PackageItem;
import com.CZ2002.project_enums.Type;

import java.util.ArrayList;

public class MenuMgr {
    // Attributes
    private ArrayList<MenuItem> menu;

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
            if (itemName.equalsIgnoreCase(name)){
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
            if (itemName.equalsIgnoreCase(name)){
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
            if (itemName.equalsIgnoreCase(name)){
                this.menu.remove(item);
                break;
            }
        }
    }

    public void addItemToPackage(String packageName, String alaCartItemName){
        AlaCarteItem newItem = new AlaCarteItem("Placeholder", "Placeholder", 0.00, Type.MAINS);
        for (int i=0; i<this.menu.size(); i++){
            MenuItem item = this.menu.get(i);
            String itemName = item.getName();
            if (itemName.equalsIgnoreCase(alaCartItemName)){
                newItem = (AlaCarteItem) item;
                break;
            }
        }
        for (int i=0; i<this.menu.size(); i++){
            MenuItem item = this.menu.get(i);
            String itemName = item.getName();
            if (packageName.equalsIgnoreCase(itemName)){
                PackageItem newPackage = (PackageItem) item;
                newPackage.addItem(newItem);
                this.menu.set(i, newPackage);
                break;
            }
        }
    }

    public void removeItemFromPackage(String packageName, String alaCartItemName){
        AlaCarteItem newItem = new AlaCarteItem("Placeholder", "Placeholder", 0.00, Type.MAINS);
        for (int i=0; i<this.menu.size(); i++){
            MenuItem item = this.menu.get(i);
            String itemName = item.getName();
            if (itemName.equalsIgnoreCase(alaCartItemName)){
                newItem = (AlaCarteItem) item;
                break;
            }
        }
        for (int i=0; i<this.menu.size(); i++){
            MenuItem item = this.menu.get(i);
            String itemName = item.getName();
            if (packageName.equalsIgnoreCase(itemName)){
                PackageItem newPackage = (PackageItem) item;
                newPackage.removeItem(newItem);
                this.menu.set(i, newPackage);
                break;
            }

        }
    }

    public void printMenu(){  // TODO: DECISION TO MOVE TO CONSOLE?
        for (int i=0; i<this.menu.size(); i++){
            MenuItem item = this.menu.get(i);
            System.out.println("Name: " + item.getName());
            System.out.println("Description: " + item.getDescription());
            try {
                AlaCarteItem dummyAla = (AlaCarteItem)item;
                System.out.println("Type: " + dummyAla.getType());
            }
            catch (Exception e){
                PackageItem dummyPack = (PackageItem)item;
                dummyPack.printItems();
                System.out.println("");
            }
            finally {
                System.out.println("Price: " + item.getPrice());
                System.out.println("");
            }
        }
    }
}
