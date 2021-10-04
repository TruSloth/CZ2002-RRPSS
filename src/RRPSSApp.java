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
                        view = system.handleMenuItemsOptions(sc, system.displayMenuItemsOptions());
                    } while (view != RRPSS.menuView.PREVIOUS_MENU);
                    break;
                case PROMOTIONS:
                    break;
                case ORDERS:
                    break;
                case RESERVATIONS:
                    break;
                default:
            }
        } while (view != RRPSS.menuView.PROGRAM_END);
    }
}
