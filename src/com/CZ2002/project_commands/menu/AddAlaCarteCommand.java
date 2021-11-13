package com.CZ2002.project_commands.menu;

import com.CZ2002.project_boundaries.MenuManager;
import com.CZ2002.project_enums.Type;
import com.CZ2002.project_exceptions.InvalidMenuItemException;
import com.CZ2002.project_interfaces.ICommand;


/**
 * This class completes the 'Add an ala carte item' action.
 */
public class AddAlaCarteCommand implements ICommand<Void, InvalidMenuItemException> {
    private MenuManager menuManager;
    private String name;
    private double price;
    private String des;
    private Type type;

    /**
     * Constructor that accepts the necessary parameters for {@code execute} to successfully complete.
     *
     * @param menuManager  the reference to the Restaurant's {@link MenuManager}
     * @param name  the name of AlaCarteItem
     * @param price  the price of AlaCarteItem
     * @param des  the description of AlaCarteItem
     */
    public AddAlaCarteCommand(MenuManager menuManager, String name, double price,
                              String des,  Type type) {
        this.menuManager = menuManager;
        this.name = name;
        this.price = price;
        this.des = des;
        this.type =type;
    }

    /**
     * Completes the 'Add an ala carte item' action.
     */
    public Void execute() throws InvalidMenuItemException {
        if(menuManager.getItem(name) != null) {
            throw new InvalidMenuItemException("Menu Item of the Same Name Already Exists!");
        }
        menuManager.addAlaCarteItem(name, des, price, type);
        return null;
    }
}
