package com.CZ2002.commands.menu;

import java.util.ArrayList;

import com.CZ2002.boundaries.MenuManager;
import com.CZ2002.entities.AlaCarteItem;
import com.CZ2002.exceptions.InvalidMenuItemException;
import com.CZ2002.interfaces.ICommand;

/**
 * This class completes the 'Add a package item' action.
 */
public class AddPackageCommand implements ICommand<Void, InvalidMenuItemException> {
    private MenuManager menuManager;
    private String name;
    private double price;
    private String des;
    private ArrayList<AlaCarteItem> componentList = new ArrayList<>();

    /**
     * Constructor that accepts the necessary parameters for {@code execute} to successfully complete.
     *
     * @param menuManager  the reference to the Restaurant's {@link MenuManager}
     * @param name  the name of PackageItem
     * @param price  the price of PackageItem
     * @param des  the description PackageItem
     * @param componentList	list of items to be added inside PackgeItem
     */
    public AddPackageCommand(MenuManager menuManager, String name, double price,
                             String des,  ArrayList<AlaCarteItem> componentList) {
        this.menuManager = menuManager;
        this.name = name;
        this.price = price;
        this.des = des;
        this.componentList =componentList;
    }

    /**
     * Completes the 'Add an package item' action.
     */ 
    public Void execute() throws InvalidMenuItemException {
        if(menuManager.getItem(name) != null) {
            throw new InvalidMenuItemException("Menu Item of the same name already exists");
        }
        menuManager.addPackageItem(name, des, price, componentList);
        return null;
    }
}
