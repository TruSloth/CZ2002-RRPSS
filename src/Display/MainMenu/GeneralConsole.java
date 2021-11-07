package Display.MainMenu;

import java.util.Scanner;

import Display.ConsoleDisplay;
import Display.Reservations.ReservationConsole;
import ManagerClasses.RestaurantManager;
import Utils.MenuBuilder;
import Utils.MenuView;

/**
 * The main interface that the user interacts with.
 * <p>
 * This represents the first screen that the user interacts with and links to all other {@link ConsoleDisplay} instances.
 */
public class GeneralConsole extends ConsoleDisplay {
    private ReservationConsole reservationConsole;
    // TODO: Add other consoles

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
    }

    
    /** 
     * Accepts input from the user surrounding the possible actions the user can take in relation
     * to the entire program.
     *
     */
    @Override
    public MenuView handleConsoleOptions() {
        MenuView view = MenuView.CURRENT_MENU;
    
        int choice = sc.nextInt();
        
        switch (choice) {
            case 1:
                // Menu Items
                view = MenuView.MENU_ITEMS;
                break;
            case 2:
                // Promotions
                view = MenuView.PROMOTIONS;
                break;
            case 3:
                // Orders
                view = MenuView.ORDERS;
                break;
            case 4:
                // Reservations
                view = MenuView.RESERVATIONS;
                do {
                    reservationConsole.displayConsoleOptions();
                    view = reservationConsole.handleConsoleOptions();
                } while (view != MenuView.PREVIOUS_MENU);
            
                break;
            case 5:
                // Sales Revenue Report
                break;
            case 6:
                // Quit
                view = MenuView.PROGRAM_END;
                break;
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
            "Promotions",
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
