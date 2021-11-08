package com.CZ2002.project_commands;

import java.util.ArrayList;

import com.CZ2002.project_boundaries.MenuManager;
import com.CZ2002.project_entities.AlaCarteItem;

/**
 * This class completes the 'Add a package item' action.
 */
public class AddPackageCommand {
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
	public AddPackageCommand(MenuManager menuManger, String name, double price,
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
	public void execute() {
		menuManager.addPackageItem(name, des, price, componentList);
	}

}