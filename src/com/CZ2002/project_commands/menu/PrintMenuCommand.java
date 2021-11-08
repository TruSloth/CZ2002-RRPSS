package com.CZ2002.project_commands.menu;

import java.util.ArrayList;

import com.CZ2002.project_boundaries.MenuManager;
import com.CZ2002.project_entities.MenuItem;

/**
 * This class completes the 'print menu' action.
 */
public class PrintMenuCommand {
	private MenuManager menuManager;
	
	 /**
     * Constructor for {@code execute} to successfully complete.
     */
	public PrintMenuCommand(MenuManager menuManager) {
		this.menuManager = menuManager;
	}
	
	/**
     * Completes the 'print menu' action.
     */
	public ArrayList<MenuItem> execute() {
		return menuManager.getMenu();
	}

}
