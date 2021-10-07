package Display;

public class OrderOptionsDisplay extends ConsoleDisplay {
    public OrderOptionsDisplay() {
        super.options = new String[]{
            "Create Order",
            "View Order",
            "Edit Order",
            "Back"
        };

        super.title = "Orders";
    }
}
