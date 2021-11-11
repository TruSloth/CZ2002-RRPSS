package com.CZ2002.project_commands.menu;


import com.CZ2002.project_boundaries.MenuManager;
import com.CZ2002.project_exceptions.InvalidMenuItemException;
import com.CZ2002.project_interfaces.ICommand;

/**
 * This class completes the 'Update item' action.
 */
public class UpdateMenuItemCommand implements ICommand<Void, InvalidMenuItemException>{
    private MenuManager menuManager;
    private String name;
    private String subName;
    private double price;
    private String des;

    /**
     * Constructor that accepts the necessary parameters for {@code execute} to successfully complete.
     *
     * @param menuManager  the reference to the Restaurant's {@link MenuManager}
     * @param name  the name of MenuItem
     * @param subName	new name of MenuItem
     * @param price  the price of MenuItem
     * @param des  the description MenuItem

     */
    public UpdateMenuItemCommand(MenuManager menuManager, String name, String subName, double price,
                                 String des) {
        this.menuManager = menuManager;
        this.name = name;
        this.subName = subName;
        this.price = price;
        this.des = des;
    }

    /**
     * Completes the 'Update item' action.
     */
    public Void execute() throws InvalidMenuItemException {
    	try {
    		menuManager.updateItem(name, subName, des, price);
    		if(menuManager.getItem(name)==null) {
    			throw new InvalidMenuItemException("Requested Menu Item does not exist");
    		}
    	} finally{
    		
    	}
    	
    	return null;
        
    }
}
