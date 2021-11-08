package com.CZ2002.project_commands;

import com.CZ2002.project_boundaries.MenuManager;
import com.CZ2002.project_enums.Type;


/**
 * This class completes the 'Add an ala carte item' action.
 */
public class AddAlaCarteCommand {
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
	public AddAlaCarteCommand(MenuManager menuManger, String name, double price,
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
	public void execute() {
		menuManager.addAlaCarteItem(name, des, price, type);	
	}
}
