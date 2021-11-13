package com.CZ2002.project_managers;

import java.util.ArrayList;

import com.CZ2002.project_entities.AlaCarteItem;
import com.CZ2002.project_entities.MenuItem;
import com.CZ2002.project_entities.PackageItem;
import com.CZ2002.project_enums.Type;

/**
 * A Control class to execute the logics of MenuItem objects 
 * MenuManager will keep track of all MenuItem objects created
 */
public class MenuManager extends Manager<MenuItem>{
  

    /** Gets the MenuItem object based on its name
     * returns null if no MenuItem with called name exists
     * returns A MenuItem object with called name
     */
    public MenuManager() {
    	entities = new ArrayList<>();
    }
    
    public MenuItem getItem(String name) {
        for (MenuItem item: entities) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    /** Creates and stores an AlaCarteItem inside menu
     * @param name name of the AlaCarteItem
     * @param description a string of text to describe the AlaCarteItem
     * @param price the price of the item in terms of dollars
     * @param type one of three types of AlaCarteItem
     */
    public void addAlaCarteItem(String name, String description, double price, Type type) {
    	entities.add(new AlaCarteItem(name,description,price,type));
    }

    /** Creates and stores a PackageItem inside menu
     * @param name name of the PackageItem
     * @param description a string of text to describe the PackageItem
     * @param price the price of the item in terms of dollars
     * @param list an ArrayList of AlaCarteItem that are included inside the PackageItem
     */
    public void addPackageItem(String name, String description, double price, ArrayList<AlaCarteItem> list) {
    	entities.add(new PackageItem(name,description,price,list));
    }

    /** Changes the name, description and price of any item on the menu
     * @param name name of the MenuItem that is to be updated
     * @param newName new name assigned to MenuItem
     * @param des new description assigned to MenuItem
     * @param price new price assigned to MenuItem
     */
    public void updateItem(String name,String newName, String des, double price) {
        for (MenuItem item:entities) {
            if (item.getName().equals(name)) {	//possible error handling
                item.setName(newName);
                item.setDescription(des);
                item.setPrice(price);
            }
        }
    }

    /** Removes the MenuItem of the passed name
     * @param name name of the MenuItem to be removed
     */
    public void removeItem(String name) {
        for (MenuItem item: entities) {
            if (item.getName().equals(name)) {
            	entities.remove(item);
                break;
            }
        }

    }

    /** Adds an AlaCarteItem to a PackageItem
     * @param pName name of the PackgeItem where AlaCarteItem needs to be added
     * @param aName name of the AlaCarteItem that needs to be added
     */
    public void addItemToPackage(String pName, String aName) {
        AlaCarteItem newItem = null;
        for (MenuItem aItem: entities) {
            if (aItem.getName().equals(aName) && aItem instanceof AlaCarteItem) {
                newItem = (AlaCarteItem)aItem;	//ala carte item assigned
            }
        }
        if (newItem != null) {
        	for (MenuItem item: entities) {
            if (item.getName().equals(pName) && item instanceof PackageItem newPackage) {
                newPackage.addItem(newItem);
            }
        	}
        }
        
    }

    /** Removes an AlaCarteItem from a PackageItem
     * @param pName name of the PackgeItem where AlaCarteItem needs to be removed from
     * @param aName name of the AlaCarteItem that needs to be removed
     */
    public void removeItemFromPackage(String pName, String aName) {
        AlaCarteItem newItem = null;
        for (MenuItem aItem: entities) {
            if (aItem.getName().equals(aName) && aItem instanceof AlaCarteItem) {
                newItem = (AlaCarteItem)aItem;	//ala carte item assigned
            }
        }
        for (MenuItem item: entities) {
            if (item.getName().equals(pName) && item instanceof PackageItem newPackage) {
                newPackage.removeItem(newItem);
            }
        }
    }

    /** Returns the menu
     * @return	the ArrayList of MenuItem in menu
     */
    public ArrayList<MenuItem> getMenu(){
    	return (ArrayList<MenuItem>) entities;
    }
}
