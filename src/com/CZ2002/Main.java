package com.CZ2002;


import java.text.ParseException;
import java.util.Scanner;

import com.CZ2002.consoles.GeneralConsole;
import com.CZ2002.entities.Restaurant;
import com.CZ2002.enums.MenuView;
import com.CZ2002.exceptions.InvalidSalesRevenueQueryException;
import com.CZ2002.exceptions.InvalidStaffException;
import com.CZ2002.managers.RestaurantManager;
import com.CZ2002.utils.DataStore;

/**
 * The main driver code that represents the initial entry point.
 * 
 * This classes instantiates the necessary classes required and
 * calls {@link com.CZ2002.interfaces.IMainManager#shutdown()}
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
