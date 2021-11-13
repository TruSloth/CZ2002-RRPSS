package com.CZ2002.commands.menu;

import java.util.ArrayList;

import com.CZ2002.boundaries.MenuManager;
import com.CZ2002.entities.MenuItem;

/**
 * This class completes the 'print menu' action.
 */
public class GetMenuCommand {
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
