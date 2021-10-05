import java.util.ArrayList;
import java.util.Scanner;

import Utils.MenuBuilder;

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

    public int displayMainMenuOptions() {
        String[] options = {
            "Menu Items",
            "Promotions",
            "Orders",
            "Reservations",
            "Sales Revenue Report",
            "Quit"
        };

        final String title = "Restaurant Reservation & Point of Sale System";

        System.out.println(MenuBuilder.buildMenu(title, options));

        return options.length;
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

    public int displayMenuOptions() {
        displayMenuItems();

        String[] options = {
            "Create Menu Item",
            "Edit Menu Item",
            "Remove Menu Item",
            "Back"
        };

        final String title = "Menu Items";

        System.out.println(MenuBuilder.buildMenu(title, options));

        return options.length;
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
                view = editMenuItem(sc);
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

    public int displayPromotionsOptions() {
        String[] options = {
            "Create Promotion",
            "Edit Promotion",
            "Remove Promotion",
            "Back"
        };

        final String title = "Promotions";

        System.out.println(MenuBuilder.buildMenu(title, options));

        return options.length;
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

    public int displayOrdersOptions() {
        String[] options = {
            "Create Order",
            "View Order",
            "Edit Order",
            "Back"
        };

        final String title = "Orders";

        System.out.println(MenuBuilder.buildMenu(title, options));

        return options.length;
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

    public int displayReservationsOptions() {
        String[] options = {
            "Create Reservation Booking",
            "Check Reservation Booking",
            "Remove Reservation Booking",
            "Back"
        };

        final String title = "Reservations";

        System.out.println(MenuBuilder.buildMenu(title, options));

        return options.length;
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
        System.out.printf("Item description: ");
        String description = sc.nextLine();
        System.out.printf("Price: ");
        double price = sc.nextDouble();

        menu.add(new MenuItem(name, description, price));

        return menuView.MENU_ITEMS;
    }

    private void displayMenuItems() {
        if (menu.size() == 0) {
            System.out.println("The menu is currently empty");
            return;
        }

        String[] options = new String[menu.size()];
        for (int i = 0; i < options.length; i++) {
            options[i] = String.format("%s ($%.2f)", menu.get(i).getName(), menu.get(i).getPrice());
        }

        final String title = "Menu";

        System.out.println(MenuBuilder.buildMenu(title, options));
    }

    private int displayMenuItemsOptions() {
        String[] options = new String[menu.size() + 1];
        for (int i = 0; i < menu.size(); i++) {
            options[i] = String.format("%s ($%.2f)", menu.get(i).getName(), menu.get(i).getPrice());
        }

        options[options.length - 1] = "Back";

        final String title = "Menu";

        System.out.println(MenuBuilder.buildMenu(title, options));

        return options.length;
    }

    public menuView editMenuItem(Scanner sc) {
        int back = displayMenuItemsOptions();
        System.out.println("Which item would you like to edit?");
        int choice = sc.nextInt();
        if (choice == back) {
            System.out.println("here");
            return menuView.MENU_ITEMS;
        }

        displayMenuItemDetails(menu.get(choice - 1));

        System.out.println("What would you like to edit?");
        choice = sc.nextInt();

        return menuView.MENU_ITEMS;
    }

    // TODO: Add DisplayEditMenutItem Code

    // TODO: Add HandleEditMenuItem Code

    public void displayMenuItemDetails(MenuItem item) {
        final int LONGEST_WIDTH = 20;
        String title = item.getName();
        String[] optionHeaders = {"Price", "Description"};
        String[] options = {String.format("$%.2f", item.getPrice()), item.getDescription()};
        System.out.println(MenuBuilder.buildMenu(title, optionHeaders, options, LONGEST_WIDTH));
    }

    public menuView removeMenuItem(Scanner sc) {
        int back = displayMenuItemsOptions();
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


