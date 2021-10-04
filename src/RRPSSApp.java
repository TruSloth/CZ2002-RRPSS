import java.util.Scanner;

public class RRPSSApp {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        
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
