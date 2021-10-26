package Unused;

import java.util.ArrayList;

import RestaurantClasses.MenuItem;

public class MenuItemsOptionsDisplay extends ConsoleDisplay {
    // Display for items on the menu for selection

    public MenuItemsOptionsDisplay(ArrayList<MenuItem> menu) {
        super.options = new String[menu.size() + 1];

        for (int i = 0; i < menu.size(); i++) {
            super.options[i] = String.format("%s ($%.2f)", menu.get(i).getName(), menu.get(i).getPrice());
        }

        super.options[super.options.length - 1] = "Back";

        super.title = "Menu";
    }
}
