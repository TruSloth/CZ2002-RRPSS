package Display.MainMenu;

import java.util.Scanner;

import Display.Reservations.ReservationConsole;
import ManagerClasses.RestaurantManager;
import Utils.MenuBuilder;
import Utils.MenuView;

public class MainMenuConsole {
    private ReservationConsole reservationConsole;
    // TODO: Add other consoles

    public MainMenuConsole(RestaurantManager restaurantManager) {
        // Initialise consoles
        
        reservationConsole = new ReservationConsole(restaurantManager);
    }

    public MenuView handleMainMenuOptions(Scanner sc) {
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
                    reservationConsole.displayReservationMenuOptions();
                    view = reservationConsole.handleReservationMenuOptions(sc);
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

    public int displayMainMenuOptions() {
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
