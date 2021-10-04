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
        NEXT_MENU
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
        menuView view = menuView.NEXT_MENU;

        switch (choice) {
            case 1:
                // Menu Items
                break;
            case 2:
                // Promotions
                break;
            case 3:
                // Orders
                break;
            case 4:
                // Reservations
                break;
            case 5:
                // Sales Revenue Report
                break;
            case 6:
                // Quit
                view = menuView.PREVIOUS_MENU;
                break;
            default:
                view = menuView.CURRENT_MENU;
        }

        return view;
    }
}
