package com.CZ2002.project_displays;
import com.CZ2002.project_boundaries.MenuManager;
import com.CZ2002.project_entities.AlaCarteItem;
import com.CZ2002.project_utils.MenuBuilder;
import com.CZ2002.project_enums.Type;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * A boundary class that takes in inputs from user to interact with MenuItem
 */
public class MenuConsole{
/**
 * function to print out the console
 */
    public void menuConsole() {	//main function for test run. Replace with "public void menuConsole()" before compilation!
        MenuManager mm = new MenuManager();	//will be created in restaurant/ main option for the actual application

        //Initial population of MenuItem. Must be initialized in restaurant along with MenuManager.
        mm.addAlaCarteItem("Battered Fish & Chips", "Battered fish fillets lightly seasoned, fried to a crisp golden brown and served with creamy tartar sauce.", 12.5, Type.MAIN);
        mm.addAlaCarteItem("BBQ Chicken", "Super tender juicy grilled chicken generously brushed with our in-house guava BBQ sauce.", 12.50, Type.MAIN);
        mm.addAlaCarteItem("Grilled Fish Sambal", "Tender fish fillet seasoned with spices for a mildly fiery kick, grilled to perfection.", 13.50, Type.MAIN);
        mm.addAlaCarteItem("Lamb Chops", "Cuts of grilled lamb marinated with our homemade herbs and spices.", 14.00, Type.MAIN);
        mm.addAlaCarteItem("Ribeye Steak", "Grilled ribeye steak with choice of pineapple BBQ, sambal or black pepper sauce.", 18.00,Type.MAIN);
        mm.addAlaCarteItem("Cookie Summit", "Mountain of Cookies 'N' Cream and Butterscotch ice cream, covered by cold fudge and chocolate chips.", 3.00, Type.DESSERT);
        mm.addAlaCarteItem("Double Berry", "Strawberry ice cream with layers of blueberry topping.", 3.00, Type.DESSERT);
        mm.addAlaCarteItem("Mango Peach Tropics", "Mango and peach flavoured sparkling drink.", 3.00, Type.DRINK);
        mm.addAlaCarteItem("Sparkling Pink Lemonade", "Lemon and strawberry flavoured sparkling drink.", 3.00, Type.DRINK);

        // Main options
        String title = "MenuItem Options";
        String[] options = {"Add an ala carte item","Add a package", "Update an item", "Remove an item", "Add an ala carte item to a package",
                "Remove an ala carte item from a package","Print menu","Exit"};

        String menuMenu = MenuBuilder.buildMenu(title,options);

        //initiating necessary variables for inputs
        int choice, subChoice, packageSize,i;
        String name,subName, des, dummy;
        double price;
        Type type;
        ArrayList<AlaCarteItem> componentList = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        System.out.println(menuMenu);
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

                String typeTitle = "Pick ala carte item's type";
                String[] typeOptions = {"Main", "Drinks", "Desserts"};
                String typeMenu = MenuBuilder.buildMenu(typeTitle, typeOptions);
                System.out.println(typeMenu);

                subChoice = sc.nextInt();
                if (subChoice == 1) {
                    type = Type.MAIN;
                } else if (subChoice == 2) {
                    type = Type.DRINK;
                } else {
                    type = Type.DESSERT;
                }


                mm.addAlaCarteItem(name, des, price, type);

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
                        if (mm.getItem(subName) == null) {
                            System.out.println("Item does not exist! Try again: ");
                            subName= sc.nextLine();
                        } else {
                            break;
                        }
                    }
                    componentList.add((AlaCarteItem)mm.getItem(subName));
                }

                mm.addPackageItem(name, des, price, componentList);

                componentList.clear();

                //item successfully added
                System.out.println("Package added!");
                System.out.println();



            } else if (choice ==3) {	//update an item

                //input for the required params
                System.out.println("Enter item to be changed:");
                name = sc.nextLine();
                while (true) {
                    if (mm.getItem(name) == null) {
                        System.out.println("Item does not exist! Try again: ");
                        name= sc.nextLine();
                    } else {
                        break;
                    }
                }
                System.out.println("Enter new name: ");
                subName = sc.nextLine();

                System.out.println("Enter new description: ");
                System.out.println("(Current description: " + mm.getItem(name).getDescription() +  ")");
                des = sc.nextLine();

                System.out.println("Enter new price:");
                System.out.println("(Current price: " + mm.getItem(name).getPrice() +  ")");
                price = sc.nextDouble();
                dummy= sc.nextLine();

                mm.updateItem(name, subName, des, price);

                //item successfully updated
                System.out.println(name + " was changed!");
                System.out.println();


            } else if (choice ==4) {	//remove an item (does not remove components from package)

                //input for the required params
                System.out.println("Enter item to be removed:");
                name = sc.nextLine();
                while (true) {
                    if (mm.getItem(name) == null) {
                        System.out.println("Item does not exist! Try again: ");
                        name= sc.nextLine();
                    } else {
                        break;
                    }
                }
                mm.removeItem(name);

                //item successfully removed
                System.out.println(name + " was removed!");
                System.out.println();


            } else if (choice == 5) {	//add ala carte to package

                //input for the required params
                System.out.println("Enter package name:");
                name = sc.nextLine();
                while (true) {
                    if (mm.getItem(name) == null) {
                        System.out.println("Item does not exist! Try again: ");
                        subName= sc.nextLine();
                    } else {
                        break;
                    }
                }

                System.out.println("Enter name of ala carte to be added:");
                subName = sc.nextLine();
                while (true) {
                    if (mm.getItem(subName) == null) {
                        System.out.println("Item does not exist! Try again: ");
                        subName= sc.nextLine();
                    } else {
                        break;
                    }
                }
                mm.addItemToPackage(name, subName);

                //item successfully added to package
                System.out.println( subName + " was added to " + name);
                System.out.println();


            } else if (choice == 6) {	//remove ala carte from item

                //input for the required params
                System.out.println("Enter package name:");
                name = sc.nextLine();
                while (true) {
                    if (mm.getItem(name) == null) {
                        System.out.println("Item does not exist! Try again: ");
                        subName= sc.nextLine();
                    } else {
                        break;
                    }
                }

                System.out.println("Enter name of ala carte to be removed:");
                subName = sc.nextLine();
                while (true) {
                    if (mm.getItem(subName) == null) {
                        System.out.println("Item does not exist! Try again: ");
                        subName= sc.nextLine();
                    } else {
                        break;
                    }
                }
                mm.removeItemFromPackage(name, subName);

                //item successfully added to package
                System.out.println( subName + " was removed from " + name);
                System.out.println();

            } else if (choice == 7) {
                mm.printMenu();
            } else {
                System.out.println("Enter valid choice!");
            }

            System.out.println(menuMenu);
            System.out.println("Enter choice: ");
            choice = sc.nextInt();
            dummy= sc.nextLine();
        }
    }

}