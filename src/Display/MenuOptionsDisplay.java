package Display;

public class MenuOptionsDisplay extends ConsoleDisplay {
    public MenuOptionsDisplay() {
        super.title = "Menu Items";
        super.options = new String[]{
                "Create Menu Item",
                "Edit Menu Item",
                "Remove Menu Item",
                "Back"
            };
    } 
}
