package com.CZ2002.project_displays;

import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.CZ2002.project_boundaries.RestaurantManager;
import com.CZ2002.project_exceptions.InvalidSalesRevenueQueryException;
import com.CZ2002.project_exceptions.InvalidStaffException;
import com.CZ2002.project_utils.MenuBuilder;
import com.CZ2002.project_enums.MenuView;

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
                            System.out.println("Invalid input!");
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
                            System.out.println("Invalid input!");
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
                            System.out.println("Invalid input!");
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
                            System.out.println("Invalid input!");
                            sc.nextLine();
                        } 
                    } while (view != MenuView.PREVIOUS_MENU);
                    break;
                case 5:
                    // Quit
                    view = MenuView.PROGRAM_END;
                    break;
            }
        } catch (NoSuchElementException e) {
            view = MenuView.PROGRAM_END; // Scanner has closed.
        }

        return view;
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
                "Quit"
        };
        String title = "Restaurant Reservation & Point of Sale System";

        System.out.println(MenuBuilder.buildMenu(title, options));

        return options.length;
    }
}