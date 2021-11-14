package com.CZ2002.commands.menu;

import java.util.ArrayList;

import com.CZ2002.entities.MenuItem;
import com.CZ2002.exceptions.InvalidAddItemOrderException;
import com.CZ2002.interfaces.ICommand;
import com.CZ2002.managers.MenuManager;

/**
 * This class completes the 'print menu' action.
 */
public class GetMenuCommand implements ICommand<ArrayList<MenuItem>, InvalidAddItemOrderException> {
	private MenuManager menuManager;
	
	 /**
     * Constructor for {@code execute} to successfully complete.
     */
	public GetMenuCommand(MenuManager menuManager) {
		this.menuManager = menuManager;
	}
	
	/**
     * Completes the 'print menu' action.
     */
	public ArrayList<MenuItem> execute() {
		return menuManager.getMenu();
	}

}
