package com.CZ2002.project_displays;
import com.CZ2002.project_boundaries.MenuManager;
import com.CZ2002.project_boundaries.RestaurantManager;
import com.CZ2002.project_commands.menu.AddAlaCarteCommand;
import com.CZ2002.project_commands.menu.AddPackageCommand;
import com.CZ2002.project_commands.menu.RemoveMenuItemCommand;
import com.CZ2002.project_commands.menu.UpdateMenuItemCommand;
import com.CZ2002.project_commands.menu.AddToPackageCommand;
import com.CZ2002.project_commands.menu.RemoveFromPackageCommand;
import com.CZ2002.project_entities.AlaCarteItem;
import com.CZ2002.project_utils.MenuBuilder;
import com.CZ2002.project_enums.MenuView;
import com.CZ2002.project_enums.Type;

import java.util.ArrayList;
import java.util.Scanner;


/**
 * A boundary class that takes in inputs from user to interact with MenuItem
 */
public class MenuConsole extends ConsoleDisplay{
    private AddAlaCarteCommand addAlaCarteCommand;
    private AddPackageCommand addPackageCommand;
    private UpdateMenuItemCommand updateMenuItemCommand;
    private RemoveMenuItemCommand removeMenuItemCommand;
    private AddToPackageCommand addToPackageCommand;
    private RemoveFromPackageCommand removeFromPackageCommand;

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
                "Exit"

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

    @Override
    /**
     * Accepts input from the user surrounding the possible actions the user can take
     * in relation to {@link MenuItem,AlaCarteItem, PackageItem} instances.
     */
    public MenuView handleConsoleOptions() {

        //
        MenuView view = MenuView.MENU_ITEMS;
        int choice, subChoice, packageSize,i;
        String name,subName, des, dummy;
        double price;
        Type type;
        ArrayList<AlaCarteItem> componentList = new ArrayList<>();

        //Scanner sc = new Scanner(System.in);
        displayConsoleOptions();
        System.out.println("Enter choice: ");
        choice = sc.nextInt();
        dummy= sc.nextLine();

        while (choice != 8) {
            if (choice == 1) {	// add an ala carte item

                //input for the required params
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


            } else if (choice == 2) {	//add a package item

                //input for the required params
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
                        if (mainManager.getSubManager("menuManager", MenuManager.class).getItem(subName) == null) {
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



            } else if (choice ==3) {	//update an item

                //input for the required params
                System.out.println("Enter item to be changed:");
                name = sc.nextLine();
                while (true) {
                    if (mainManager.getSubManager("menuManager", MenuManager.class).getItem(name) == null) {
                        System.out.println("Item does not exist! Try again: ");
                        name= sc.nextLine();
                    } else {
                        break;
                    }
                }
                System.out.println("Enter new name: ");
                subName = sc.nextLine();

                System.out.println("Enter new description: ");
                System.out.println("(Current description: " + mainManager.getSubManager("menuManager", MenuManager.class).getItem(name).getDescription() +  ")");
                des = sc.nextLine();

                System.out.println("Enter new price:");
                System.out.println("(Current price: " + mainManager.getSubManager("menuManager", MenuManager.class).getItem(name).getPrice() +  ")");
                price = sc.nextDouble();
                dummy= sc.nextLine();

                updateMenuItemCommand = new UpdateMenuItemCommand(
                        mainManager.getSubManager("menuManager", MenuManager.class),
                        name,subName,price,des);
                updateMenuItemCommand.execute();

                //item successfully updated
                System.out.println(name + " was changed!");
                System.out.println();


            } else if (choice ==4) {	//remove an item (does not remove components from package)

                //input for the required params
                System.out.println("Enter item to be removed:");
                name = sc.nextLine();
                while (true) {
                    if (mainManager.getSubManager("menuManager", MenuManager.class).getItem(name) == null) {
                        System.out.println("Item does not exist! Try again: ");
                        name= sc.nextLine();
                    } else {
                        break;
                    }
                }
                removeMenuItemCommand = new RemoveMenuItemCommand(mainManager.getSubManager("menuManager", MenuManager.class), name);
                removeMenuItemCommand.execute();

                //item successfully removed
                System.out.println(name + " was removed!");
                System.out.println();


            } else if (choice == 5) {	//add ala carte to package

                //input for the required params
                System.out.println("Enter package name:");
                name = sc.nextLine();
                while (true) {
                    if (mainManager.getSubManager("menuManager", MenuManager.class).getItem(name) == null) {
                        System.out.println("Item does not exist! Try again: ");
                        subName= sc.nextLine();
                    } else {
                        break;
                    }
                }

                System.out.println("Enter name of ala carte to be added:");
                subName = sc.nextLine();
                while (true) {
                    if (mainManager.getSubManager("menuManager", MenuManager.class).getItem(subName) == null) {
                        System.out.println("Item does not exist! Try again: ");
                        subName= sc.nextLine();
                    } else {
                        break;
                    }
                }
                addToPackageCommand = new AddToPackageCommand(mainManager.getSubManager("menuManager", MenuManager.class), name, subName);
                addToPackageCommand.execute();

                //item successfully added to package
                System.out.println( subName + " was added to " + name);
                System.out.println();


            } else if (choice == 6) {	//remove ala carte from item

                //input for the required params
                System.out.println("Enter package name:");
                name = sc.nextLine();
                while (true) {
                    if (mainManager.getSubManager("menuManager", MenuManager.class).getItem(name) == null) {
                        System.out.println("Item does not exist! Try again: ");
                        subName= sc.nextLine();
                    } else {
                        break;
                    }
                }

                System.out.println("Enter name of ala carte to be removed:");
                subName = sc.nextLine();
                while (true) {
                    if (mainManager.getSubManager("menuManager", MenuManager.class).getItem(subName) == null) {
                        System.out.println("Item does not exist! Try again: ");
                        subName= sc.nextLine();
                    } else {
                        break;
                    }
                }
                removeFromPackageCommand = new RemoveFromPackageCommand(mainManager.getSubManager("menuManager", MenuManager.class), name, subName);
                removeFromPackageCommand.execute();

                //item successfully added to package
                System.out.println( subName + " was removed from " + name);
                System.out.println();

            } else if (choice == 7) {
                mainManager.getSubManager("menuManager", MenuManager.class).printMenu();
            } else {
                System.out.println("Enter valid choice!");
            }

            displayConsoleOptions();
            System.out.println("Enter choice: ");
            choice = sc.nextInt();
            dummy= sc.nextLine();
        }

        view = MenuView.PREVIOUS_MENU;
        return view;

    }

}