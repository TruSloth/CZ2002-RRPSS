package com.CZ2002.project_displays;
import com.CZ2002.project_boundaries.MenuManager;
import com.CZ2002.project_boundaries.RestaurantManager;
import com.CZ2002.project_commands.menu.*;
import com.CZ2002.project_entities.AlaCarteItem;
import com.CZ2002.project_entities.MenuItem;
import com.CZ2002.project_entities.PackageItem;
import com.CZ2002.project_utils.MenuBuilder;
import com.CZ2002.project_enums.MenuView;
import com.CZ2002.project_enums.Type;
import com.CZ2002.project_exceptions.InvalidMenuItemException;
import com.CZ2002.project_interfaces.ICommand;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * A boundary class that takes in inputs from user to interact with MenuItem
 */
public class MenuConsole extends ConsoleDisplay{
    private AddAlaCarteCommand addAlaCarteCommand;
    private AddPackageCommand addPackageCommand;
    private ICommand<Void, InvalidMenuItemException> updateMenuItemCommand;
    private ICommand<Void, InvalidMenuItemException> removeMenuItemCommand;
    private ICommand<Void, InvalidMenuItemException> addToPackageCommand;
    private ICommand<Void, InvalidMenuItemException> removeFromPackageCommand;
    private GetMenuCommand getMenuCommand;

    /**
     * function to print out the console
     */
    public MenuConsole(RestaurantManager restaurantManager, Scanner sc) {
        super.sc = sc;
        super.mainManager = restaurantManager;
    }

    /**
     * Formats and outputs the possible actions that can be taken on this {@code MenuConsole}.
     * @see ConsoleDisplay
     */
    @Override
    public int displayConsoleOptions() {
        String[] options = new String[] {
                "Add an ala carte item",
                "Add a package",
                "Update an item",
                "Remove an item",
                "Add an ala carte item to a package",
                "Remove an ala carte item from a package",
                "Print menu",
                "Back"

        };

        String title = "Menu Items";

        System.out.println(MenuBuilder.buildMenu(title, options));

        return options.length;
    }

    /**
     * Formats and outputs the possible actions that can be taken for choosing Type.
     * @see ConsoleDisplay
     */
    public int displayTypeOptions() {
        String[] options = new String[] {
                "Main",
                "Drink",
                "Dessert",
        };

        String title = "AlaCarte Item Types";

        System.out.println(MenuBuilder.buildMenu(title, options));

        return options.length;
    }

    public int displayMenu(ArrayList<MenuItem> menu) {
        for (MenuItem item : menu) {
        	if (item instanceof AlaCarteItem ) {
        		String title = item.getName();
                String[] optionHeaders = {"Price", "Description","Category"};
                String type ="";
                if (((AlaCarteItem) item).getType()== Type.MAIN) {
                	type = "Main";
                } else if (((AlaCarteItem) item).getType()== Type.DESSERT) {
                	type = "Dessert";
                } else {
                	type = "Drink";
                }
                String[] options = {String.format("%.2f", item.getPrice()), item.getDescription(),type};
                System.out.println(MenuBuilder.buildMenu(title, optionHeaders, options, 40));
        	} else {
        		PackageItem dummyPack = (PackageItem)item;
        		String[] optionHeaders = {"Price", "Description","Includes"};
        		String title = item.getName();
        		String components ="";
        		for (AlaCarteItem component: dummyPack.getList()) {
        			components+= component.getName() + ", ";
        		}
                String[] options = {String.format("%.2f", item.getPrice()), item.getDescription(),components};
                System.out.println(MenuBuilder.buildMenu(title, optionHeaders, options, 40));
        	}  
        }

        return menu.size();
    }

    @Override
    /**
     * Accepts input from the user surrounding the possible actions the user can take
     * in relation to {@link MenuItem,AlaCarteItem, PackageItem} instances.
     */
    public MenuView handleConsoleOptions() {
        MenuView view = MenuView.MENU_ITEMS;
        int choice, subChoice, packageSize,i;
        String name,subName, des, dummy;
        double price;
        Type type;
        ArrayList<AlaCarteItem> componentList = new ArrayList<>();
        
        System.out.println("Enter choice: ");
        choice = sc.nextInt();
        dummy= sc.nextLine();
        
        switch (choice) {
        	case 1: 
        		//Add Ala Carte Item
                System.out.println("Enter ala carte item's name:");
                name = sc.nextLine();

                System.out.println("Enter ala carte item's price:");
                price = sc.nextDouble();
                dummy= sc.nextLine();

                System.out.println("Enter ala carte item's description:");
                des = sc.nextLine();

                displayTypeOptions();
                subChoice = sc.nextInt();
                if (subChoice == 1) {
                    type = Type.MAIN;
                } else if (subChoice == 2) {
                    type = Type.DRINK;
                } else {
                    type = Type.DESSERT;
                }

                addAlaCarteCommand = new AddAlaCarteCommand(
                        mainManager.getSubManager("menuManager", MenuManager.class),
                        name,price,des,type);
                addAlaCarteCommand.execute();

                //item successfully added
                System.out.println("Ala carte item added!");
                view = MenuView.MENU_ITEMS;
                break;
        	case 2:
        		//Add Package Item
                System.out.println("Enter package name:");
                name = sc.nextLine();

                System.out.println("Enter package price:");
                price = sc.nextDouble();
                dummy= sc.nextLine();

                System.out.println("Enter package description: ");
                des = sc.nextLine();

                System.out.println("Enter no of items in package: ");
                packageSize = sc.nextInt();
                dummy= sc.nextLine();
                for (i= 1; i<= packageSize; i++) {
                    System.out.println("Enter ala carte item no " + i +  " name:");
                    subName= sc.nextLine();                    
                    while (true) {
                        if (!(mainManager.getSubManager("menuManager", MenuManager.class).getItem(subName) instanceof MenuItem) ) {
                            System.out.println("Item does not exist! Try again: ");
                            subName= sc.nextLine();
                        } else {
                            break;
                        }
                    }
                    componentList.add((AlaCarteItem)mainManager.getSubManager("menuManager", MenuManager.class).getItem(subName));                  
                }

                addPackageCommand = new AddPackageCommand(
                        mainManager.getSubManager("menuManager", MenuManager.class),
                        name,price,des,componentList);
                addPackageCommand.execute();

                componentList.clear();

                //item successfully added
                System.out.println("Package added!");
                System.out.println();
         
                
                view = MenuView.MENU_ITEMS;
                break;
        	case 3:
        		//Update Menu Item
                System.out.println("Enter item to be changed:");
                name = sc.nextLine();
              
                System.out.println("Enter new name: ");
                subName = sc.nextLine();

                System.out.println("Enter new description: ");
                //System.out.println("(Current description: " + mainManager.getSubManager("menuManager", MenuManager.class).getItem(name).getDescription() +  ")");
                des = sc.nextLine();

                System.out.println("Enter new price:");
                //System.out.println("(Current price: " + mainManager.getSubManager("menuManager", MenuManager.class).getItem(name).getPrice() +  ")");
                price = sc.nextDouble();
                dummy= sc.nextLine();

                updateMenuItemCommand = new UpdateMenuItemCommand(
                        mainManager.getSubManager("menuManager", MenuManager.class),
                        name,subName,price,des);
                try {
                	updateMenuItemCommand.execute();
                	System.out.println(name + " was changed!");
                	System.out.println();
                } catch (InvalidMenuItemException | ParseException e) {
					System.out.println(e.getMessage());
				}   		
                view = MenuView.MENU_ITEMS;
                break;
        	case 4:
        		//Remove Menu Item
                System.out.println("Enter item to be removed:");
                name = sc.nextLine();
                removeMenuItemCommand = new RemoveMenuItemCommand(mainManager.getSubManager("menuManager", MenuManager.class), name);
				try {
					removeMenuItemCommand.execute();
					System.out.println(name + " was removed!");
	                System.out.println();
				} catch (InvalidMenuItemException | ParseException e) {
					System.out.println(e.getMessage());
				}
				view = MenuView.MENU_ITEMS;
                break;
        	case 5:
        		//Add Ala carte to package
                System.out.println("Enter package name:");
                name = sc.nextLine();
                
                System.out.println("Enter name of ala carte to be added:");
                subName = sc.nextLine();
                
                addToPackageCommand = new AddToPackageCommand(mainManager.getSubManager("menuManager", MenuManager.class), name, subName);
				try {
					addToPackageCommand.execute();
					System.out.println( subName + " was added to " + name);
	                System.out.println();
				} catch (InvalidMenuItemException | ParseException e) {
					System.out.println(e.getMessage());
				}

                view = MenuView.MENU_ITEMS;
                break;
        	case 6:
        		//Remove ala carte from package
                System.out.println("Enter package name:");
                name = sc.nextLine();

                System.out.println("Enter name of ala carte to be removed:");
                subName = sc.nextLine();
                removeFromPackageCommand = new RemoveFromPackageCommand(mainManager.getSubManager("menuManager", MenuManager.class), name, subName);
				try {
					removeFromPackageCommand.execute();
					 System.out.println( subName + " was removed from " + name);
		             System.out.println();
				} catch (InvalidMenuItemException | ParseException e) {
					System.out.println(e.getMessage());
				}            
                view = MenuView.MENU_ITEMS;
                break;
        	case 7:
        		//Print Menu
        		getMenuCommand = new GetMenuCommand(mainManager.getSubManager("menuManager", MenuManager.class));
                ArrayList<MenuItem> menu = getMenuCommand.execute();
                displayMenu(menu);
                view = MenuView.MENU_ITEMS;
                break;
        	case 8: 
        		// Back
                view = MenuView.PREVIOUS_MENU;
                break;
        		
        }
        return view;

    }

}
