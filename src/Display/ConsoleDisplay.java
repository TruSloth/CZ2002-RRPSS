package Display;

import java.util.Scanner;

import ManagerClasses.RestaurantManager;
import Utils.MenuView;

public abstract class ConsoleDisplay {
    protected RestaurantManager restaurantManager;

    public abstract int displayConsoleOptions();

    public abstract MenuView handleConsoleOptions(Scanner sc);
}
