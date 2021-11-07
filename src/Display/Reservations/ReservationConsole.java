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


/**
 * The interface that the user interacts with for {@link Reservation} instances.
 */
public class ReservationConsole extends ConsoleDisplay implements iGregorianCalendarFormatter {
    // Declare all available commands here

    private iCommand<Void, InvalidReservationException> addReservationCommand;

    private iCommand<Reservation, InvalidReservationException> findReservationCommand;

    private iCommand<MenuView, InvalidReservationException> updateReservationCommand;

    private iCommand<Boolean, InvalidReservationException> removeReservationCommand;

    /**
     * Initalises this {@code ReservationConsole} with the reference to the {@link RestaurantManager}.
     * 
     * @param restaurantManager  the reference to the {@code RestaurantManager} to be used
     * @param sc  the {@link Scanner} instance used by the boundary layer
     */
    public ReservationConsole(RestaurantManager restaurantManager, Scanner sc) {
        super.sc = sc;
        super.mainManager = restaurantManager;
    }

    /**
     * Formats and outputs the information enclosed in given {@link Reservation} instance.
     * 
     * @param reservation  the {@code Reservation} whose details are to be shown
     * @return  the number of options used
     */
    public int displayReservationDetails(Reservation reservation) {
        final int LONGEST_WIDTH = 20; // This defines the maximum number of characters that an option can take, beyond which it will be justified
        String[] optionHeaders = {"Name", "Contact", "Pax", "Table No.", "Reservation Period"};

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
        LocalDateTime ldt = reservation.getReservationPeriod().toInstant().atZone(ZoneId.of("Asia/Singapore")).toLocalDateTime();
        String[] options = new String[] {reservation.getName(), reservation.getContactNumber(), String.valueOf(reservation.getPax()), String.valueOf(reservation.getTableNo()), fmt.format(ldt)};
        String title = "Reservation Details";

        System.out.println(MenuBuilder.buildMenu(title, optionHeaders, options, LONGEST_WIDTH));

        return options.length;
    }

    /**
     * Formats and outputs the possible actions that can be taken on this {@code ReservationConsole}.
     * 
     * @see ConsoleDisplay
     */
    @Override
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

    /**
     * Formats and outputs the possible ways to update a {@link Reservation} instance.
     * 
     * @return  the number of options to be displayed
     */
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

    /**
     * Accepts input from the user surrounding the possible actions the user can take
     * in relation to {@link Reservation} instances.
     * 
     */
    @Override
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
                    mainManager.getSubManager("reservationManager", ReservationManager.class),
                    mainManager.getSubManager("tableManager", TableManager.class),
                    name, contact, pax, reservationPeriod);
                try {
                    addReservationCommand.execute();
                } catch (InvalidReservationException e) {
                    System.out.println(e.getMessage());
                }
                
                view = MenuView.RESERVATIONS;
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
                    mainManager.getSubManager("reservationManager", ReservationManager.class), 
                    name, contact, reservationPeriod);

                try {
                    reservation = findReservationCommand.execute();
                    displayReservationDetails(reservation);
                } catch (InvalidReservationException e) {
                    System.out.println(e.getMessage());
                }

                view = MenuView.RESERVATIONS;
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
                    mainManager.getSubManager("reservationManager", ReservationManager.class), 
                    name, contact, reservationPeriod);

                try {
                    reservation = findReservationCommand.execute();

                    updateReservationCommand = new UpdateReservationCommand(
                    mainManager.getSubManager("reservationManager", ReservationManager.class),
                    mainManager.getSubManager("tableManager", TableManager.class), 
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
                    mainManager.getSubManager("reservationManager", ReservationManager.class), 
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
}
