package Unused;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

import RestaurantClasses.MenuItem;
import RestaurantClasses.Order;
import RestaurantClasses.Reservation;
import RestaurantClasses.Restaurant;
import RestaurantClasses.Table;
import Utils.MenuBuilder;

public class RRPSS {
    private ArrayList<MenuItem> menu;
    private ArrayList<Order> orders;
    private Table[] tables;
    private Restaurant restaurant;

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
        tables = new Table[10];
        //restaurant = new Restaurant("Koufu", 2000, 100, 10, menu);
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
                    view = createReservation(sc);
                    break;
                case 2:
                    // Check Reservation
                    //view = checkReservation(sc);
                    break;
                case 3:
                    // Update Reservation
                    //view = updateReservation(sc);
                    break;
                case 4:
                    // Remove Reservation
                    //view = removeReservation(sc);
                    break;
                case 5:
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
        System.out.printf("Type: ");
        String type = sc.nextLine();

        menu.add(new MenuItem(name, description, price, type));

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
            
        //int back = new MenuItemsOptionsDisplay(menu).displayMenu();
        int back = 1;
            
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

    public menuView createReservation(Scanner sc) {
        System.out.println("Creating a new Reservation");
        sc.nextLine(); // Throw away \n in buffer
        System.out.printf("Name: ");
        String name = sc.nextLine();
        System.out.printf("Contact: ");
        String contact = sc.nextLine();
        System.out.printf("Pax: ");
        int pax = sc.nextInt();
        System.out.printf("Reservation Period(DD/MM/YY HH:MM): ");
        sc.nextLine(); // Throw away \n in buffer
        String reservationPeriodString = sc.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(reservationPeriodString, formatter);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Singapore"));
        GregorianCalendar reservationPeriod = GregorianCalendar.from(zonedDateTime);

        //restaurant.addReservation(reservationPeriod, pax, name, contact);

        return menuView.MENU_ITEMS;
    }

    // public menuView checkReservation(Scanner sc) {
    //     System.out.println("Which Reservation are you looking for?");
    //     sc.nextLine(); // Throw away \n in buffer
    //     System.out.printf("Name: ");
    //     String name = sc.nextLine();
    //     System.out.printf("Contact: ");
    //     String contact = sc.nextLine();
    //     System.out.printf("Reservation Period(DD/MM/YY HH:MM): ");
    //     String reservationPeriodString = sc.nextLine();

    //     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
    //     LocalDateTime localDateTime = LocalDateTime.parse(reservationPeriodString, formatter);
    //     ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Singapore"));
    //     GregorianCalendar reservationPeriod = GregorianCalendar.from(zonedDateTime);

    //     Reservation reservation = restaurant.getReservationDetails(name, contact, reservationPeriod);

    //     if (reservation == null) {
    //         System.out.println("The requested reservation does not exist!");
    //     } else {
    //         new ReservationDetailsDisplay(reservation).displayMenu();
    //     }

    //     return menuView.MENU_ITEMS;
    // }

    // public menuView removeReservation(Scanner sc) {
    //     System.out.println("Which Reservation would you like to remove?");
    //     sc.nextLine(); // Throw away \n in buffer
    //     System.out.printf("Name: ");
    //     String name = sc.nextLine();
    //     System.out.printf("Contact: ");
    //     String contact = sc.nextLine();
    //     System.out.printf("Reservation Period(DD/MM/YY HH:MM): ");
    //     String reservationPeriodString = sc.nextLine();

    //     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
    //     LocalDateTime localDateTime = LocalDateTime.parse(reservationPeriodString, formatter);
    //     ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Singapore"));
    //     GregorianCalendar reservationPeriod = GregorianCalendar.from(zonedDateTime);

    //     if (restaurant.removeReservation(name, contact, reservationPeriod)) {
    //         System.out.println("Reservation successfully removed!");
    //     } else {
    //         System.out.println("The requested reservation does not exist!");
    //     }

    //     return menuView.MENU_ITEMS;
    // }

    // public menuView updateReservation(Scanner sc) {
    //     // Similar to checkReservation (Can refactor)
    //     menuView view = menuView.CURRENT_MENU;
    //     System.out.println("Which Reservation would you like to update?");
    //     sc.nextLine(); // Throw away \n in buffer
    //     System.out.printf("Name: ");
    //     String name = sc.nextLine();
    //     System.out.printf("Contact: ");
    //     String contact = sc.nextLine();
    //     System.out.printf("Reservation Period(DD/MM/YY HH:MM): ");
    //     String reservationPeriodString = sc.nextLine();

    //     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
    //     LocalDateTime localDateTime = LocalDateTime.parse(reservationPeriodString, formatter);
    //     ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Singapore"));
    //     GregorianCalendar reservationPeriod = GregorianCalendar.from(zonedDateTime);

    //     Reservation reservation = restaurant.getReservationDetails(name, contact, reservationPeriod);

    //     if (reservation == null) {
    //         System.out.println("The requested reservation does not exist!");
    //         return menuView.MENU_ITEMS;
    //     } else {
    //         do {
    //             new ReservationDetailsDisplay(reservation).displayMenu();
    //             System.out.println("What would you like to update?");
    //             new ReservationUpdateOptionsDisplay().displayMenu();
    //             int choice = sc.nextInt();
    //             switch (choice) {
    //                 case 1:
    //                     System.out.printf("Name: ");
    //                     sc.nextLine(); // Throw away \n in buffer
    //                     String newName = sc.nextLine();
    //                     restaurant.updateReservation(reservation, 1, newName);
    //                     break;
    //                 case 2: 
    //                     System.out.printf("Contact: ");
    //                     sc.nextLine(); // Throw away \n in buffer
    //                     String newContact = sc.nextLine();
    //                     restaurant.updateReservation(reservation, 2, newContact);
    //                     break;
    //                 case 3:
    //                     System.out.printf("Pax: ");
    //                     int newPax = sc.nextInt();
    //                     restaurant.updateReservation(reservation, 1, newPax);
    //                     break;
    //                 case 4:
    //                     System.out.printf("Table No: ");
    //                     int newTableNo = sc.nextInt();
    //                     restaurant.updateReservation(reservation, 2, newTableNo);
    //                     break;
    //                 case 5:
    //                     System.out.printf("Reservation Period(DD/MM/YY HH:MM): ");
    //                     sc.nextLine(); // Throw away \n in buffer
    //                     String newReservationPeriodString = sc.nextLine();
                
    //                     LocalDateTime newLocalDateTime = LocalDateTime.parse(newReservationPeriodString, formatter);
    //                     ZonedDateTime newZonedDateTime = newLocalDateTime.atZone(ZoneId.of("Asia/Singapore"));
    //                     GregorianCalendar newReservationPeriod = GregorianCalendar.from(newZonedDateTime);
                        
    //                     restaurant.updateReservation(reservation, newReservationPeriod);
    //                     break;
    //                 case 6:
    //                     view = menuView.PREVIOUS_MENU;
    //                     break;
    //             }
    //         } while (view != menuView.PREVIOUS_MENU);
    //     } 

    //     return menuView.MENU_ITEMS;
    // }
}


