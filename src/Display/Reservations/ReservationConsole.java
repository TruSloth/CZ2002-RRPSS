package Display.Reservations;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Display.ConsoleDisplay;
import Exceptions.InvalidReservationException;
import Exceptions.ReservationsFullException;
import ManagerClasses.RestaurantManager;
import RestaurantClasses.Reservation;
import Utils.MenuBuilder;
import Utils.MenuView;

public class ReservationConsole extends ConsoleDisplay {
    private RestaurantManager restaurantManager;

    private GregorianCalendar formatReservationPeriod(Scanner sc) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
        boolean done = false; // Exit loop only if no exception is caught
        LocalDateTime localDateTime = LocalDateTime.now();
        do {
            System.out.printf("Reservation Period(DD/MM/YY HH:MM): ");
            String reservationPeriodString = sc.nextLine();
            try {
                localDateTime = LocalDateTime.parse(reservationPeriodString, formatter);
                if (localDateTime.isBefore(LocalDateTime.now())) {

                }
                done = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date-time format!");
                done = false;
            }
        } while (!done);
        
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Singapore"));
        GregorianCalendar reservationPeriod = GregorianCalendar.from(zonedDateTime);
        return reservationPeriod;
    }

    public int displayReservationDetails(Reservation reservation) {
        final int LONGEST_WIDTH = 20;
        String[] optionHeaders = {"Name", "Contact", "Pax", "Table No.", "Reservation Period"};

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
        LocalDateTime ldt = reservation.getReservationPeriod().toInstant().atZone(ZoneId.of("Asia/Singapore")).toLocalDateTime();
        String[] options = new String[] {reservation.getName(), reservation.getContactNumber(), String.valueOf(reservation.getPax()), String.valueOf(reservation.getTableNo()), fmt.format(ldt)};
        String title = "Reservation Details";

        System.out.println(MenuBuilder.buildMenu(title, optionHeaders, options, LONGEST_WIDTH));

        return options.length;
    }

    public int displayConsoleOptions() {
        String[] options = new String[] {
            "Create Reservation Booking",
            "Check Reservation Booking",
            "Update Reservation Booking",
            "Remove Reservation Booking",
            "Back"
        };

        String title = "Reservations";

        System.out.println(MenuBuilder.buildMenu(title, options));
    
        return options.length;
    }

    public int displayUpdateReservationOptions() {
        String[] options = new String[] {
                "Name",
                "Contact",
                "Pax",
                "Table No.",
                "Reservation Period",
                "Back"
            };
    
        String title = "Reservations";

        System.out.println(MenuBuilder.buildMenu(title, options));
    
        return options.length;
    }

    public MenuView handleConsoleOptions(Scanner sc) {
        int choice = sc.nextInt();
        MenuView view = MenuView.RESERVATIONS;

        switch (choice) {
            case 1:
                // Create Reservation
                view = createReservation(sc);
                break;
            case 2:
                // Check Reservation
                view = checkReservation(sc);
                break;
            case 3:
                // Update Reservation
                view = updateReservation(sc);
                break;
            case 4:
                // Remove Reservation
                view = removeReservation(sc);
                break;
            case 5:
                // Back
                view = MenuView.PREVIOUS_MENU;
                break;
        }

        return view;
    }

    public MenuView createReservation(Scanner sc) {
        System.out.println("Creating a new Reservation");
        sc.nextLine(); // Throw away \n in buffer
        System.out.printf("Name: ");
        String name = sc.nextLine();
        System.out.printf("Contact: ");
        String contact = sc.nextLine();
        System.out.printf("Pax: ");
        int pax = sc.nextInt();
        sc.nextLine(); // Throw away \n in buffer
        
        GregorianCalendar reservationPeriod = formatReservationPeriod(sc);

        try {
            restaurantManager.addReservation(reservationPeriod, pax, name, contact);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } 

        return MenuView.MENU_ITEMS;
    }

    public MenuView checkReservation(Scanner sc) {
        System.out.println("Which Reservation are you looking for?");
        sc.nextLine(); // Throw away \n in buffer
        System.out.printf("Name: ");
        String name = sc.nextLine();
        System.out.printf("Contact: ");
        String contact = sc.nextLine();

        GregorianCalendar reservationPeriod = formatReservationPeriod(sc);

        Reservation reservation;

        try {
            reservation = restaurantManager.getReservationDetails(name, contact, reservationPeriod);
            displayReservationDetails(reservation);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return MenuView.MENU_ITEMS;
    }

    public MenuView removeReservation(Scanner sc) {
        System.out.println("Which Reservation would you like to remove?");
        sc.nextLine(); // Throw away \n in buffer
        System.out.printf("Name: ");
        String name = sc.nextLine();
        System.out.printf("Contact: ");
        String contact = sc.nextLine();
        
        GregorianCalendar reservationPeriod = formatReservationPeriod(sc);

        try {
            restaurantManager.removeReservation(name, contact, reservationPeriod);
            System.out.println("Reservation successfully removed!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return MenuView.MENU_ITEMS;
    }

    public MenuView updateReservation(Scanner sc) {
        // Similar to checkReservation (Can refactor)
        MenuView view = MenuView.CURRENT_MENU;
        System.out.println("Which Reservation would you like to update?");
        sc.nextLine(); // Throw away \n in buffer
        System.out.printf("Name: ");
        String name = sc.nextLine();
        System.out.printf("Contact: ");
        String contact = sc.nextLine();

        GregorianCalendar reservationPeriod = formatReservationPeriod(sc);

        try {
            Reservation reservation = restaurantManager.getReservationDetails(name, contact, reservationPeriod);
            do {
                displayReservationDetails(reservation);
                System.out.println("What would you like to update?");
                displayUpdateReservationOptions();
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        System.out.printf("Name: ");
                        sc.nextLine(); // Throw away \n in buffer
                        String newName = sc.nextLine();
                        restaurantManager.updateReservation(reservation, 1, newName);
                        break;
                    case 2: 
                        System.out.printf("Contact: ");
                        sc.nextLine(); // Throw away \n in buffer
                        String newContact = sc.nextLine();
                        restaurantManager.updateReservation(reservation, 2, newContact);
                        break;
                    case 3:
                        System.out.printf("Pax: ");
                        int newPax = sc.nextInt();
                        try {
                            restaurantManager.updateReservation(reservation, 1, newPax);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        
                        break;
                    case 4:
                        System.out.printf("Table No: ");
                        int newTableNo = sc.nextInt();
                        try {
                            restaurantManager.updateReservation(reservation, 2, newTableNo);
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        } catch (ReservationsFullException e) {
                            System.out.println(e.getMessage());
                        }
                        
                        break;
                    case 5:
                        GregorianCalendar newReservationPeriod = formatReservationPeriod(sc);
                        try {
                            restaurantManager.updateReservation(reservation, newReservationPeriod);
                        } catch (InvalidReservationException e) {
                            System.out.println(e.getMessage());
                        }
                        
                        break;
                    case 6:
                        view = MenuView.PREVIOUS_MENU;
                        break;
                }
            } while (view != MenuView.PREVIOUS_MENU);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }

        return MenuView.MENU_ITEMS;
    }
}
