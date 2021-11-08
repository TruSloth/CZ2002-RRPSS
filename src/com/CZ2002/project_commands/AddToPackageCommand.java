package com.CZ2002.project_commands;

import com.CZ2002.project_boundaries.MenuManager;

/**
* This class completes the 'Add ala carte item to package' action.
*/
public class AddToPackageCommand {
	private MenuManager menuManager;
	private String name;
	private String subname;
	
	/**
    * Constructor that accepts the necessary parameters for {@code execute} to successfully complete.
    *
    * @param menuManager  the reference to the Restaurant's {@link MenuManager}
    * @param name  the name of PackgeItem
    * @param subName the name of AlaCarteItem to be added
    */
	public AddToPackageCommand(MenuManager menuManger, String name,String subName) { 
		this.menuManager = menuManager;
		this.name = name;
		this.subname = subName;
	}
	
	/**
    * Completes the 'Add ala carte item to package' action.
    */
	public void execute() {
		menuManager.addItemToPackage(name, subname);
	}
}
