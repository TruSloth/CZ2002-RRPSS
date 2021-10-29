package Display.Reservations;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;
import java.util.Scanner;

import Commands.AddReservationCommand;
import Commands.FindReservationCommand;
import Commands.RemoveReservationCommand;
import Commands.UpdateReservationCommand;
import Commands.iCommand;
import Commands.iGregorianCalendarFormatter;
import Display.ConsoleDisplay;
import Exceptions.InvalidReservationException;
import ManagerClasses.ReservationManager;
import ManagerClasses.RestaurantManager;
import ManagerClasses.TableManager;
import RestaurantClasses.Reservation;
import Utils.MenuBuilder;
import Utils.MenuView;

public class ReservationConsole extends ConsoleDisplay implements iGregorianCalendarFormatter {
    // Declare all available commands here

    private iCommand<Void, InvalidReservationException> addReservationCommand;

    private iCommand<Reservation, InvalidReservationException> findReservationCommand;

    private iCommand<MenuView, InvalidReservationException> updateReservationCommand;

    private iCommand<Boolean, InvalidReservationException> removeReservationCommand;

    public ReservationConsole(RestaurantManager restaurantManager, Scanner sc) {
        super.sc = sc;
        super.restaurantManager = restaurantManager;
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

    public MenuView handleConsoleOptions() {
        int choice = sc.nextInt();
        MenuView view = MenuView.RESERVATIONS;
        Reservation reservation;
        String name;
        String contact;
        int pax;
        GregorianCalendar reservationPeriod;

        switch (choice) {
            case 1:
                // Create Reservation
                System.out.println("Creating a new Reservation");
                sc.nextLine(); // Throw away \n in buffer
                System.out.printf("Name: ");
                name = sc.nextLine();
                System.out.printf("Contact: ");
                contact = sc.nextLine();
                System.out.printf("Pax: ");
                pax = sc.nextInt();
                sc.nextLine(); // Throw away \n in buffer
        
                reservationPeriod = format(sc, "Reservation Period");

                addReservationCommand = new AddReservationCommand(
                    restaurantManager.getSubManager("reservationManager", ReservationManager.class),
                    restaurantManager.getSubManager("tableManager", TableManager.class),
                    name, contact, pax, reservationPeriod);
                try {
                    addReservationCommand.execute();
                } catch (InvalidReservationException e) {
                    System.out.println(e.getMessage());
                }
                
                view = MenuView.RESERVATIONS;
                //view = createReservation(sc);
                break;
            case 2:
                // Check Reservation
                System.out.println("Which Reservation are you looking for?");
                sc.nextLine(); // Throw away \n in buffer
                System.out.printf("Name: ");
                name = sc.nextLine();
                System.out.printf("Contact: ");
                contact = sc.nextLine();
        
                reservationPeriod = format(sc, "Reservation Period");

                findReservationCommand = new FindReservationCommand(
                    restaurantManager.getSubManager("reservationManager", ReservationManager.class), 
                    name, contact, reservationPeriod);

                try {
                    reservation = findReservationCommand.execute();
                    displayReservationDetails(reservation);
                } catch (InvalidReservationException e) {
                    System.out.println(e.getMessage());
                }
                
                view = MenuView.RESERVATIONS;
                //view = checkReservation(sc);
                break;
            case 3:
                // Update Reservation
                System.out.println("Which Reservation are you looking for?");
                sc.nextLine(); // Throw away \n in buffer
                System.out.printf("Name: ");
                name = sc.nextLine();
                System.out.printf("Contact: ");
                contact = sc.nextLine();
        
                reservationPeriod = format(sc, "Reservation Period");

                findReservationCommand = new FindReservationCommand(
                    restaurantManager.getSubManager("reservationManager", ReservationManager.class), 
                    name, contact, reservationPeriod);

                try {
                    reservation = findReservationCommand.execute();

                    updateReservationCommand = new UpdateReservationCommand(
                    restaurantManager.getSubManager("reservationManager", ReservationManager.class),
                    restaurantManager.getSubManager("tableManager", TableManager.class), 
                    reservation, sc);

                    view = MenuView.CURRENT_MENU;

                    do {
                        try {
                            displayReservationDetails(reservation);
                            System.out.println("What would you like to update?");
                            displayUpdateReservationOptions();
                            view = updateReservationCommand.execute();
                        } catch (InvalidReservationException e) {
                            System.out.println(e.getMessage());
                        }
                        
                    } while (view != MenuView.PREVIOUS_MENU);
                } catch (InvalidReservationException e) {
                    System.out.println(e.getMessage());
                }

                view = MenuView.RESERVATIONS;
                break;
            case 4:
                // Remove Reservation
                System.out.println("Which Reservation would you like to remove?");
                sc.nextLine(); // Throw away \n in buffer
                System.out.printf("Name: ");
                name = sc.nextLine();
                System.out.printf("Contact: ");
                contact = sc.nextLine();
                
                reservationPeriod = format(sc, "Reservation Period");

                removeReservationCommand = new RemoveReservationCommand(
                    restaurantManager.getSubManager("reservationManager", ReservationManager.class), 
                    name, contact, reservationPeriod);

                try {
                    removeReservationCommand.execute();
                } catch (InvalidReservationException e) {
                    System.out.println(e.getMessage());
                }
                
                view = MenuView.RESERVATIONS;
                break;
            case 5:
                // Back
                view = MenuView.PREVIOUS_MENU;
                break;
        }

        return view;
    }

    // public MenuView createReservation(Scanner sc) {
    //     System.out.println("Creating a new Reservation");
    //     sc.nextLine(); // Throw away \n in buffer
    //     System.out.printf("Name: ");
    //     String name = sc.nextLine();
    //     System.out.printf("Contact: ");
    //     String contact = sc.nextLine();
    //     System.out.printf("Pax: ");
    //     int pax = sc.nextInt();
    //     sc.nextLine(); // Throw away \n in buffer
        
    //     GregorianCalendar reservationPeriod = formatReservationPeriod(sc);

    //     try {
    //         restaurantManager.addReservation(reservationPeriod, pax, name, contact);
    //     } catch (Exception e) {
    //         System.out.println(e.getMessage());
    //     } 

    //     return MenuView.MENU_ITEMS;
    // }

    // public MenuView checkReservation(Scanner sc) {
    //     System.out.println("Which Reservation are you looking for?");
    //     sc.nextLine(); // Throw away \n in buffer
    //     System.out.printf("Name: ");
    //     String name = sc.nextLine();
    //     System.out.printf("Contact: ");
    //     String contact = sc.nextLine();

    //     GregorianCalendar reservationPeriod = formatReservationPeriod(sc);

    //     Reservation reservation;

    //     try {
    //         reservation = restaurantManager.getReservationDetails(name, contact, reservationPeriod);
    //         displayReservationDetails(reservation);
    //     } catch (Exception e) {
    //         System.out.println(e.getMessage());
    //     }

    //     return MenuView.MENU_ITEMS;
    // }

    // public MenuView removeReservation(Scanner sc) {
    //     System.out.println("Which Reservation would you like to remove?");
    //     sc.nextLine(); // Throw away \n in buffer
    //     System.out.printf("Name: ");
    //     String name = sc.nextLine();
    //     System.out.printf("Contact: ");
    //     String contact = sc.nextLine();
        
    //     GregorianCalendar reservationPeriod = formatReservationPeriod(sc);

    //     try {
    //         restaurantManager.removeReservation(name, contact, reservationPeriod);
    //         System.out.println("Reservation successfully removed!");
    //     } catch (Exception e) {
    //         System.out.println(e.getMessage());
    //     }

    //     return MenuView.MENU_ITEMS;
    // }

    // public MenuView updateReservation(Scanner sc) {
    //     // Similar to checkReservation (Can refactor)
    //     MenuView view = MenuView.CURRENT_MENU;
    //     System.out.println("Which Reservation would you like to update?");
    //     sc.nextLine(); // Throw away \n in buffer
    //     System.out.printf("Name: ");
    //     String name = sc.nextLine();
    //     System.out.printf("Contact: ");
    //     String contact = sc.nextLine();

    //     GregorianCalendar reservationPeriod = formatReservationPeriod(sc);

    //     try {
    //         Reservation reservation = restaurantManager.getReservationDetails(name, contact, reservationPeriod);
    //         do {
    //             displayReservationDetails(reservation);
    //             System.out.println("What would you like to update?");
    //             displayUpdateReservationOptions();
    //             int choice = sc.nextInt();
    //             switch (choice) {
    //                 case 1:
    //                     System.out.printf("Name: ");
    //                     sc.nextLine(); // Throw away \n in buffer
    //                     String newName = sc.nextLine();
    //                     restaurantManager.updateReservation(reservation, 1, newName);
    //                     break;
    //                 case 2: 
    //                     System.out.printf("Contact: ");
    //                     sc.nextLine(); // Throw away \n in buffer
    //                     String newContact = sc.nextLine();
    //                     restaurantManager.updateReservation(reservation, 2, newContact);
    //                     break;
    //                 case 3:
    //                     System.out.printf("Pax: ");
    //                     int newPax = sc.nextInt();
    //                     try {
    //                         restaurantManager.updateReservation(reservation, 1, newPax);
    //                     } catch (Exception e) {
    //                         System.out.println(e.getMessage());
    //                     }
                        
    //                     break;
    //                 case 4:
    //                     System.out.printf("Table No: ");
    //                     int newTableNo = sc.nextInt();
    //                     try {
    //                         restaurantManager.updateReservation(reservation, 2, newTableNo);
    //                     } catch (IllegalArgumentException e) {
    //                         System.out.println(e.getMessage());
    //                     } catch (ReservationsFullException e) {
    //                         System.out.println(e.getMessage());
    //                     }
                        
    //                     break;
    //                 case 5:
    //                     GregorianCalendar newReservationPeriod = formatReservationPeriod(sc);
    //                     try {
    //                         restaurantManager.updateReservation(reservation, newReservationPeriod);
    //                     } catch (InvalidReservationException e) {
    //                         System.out.println(e.getMessage());
    //                     }
                        
    //                     break;
    //                 case 6:
    //                     view = MenuView.PREVIOUS_MENU;
    //                     break;
    //             }
    //         } while (view != MenuView.PREVIOUS_MENU);
    //     } catch (NoSuchElementException e) {
    //         System.out.println(e.getMessage());
    //     }

    //     return MenuView.MENU_ITEMS;
    // }
}
