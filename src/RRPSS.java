import Utils.MenuBuilder;

public class RRPSS {
    public RRPSS() {
        String[] options = {
            "Menu Items",
            "Promotions",
            "Orders",
            "Reservations",
            "Sales Revenue Report"
        };

        final String title = "Restaurant Reservation & Point of Sale System";

        System.out.println(MenuBuilder.buildMenu(title, options));
    }
}
