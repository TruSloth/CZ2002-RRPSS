package Display;

import java.util.Scanner;

import ManagerClasses.RestaurantManager;
import Utils.MenuView;

public abstract class ConsoleDisplay {
    protected RestaurantManager restaurantManager;
    protected Scanner sc;

    public abstract int displayConsoleOptions();

    public abstract MenuView handleConsoleOptions();
}
