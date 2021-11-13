package com.CZ2002.project_commands.menu;

import java.util.ArrayList;

import com.CZ2002.project_entities.MenuItem;
import com.CZ2002.project_managers.MenuManager;

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
