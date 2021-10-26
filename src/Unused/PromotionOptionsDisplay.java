package Unused;

public class PromotionOptionsDisplay extends ConsoleDisplay {
    public PromotionOptionsDisplay() {
        super.options = new String[]{
            "Create Promotion",
            "Edit Promotion",
            "Remove Promotion",
            "Back"
        };

        super.title = "Promotions";
    }
}
