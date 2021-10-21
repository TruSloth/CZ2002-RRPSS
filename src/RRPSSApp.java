import java.util.Scanner;

import Display.ConsoleMenuDisplay;
import Display.MenuOptionsDisplay;
import Display.OrderOptionsDisplay;
import Display.PromotionOptionsDisplay;
import Display.Reservations.ReservationOptionsDisplay;

public class RRPSSApp {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        
        RRPSS system = new RRPSS();

        RRPSS.menuView view;

        do {
            //view = system.handleMainMenuOptions(sc, system.displayMainMenuOptions());
            view = system.handleMainMenuOptions(sc, new ConsoleMenuDisplay().displayMenu());

            switch (view) {
                case MENU_ITEMS:
                    do {
                        view = system.handleMenuItemsOptions(sc, new MenuOptionsDisplay().displayMenu());
                    } while (view != RRPSS.menuView.PREVIOUS_MENU);

                    break;
                case PROMOTIONS:
                    do {
                        view = system.handlePromotionsOptions(sc, new PromotionOptionsDisplay().displayMenu());
                    } while (view != RRPSS.menuView.PREVIOUS_MENU);

                    break;
                case ORDERS:
                    do {
                        view = system.handleOrdersOptions(sc, new OrderOptionsDisplay().displayMenu());
                    } while (view != RRPSS.menuView.PREVIOUS_MENU);
                        
                    break;
                case RESERVATIONS:
                    do {
                        view = system.handleReservationsOptions(sc, new ReservationOptionsDisplay().displayMenu());
                    } while (view != RRPSS.menuView.PREVIOUS_MENU);

                    break;
                default:
            }
        } while (view != RRPSS.menuView.PROGRAM_END);
    }
}
