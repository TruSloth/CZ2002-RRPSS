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
    private GetMenuCommand getMenuCommand;

    /**
     * Function to print out the console
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
                "Add New Ala Carte Item",
                "Add New Package",
                "Update Item/Package",
                "Remove Ala Carte Item",
                "Add Ala Carte Item To Package",
                "Remove Ala Carte Item From Package",
                "Print Menu",
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

    /**
     * Function to print out the menu
     */
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
        		StringBuilder components = new StringBuilder();
        		for (AlaCarteItem component: dummyPack.getList()) {
        			components.append(component.getName()).append(", ");
        		}
                String[] options = {String.format("%.2f", item.getPrice()), item.getDescription(), components.toString()};
                System.out.println(MenuBuilder.buildMenu(title, optionHeaders, options, 40));
        	}  
        }
        return menu.size();
    }

    /**
     * Accepts input from the user surrounding the possible actions the user can take
     * in relation to {@link MenuItem,AlaCarteItem, PackageItem} instances.
     */
    @Override
    public MenuView handleConsoleOptions() {
        MenuView view = MenuView.MENU_ITEMS;
        int choice, subChoice, packageSize,i;
        String name,subName, des;
        double price;
        Type type;
        ArrayList<AlaCarteItem> componentList = new ArrayList<>();
        
        System.out.println("Enter Choice: ");
        choice = sc.nextInt();
        sc.nextLine(); // throw away \n in buffer

        switch (choice) {
            case 1 -> {
                //Add Ala Carte Item
                System.out.println("Enter Ala Carte Item Name: ");
                name = sc.nextLine();
                System.out.println("Enter Ala Carte Item Price: ");
                price = sc.nextDouble();
                sc.nextLine(); // throw away \n in buffer
                System.out.println("Enter Ala Carte Item Description: ");
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
                ICommand<Void, InvalidMenuItemException> addAlaCarteCommand = new AddAlaCarteCommand(
                        mainManager.getSubManager("menuManager", MenuManager.class),
                        name, price, des, type);
                try {
                    addAlaCarteCommand.execute();
                    System.out.println("New Ala Carte Item Added!");
                    System.out.println();
                } catch (InvalidMenuItemException | ParseException e) {
                    System.out.println(e.getMessage());
                }
                view = MenuView.MENU_ITEMS;
            }
            case 2 -> {
                //Add Package Item
                System.out.println("Enter Package Name: ");
                name = sc.nextLine();
                System.out.println("Enter Package Price: ");
                price = sc.nextDouble();
                sc.nextLine(); // throw away \n in buffer
                System.out.println("Enter Package Description: ");
                des = sc.nextLine();
                System.out.println("Enter Number of Items in Package: ");
                packageSize = sc.nextInt();
                sc.nextLine(); // throw away \n in buffer
                for (i = 1; i <= packageSize; i++) {
                    System.out.println("Enter Ala Carte Item Number " + i + " Name: ");
                    subName = sc.nextLine();
                    while (true) {
                        if (mainManager.getSubManager("menuManager", MenuManager.class).getItem(subName) == null) {
                            System.out.println("Item Does Not exist! Try Again: ");
                            subName = sc.nextLine();
                        } else {
                            break;
                        }
                    }
                    componentList.add((AlaCarteItem) mainManager.getSubManager("menuManager", MenuManager.class).getItem(subName));
                }
                ICommand<Void, InvalidMenuItemException> addPackageCommand = new AddPackageCommand(
                        mainManager.getSubManager("menuManager", MenuManager.class),
                        name, price, des, componentList);
                try {
                    addPackageCommand.execute();
                    System.out.println("Package Added!");
                    System.out.println();
                } catch (InvalidMenuItemException | ParseException e) {
                    System.out.println(e.getMessage());
                }
                componentList.clear();
                view = MenuView.MENU_ITEMS;
            }
            case 3 -> {
                //Update Menu Item
                System.out.println("Enter Item to Update:");
                name = sc.nextLine();
                if (mainManager.getSubManager("menuManager", MenuManager.class).getItem(name) == null) {
                    System.out.println("Requested Menu Item Does Not Exists!");
                    break;
                }
                System.out.println("Enter New Name: ");
                subName = sc.nextLine();
                System.out.println("Enter New Description: ");
                System.out.println("(Current Description: " + mainManager.getSubManager("menuManager", MenuManager.class).getItem(name).getDescription() + ")");
                des = sc.nextLine();
                System.out.println("Enter New Price: ");
                System.out.println("(Current Price: " + String.format("%.2f", mainManager.getSubManager("menuManager", MenuManager.class).getItem(name).getPrice()) + ")");
                price = sc.nextDouble();
                sc.nextLine(); // throw away \n in buffer
                ICommand<Void, InvalidMenuItemException> updateMenuItemCommand = new UpdateMenuItemCommand(
                        mainManager.getSubManager("menuManager", MenuManager.class),
                        name, subName, price, des);
                try {
                    updateMenuItemCommand.execute();
                    System.out.println(name + " Was Updated!");
                    System.out.println();
                } catch (InvalidMenuItemException | ParseException e) {
                    System.out.println(e.getMessage());
                }
                view = MenuView.MENU_ITEMS;
            }
            case 4 -> {
                //Remove Menu Item
                System.out.println("Enter Item to Removed: ");
                name = sc.nextLine();
                ICommand<Void, InvalidMenuItemException> removeMenuItemCommand = new RemoveMenuItemCommand(mainManager.getSubManager("menuManager", MenuManager.class), name);
                try {
                    removeMenuItemCommand.execute();
                    System.out.println(name + " Was Removed!");
                    System.out.println();
                } catch (InvalidMenuItemException | ParseException e) {
                    System.out.println(e.getMessage());
                }
                view = MenuView.MENU_ITEMS;
            }
            case 5 -> {
                //Add Ala carte to package
                System.out.println("Enter Package Name:");
                name = sc.nextLine();
                System.out.println("Enter Name of Ala Carte Item to Added: ");
                subName = sc.nextLine();
                ICommand<Void, InvalidMenuItemException> addToPackageCommand = new AddToPackageCommand(mainManager.getSubManager("menuManager", MenuManager.class), name, subName);
                try {
                    addToPackageCommand.execute();
                    System.out.println(subName + " Was Added to " + name);
                    System.out.println();
                } catch (InvalidMenuItemException | ParseException e) {
                    System.out.println(e.getMessage());
                }
                view = MenuView.MENU_ITEMS;
            }
            case 6 -> {
                //Remove ala carte from package
                System.out.println("Enter Package Name: ");
                name = sc.nextLine();
                System.out.println("Enter Name of Ala Carte Item to Removed: ");
                subName = sc.nextLine();
                ICommand<Void, InvalidMenuItemException> removeFromPackageCommand = new RemoveFromPackageCommand(mainManager.getSubManager("menuManager", MenuManager.class), name, subName);
                try {
                    removeFromPackageCommand.execute();
                    System.out.println(subName + " Was Removed from " + name);
                    System.out.println();
                } catch (InvalidMenuItemException | ParseException e) {
                    System.out.println(e.getMessage());
                }
                view = MenuView.MENU_ITEMS;
            }
            case 7 -> {
                //Print Menu
                getMenuCommand = new GetMenuCommand(mainManager.getSubManager("menuManager", MenuManager.class));
                ArrayList<MenuItem> menu = getMenuCommand.execute();
                displayMenu(menu);
                view = MenuView.MENU_ITEMS;
            }
            case 8 ->
                    // Back
                    view = MenuView.PREVIOUS_MENU;
        }
        return view;

    }

}
