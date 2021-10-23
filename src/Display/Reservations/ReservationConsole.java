package Display.Reservations;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;
import java.util.Scanner;

import RestaurantClasses.Reservation;
import Utils.MenuBuilder;
import Utils.MenuView;

public class ReservationConsole {
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

    public int displayReservationMenuOptions() {
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

    public MenuView createReservation(Scanner sc) {
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

        restaurant.addReservation(reservationPeriod, pax, name, contact);

        return MenuView.MENU_ITEMS;
    }

    public MenuView checkReservation(Scanner sc) {
        System.out.println("Which Reservation are you looking for?");
        sc.nextLine(); // Throw away \n in buffer
        System.out.printf("Name: ");
        String name = sc.nextLine();
        System.out.printf("Contact: ");
        String contact = sc.nextLine();
        System.out.printf("Reservation Period(DD/MM/YY HH:MM): ");
        String reservationPeriodString = sc.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(reservationPeriodString, formatter);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Singapore"));
        GregorianCalendar reservationPeriod = GregorianCalendar.from(zonedDateTime);

        Reservation reservation = restaurant.getReservationDetails(name, contact, reservationPeriod);

        if (reservation == null) {
            System.out.println("The requested reservation does not exist!");
        } else {
            displayReservationDetails(reservation);
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
        System.out.printf("Reservation Period(DD/MM/YY HH:MM): ");
        String reservationPeriodString = sc.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(reservationPeriodString, formatter);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Singapore"));
        GregorianCalendar reservationPeriod = GregorianCalendar.from(zonedDateTime);

        if (restaurant.removeReservation(name, contact, reservationPeriod)) {
            System.out.println("Reservation successfully removed!");
        } else {
            System.out.println("The requested reservation does not exist!");
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
        System.out.printf("Reservation Period(DD/MM/YY HH:MM): ");
        String reservationPeriodString = sc.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(reservationPeriodString, formatter);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Singapore"));
        GregorianCalendar reservationPeriod = GregorianCalendar.from(zonedDateTime);

        Reservation reservation = restaurant.getReservationDetails(name, contact, reservationPeriod);

        if (reservation == null) {
            System.out.println("The requested reservation does not exist!");
            return MenuView.MENU_ITEMS;
        } else {
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
                        restaurant.updateReservation(reservation, 1, newName);
                        break;
                    case 2: 
                        System.out.printf("Contact: ");
                        sc.nextLine(); // Throw away \n in buffer
                        String newContact = sc.nextLine();
                        restaurant.updateReservation(reservation, 2, newContact);
                        break;
                    case 3:
                        System.out.printf("Pax: ");
                        int newPax = sc.nextInt();
                        restaurant.updateReservation(reservation, 1, newPax);
                        break;
                    case 4:
                        System.out.printf("Table No: ");
                        int newTableNo = sc.nextInt();
                        restaurant.updateReservation(reservation, 2, newTableNo);
                        break;
                    case 5:
                        System.out.printf("Reservation Period(DD/MM/YY HH:MM): ");
                        sc.nextLine(); // Throw away \n in buffer
                        String newReservationPeriodString = sc.nextLine();
                
                        LocalDateTime newLocalDateTime = LocalDateTime.parse(newReservationPeriodString, formatter);
                        ZonedDateTime newZonedDateTime = newLocalDateTime.atZone(ZoneId.of("Asia/Singapore"));
                        GregorianCalendar newReservationPeriod = GregorianCalendar.from(newZonedDateTime);
                        
                        restaurant.updateReservation(reservation, newReservationPeriod);
                        break;
                    case 6:
                        view = MenuView.PREVIOUS_MENU;
                        break;
                }
            } while (view != MenuView.PREVIOUS_MENU);
        } 

        return MenuView.MENU_ITEMS;
    }
}
