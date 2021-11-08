package com.CZ2002.project_commands;

import com.CZ2002.project_boundaries.MenuManager;

/**
 * This class completes the 'Remove item' action.
 */
public class RemoveMenuItemCommand {
	private MenuManager menuManager;
	private String name;
	
	/**
     * Constructor that accepts the necessary parameters for {@code execute} to successfully complete.
     *
     * @param menuManager  the reference to the Restaurant's {@link MenuManager}
     * @param name  the name of MenuItem
     */
	public RemoveMenuItemCommand(MenuManager menuManger, String name) { 
		this.menuManager = menuManager;
		this.name = name;
	}
	
	/**
     * Completes the 'Remove item' action.
     */
	public void execute() {
		menuManager.removeItem(name);
	}
}
