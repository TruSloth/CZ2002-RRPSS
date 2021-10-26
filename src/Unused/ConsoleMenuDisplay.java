package Unused;

public class ConsoleMenuDisplay extends ConsoleDisplay{
    public ConsoleMenuDisplay() {
        super.title = "Restaurant Reservation & Point of Sale System";
        super.options = new String[]{
                "Menu Items",
                "Promotions",
                "Orders",
                "Reservations",
                "Sales Revenue Report",
                "Quit"
            };
    } 
}
