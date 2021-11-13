package com.CZ2002.consoles;

import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.CZ2002.commands.table.CheckTableAvailabilityCommand;
import com.CZ2002.enums.MenuView;
import com.CZ2002.exceptions.InvalidSalesRevenueQueryException;
import com.CZ2002.exceptions.InvalidStaffException;
import com.CZ2002.interfaces.ICommand;
import com.CZ2002.managers.RestaurantManager;
import com.CZ2002.managers.TableManager;
import com.CZ2002.utils.MenuBuilder;

/**
 * The main interface that the user interacts with.
 * <p>
 * This represents the first screen that the user interacts with and links to all other {@link ConsoleDisplay} instances.
 */
public class GeneralConsole extends ConsoleDisplay {
    private ReservationConsole reservationConsole;
    private MenuConsole menuConsole;
    private OrderConsole orderConsole;
    private SalesRevenueConsole salesRevenueConsole;

    /**
     * Initialises this {@code GeneralConsole} and all other {@link ConsoleDisplay} instances.
     *
     * @param restaurantManager  the reference to the {@link RestaurantManager} to be used
     * @param sc  the {@link Scanner} instance used by the boundary layer
     */
    public GeneralConsole(RestaurantManager restaurantManager, Scanner sc) {
        // Initialise consoles
        super.mainManager = restaurantManager;

        super.sc = sc;
        reservationConsole = new ReservationConsole(restaurantManager, sc);
        menuConsole = new MenuConsole(restaurantManager, sc);
        orderConsole = new OrderConsole(restaurantManager, sc);
        salesRevenueConsole = new SalesRevenueConsole(restaurantManager, sc);
    }


    /**
     * Accepts input from the user surrounding the possible actions the user can take in relation
     * to the entire program.
     *
     */
    @Override
    public MenuView handleConsoleOptions() throws InvalidStaffException, ParseException, InvalidSalesRevenueQueryException {
        MenuView view = MenuView.CURRENT_MENU;

        try {
            int choice = sc.nextInt();
        
            switch (choice) {
                case 1:
                    // Menu Items
                    do {
                        try {
                            view = MenuView.MENU_ITEMS;

                            menuConsole.displayConsoleOptions();
                            view = menuConsole.handleConsoleOptions();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid Input!");
                            sc.nextLine();
                        } 
                    } while (view != MenuView.PREVIOUS_MENU);
                    break;
                case 2:
                    // Orders
                    do {
                        try {
                            view = MenuView.ORDERS;

                            orderConsole.displayConsoleOptions();
                            view = orderConsole.handleConsoleOptions();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid Input!");
                            sc.nextLine();
                        }   
                    } while (view != MenuView.PREVIOUS_MENU);
                    break;
                case 3:
                    // Reservations
                    do {
                        try {
                            view = MenuView.RESERVATIONS;

                            reservationConsole.displayConsoleOptions();
                            view = reservationConsole.handleConsoleOptions();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid Input!");
                            sc.nextLine();
                        }
                    } while (view != MenuView.PREVIOUS_MENU);

                    break;
                case 4:
                    // Sales Revenue Report
                    do {
                        try {
                            view = MenuView.SALES_REVENUE;

                            salesRevenueConsole.displayConsoleOptions();
                            view = salesRevenueConsole.handleConsoleOptions();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid Input!");
                            sc.nextLine();
                        } 
                    } while (view != MenuView.PREVIOUS_MENU);
                    break;
                case 5:
                    // Check Table Availability
                    ICommand<String[], Exception> checkTableAvailabilityCommand = new CheckTableAvailabilityCommand(
                        mainManager.getSubManager("tableManager", TableManager.class));
                    try {
                        String[] tableStatus = checkTableAvailabilityCommand.execute();
                        displayTableAvailability(tableStatus);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case 6:
                    // Quit
                    view = MenuView.PROGRAM_END;
                    break;
            }
        } catch (InputMismatchException e){
            System.out.println("Invalid Input!");
            sc.nextLine();
        }
        catch (NoSuchElementException e) {
            view = MenuView.PROGRAM_END; // Scanner has closed.
        }
        return view;
    }

    /**
     * Formats and outputs the current occupancy status of tables in the Restaurant.
     * 
     * @param tableStatus the String array indicating the occupancy status of the {@code Table} with {@code tableNumber} equal the index position + 1
     * @return the number of tables
     */
    public int displayTableAvailability(String[] tableStatus) {
        String[] options = new String[tableStatus.length];
        String[] optionHeaders = new String[tableStatus.length];
        String title = "Table Availability";
        int LONGEST_WIDTH = 40;

        for (int i = 0; i < tableStatus.length; i++) {
            optionHeaders[i] = String.format("%d", i + 1);
            options[i] = tableStatus[i];
        }

        System.out.println(MenuBuilder.buildMenu(title, optionHeaders, options, LONGEST_WIDTH));

        return options.length;
    }


    /**
     * Formats and outputs the possible actions that can be taken on this {@code GeneralConsole}.
     *
     */
    @Override
    public int displayConsoleOptions() {
        String[] options = new String[] {
                "Menu Items",
                "Orders",
                "Reservations",
                "Sales Revenue Report",
                "Check Table Availability",
                "Quit"
        };
        String title = "Restaurant Reservation & Point of Sale System";

        System.out.println(MenuBuilder.buildMenu(title, options));

        return options.length;
    }
}