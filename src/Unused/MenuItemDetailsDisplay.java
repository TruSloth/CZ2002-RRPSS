package Unused;

import RestaurantClasses.MenuItem;
import Utils.MenuBuilder;

public class MenuItemDetailsDisplay extends ConsoleDisplay {
    // Display for individual menu item and its details

    final int LONGEST_WIDTH = 20;
    final String[] optionHeaders = {"Price", "Description"};

    public MenuItemDetailsDisplay(MenuItem item) {
        super.options = new String[] {String.format("$%.2f", item.getPrice()), item.getDescription()};
        super.title = item.getName();
    }

    @Override
    public int displayMenu() {
        System.out.println(MenuBuilder.buildMenu(title, optionHeaders, options, LONGEST_WIDTH));

        return super.options.length;
    }
}
