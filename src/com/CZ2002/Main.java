package com.CZ2002;


import java.text.ParseException;
import java.util.Scanner;

import com.CZ2002.project_consoles.GeneralConsole;
import com.CZ2002.project_entities.Restaurant;
import com.CZ2002.project_enums.MenuView;
import com.CZ2002.project_exceptions.InvalidSalesRevenueQueryException;
import com.CZ2002.project_exceptions.InvalidStaffException;
import com.CZ2002.project_managers.RestaurantManager;
import com.CZ2002.utils.DataStore;

/**
 * The main driver code that represents the initial entry point.
 * 
 * This classes instantiates the necessary classes required and
 * calls {@link com.CZ2002.project_interfaces.IMainManager#shutdown()}
 * when the program is about to exit.
 */
public class Main {
    public static void main(String[] args) throws InvalidStaffException, ParseException, InvalidSalesRevenueQueryException {
        Scanner sc = new Scanner(System.in);

        DataStore ds = new DataStore();

        Restaurant restaurant = new Restaurant("RestaurantA", 40, 5);
        RestaurantManager restaurantManager = new RestaurantManager(restaurant, ds);
        GeneralConsole mainMenuConsole = new GeneralConsole(restaurantManager, sc);

        MenuView view = MenuView.CURRENT_MENU;

        do {
            mainMenuConsole.displayConsoleOptions();
            view = mainMenuConsole.handleConsoleOptions();
        } while (view != MenuView.PROGRAM_END);

        restaurantManager.shutdown();
    }
}
