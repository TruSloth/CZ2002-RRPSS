package Display;

import java.util.ArrayList;
import RestaurantClasses.MenuItem;
import Utils.MenuBuilder;

public class MenuDisplay extends ConsoleDisplay {
    private ArrayList<MenuItem> menu;

    public MenuDisplay(ArrayList<MenuItem> menu) {
        this.menu = menu;

        if (menu.size() != 0) {
            super.options = new String[menu.size()];
            for (int i = 0; i < super.options.length; i++) {
                super.options[i] = String.format("%s ($%.2f)", menu.get(i).getName(), menu.get(i).getPrice());
            }

            super.title = "Menu";
        }
    }

    @Override
    public int displayMenu() {
        if (menu.size() == 0) {
            System.out.println("The menu is currently empty");
            return 0;
        }

        System.out.println(MenuBuilder.buildMenu(title, options));
        return super.options.length;
    }
}
