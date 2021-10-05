import java.util.Arrays;
import java.util.Scanner;

import Utils.MenuBuilder;

public class RRPSSApp {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        //String text = "The quick brown fox jumped over the scaly rock into the ocean";

        //System.out.println(MenuBuilder.fullJustify(text.split(" "), 10));

        // String name = "Steak";
        // double price = 10.99;
        // String description = "A steak is a meat generally sliced across the muscle fibers, potentially including a bone. It is normally grilled, though can also be pan-fried. ...For some meats, such as pork, lamb and mutton, chevon, and veal, these cuts are often referred to as chops. Some cured meat, such as gammon, is commonly served as steak.";
        
        // String[] optionHeaders = {"Name", "Price", "Description"};
        // String[] options = {name, String.valueOf(price), description};

        // System.out.println(MenuBuilder.buildMenu("Menu", optionHeaders, options, 15));

        RRPSS system = new RRPSS();

        RRPSS.menuView view;

        do {
            view = system.handleMainMenuOptions(sc, system.displayMainMenuOptions());

            switch (view) {
                case MENU_ITEMS:
                    do {
                        view = system.handleMenuItemsOptions(sc, system.displayMenuOptions());
                    } while (view != RRPSS.menuView.PREVIOUS_MENU);

                    break;
                case PROMOTIONS:
                    do {
                        view = system.handlePromotionsOptions(sc, system.displayPromotionsOptions());
                    } while (view != RRPSS.menuView.PREVIOUS_MENU);

                    break;
                case ORDERS:
                    do {
                        view = system.handleOrdersOptions(sc, system.displayOrdersOptions());
                    } while (view != RRPSS.menuView.PREVIOUS_MENU);
                        
                    break;
                case RESERVATIONS:
                    do {
                        view = system.handleReservationsOptions(sc, system.displayReservationsOptions());
                    } while (view != RRPSS.menuView.PREVIOUS_MENU);

                    break;
                default:
            }
        } while (view != RRPSS.menuView.PROGRAM_END);
    }
}
