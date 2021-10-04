import java.util.Scanner;

public class RRPSSApp {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        
        RRPSS system = new RRPSS();

        RRPSS.menuView view;

        do {
            view = system.handleMainMenuOptions(sc, system.displayMainMenuOptions());
        } while (view != RRPSS.menuView.PREVIOUS_MENU);
    }
}
