import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import RestaurantClasses.MenuItem;
import Utils.MenuBuilder;
import Display.*;

public class RRPSS {
    private ArrayList<MenuItem> menu;
    private ArrayList<Order> orders;
    private ArrayList<Reservation> reservations;
    private Table[] tables;

    public static enum menuView {
        PREVIOUS_MENU,
        CURRENT_MENU,
        MENU_ITEMS,
        PROMOTIONS,
        ORDERS,
        RESERVATIONS,
        PROGRAM_END
    }

    public RRPSS() {
        menu = new ArrayList<MenuItem>();
        orders = new ArrayList<Order>();
        reservations = new ArrayList<Reservation>();
        tables = new Table[10];
    }

    public menuView handleMainMenuOptions(Scanner sc, int optionsLength) {
        int choice = sc.nextInt();
        menuView view = menuView.CURRENT_MENU;

        switch (choice) {
            case 1:
                // Menu Items
                view = menuView.MENU_ITEMS;
                break;
            case 2:
                // Promotions
                view = menuView.PROMOTIONS;
                break;
            case 3:
                // Orders
                view = menuView.ORDERS;
                break;
            case 4:
                // Reservations
                view = menuView.RESERVATIONS;
                break;
            case 5:
                // Sales Revenue Report
                break;
            case 6:
                // Quit
                view = menuView.PROGRAM_END;
                break;
        }

        return view;
    }

    public menuView handleMenuItemsOptions(Scanner sc, int optionsLength) {
        int choice = sc.nextInt();
        menuView view = menuView.MENU_ITEMS;

        System.out.println("What would you like to do?");
        switch (choice) {
            case 1:
                // Create Menu Item
                view = createMenuItem(sc);
                break;
            case 2:
                // Edit Menu Item
                do {
                    view = editMenuItem(sc);
                } while (view == menuView.CURRENT_MENU);
                break;
            case 3:
                // Remove Menu Item
                do {
                    view = removeMenuItem(sc);
                } while (view == menuView.CURRENT_MENU);

                break;
            case 4:
                // Back
                view = menuView.PREVIOUS_MENU;
                break;
        }

        return view;
    }

    public menuView handlePromotionsOptions(Scanner sc, int optionsLength) {
        int choice = sc.nextInt();
        menuView view = menuView.PROMOTIONS;

        switch (choice) {
            case 1:
                // Create Promotion
                break;
            case 2:
                // Edit Promotion
                break;
            case 3:
                // Remove Promotion
                break;
            case 4:
                // Back
                view = menuView.PREVIOUS_MENU;
                break;
        }

        return view;
    }

    public menuView handleOrdersOptions(Scanner sc, int optionsLength) {
        int choice = sc.nextInt();
        menuView view = menuView.ORDERS;

        switch (choice) {
            case 1:
                // Create Order
                break;
            case 2:
                // View Order
                break;
            case 3:
                // Edit Order
                break;
            case 4:
                // Back
                view = menuView.PREVIOUS_MENU;
                break;
        }

        return view;
    }

    public menuView handleReservationsOptions(Scanner sc, int optionsLength) {
        int choice = sc.nextInt();
        menuView view = menuView.RESERVATIONS;

        switch (choice) {
            case 1:
                // Create Reservation
                break;
            case 2:
                // Check Reservation
                break;
            case 3:
                // Remove Reservation
                break;
            case 4:
                // Back
                view = menuView.PREVIOUS_MENU;
                break;
        }

        return view;
    }

    public menuView createMenuItem(Scanner sc) {
        System.out.println("Creating a new Menu Item");
        sc.nextLine(); // Throw away \n in buffer
        System.out.printf("Item Name: ");
        String name = sc.nextLine();
        System.out.printf("Item Description: ");
        String description = sc.nextLine();
        System.out.printf("Price: ");
        double price = sc.nextDouble();

        menu.add(new MenuItem(name, description, price));

        return menuView.MENU_ITEMS;
    }

    public menuView handleEditMenuItem(Scanner sc, MenuItem item) {
        String[] options = {"Edit Name", "Edit Price", "Edit Description", "Back"};
        menuView view = menuView.CURRENT_MENU;

        new MenuItemDetailsDisplay(item).displayMenu();
        System.out.println(MenuBuilder.buildMenu(20, options));

        System.out.println("What would you like to edit?");

        int choice = sc.nextInt();

        switch(choice) {
            case 1:
                System.out.printf("Item Name: ");
                sc.nextLine();
                item.setName(sc.nextLine());
                break;
            case 2:
                System.out.printf("Price: ");
                sc.nextLine();
                item.setPrice(sc.nextDouble());
                break;
            case 3:
                System.out.printf("Item Description: ");
                sc.nextLine();
                item.setDescription(sc.nextLine());
                break;
            case 4:
                view = menuView.PREVIOUS_MENU;
                break;
        }
        return view;
    }

    public menuView editMenuItem(Scanner sc) {
        menuView view = menuView.CURRENT_MENU;
            
        int back = new MenuItemsOptionsDisplay(menu).displayMenu();
            
        System.out.println("Which item would you like to edit?");
            
        int choice = sc.nextInt();

        if (choice == back) {
            view = menuView.MENU_ITEMS;
            return view;
        }

        MenuItem item = menu.get(choice - 1);

        do {
            view = handleEditMenuItem(sc, item);
        } while (view == menuView.CURRENT_MENU);
        
        view = menuView.CURRENT_MENU;
        return view;
    }

    // TODO: Add DisplayEditMenutItem Code

    // TODO: Add HandleEditMenuItem Code

    public menuView removeMenuItem(Scanner sc) {
        int back = new MenuItemsOptionsDisplay(menu).displayMenu();
        System.out.println("Which item would you like to remove?");
        int choice = sc.nextInt();
        if (choice == back) {
            return menuView.MENU_ITEMS;
        }

        menu.remove(choice - 1);

        if (menu.size() == 0) {
            return menuView.MENU_ITEMS;
        }

        return menuView.CURRENT_MENU;
    }
}


