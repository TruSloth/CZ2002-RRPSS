package com.CZ2002;


import java.text.ParseException;
import java.util.Scanner;

import com.CZ2002.boundaries.RestaurantManager;
import com.CZ2002.consoles.GeneralConsole;
import com.CZ2002.entities.Restaurant;
import com.CZ2002.enums.MenuView;
import com.CZ2002.exceptions.InvalidSalesRevenueQueryException;
import com.CZ2002.exceptions.InvalidStaffException;

public class Main {
    public static void main(String[] args) throws InvalidStaffException, ParseException, InvalidSalesRevenueQueryException {
        Scanner sc = new Scanner(System.in);

        Restaurant restaurant = new Restaurant("RestaurantA", 40, 5);
        RestaurantManager restaurantManager = new RestaurantManager(restaurant);
        GeneralConsole mainMenuConsole = new GeneralConsole(restaurantManager, sc);

        MenuView view = MenuView.CURRENT_MENU;

        do {
            mainMenuConsole.displayConsoleOptions();
            view = mainMenuConsole.handleConsoleOptions();
        } while (view != MenuView.PROGRAM_END);

        restaurantManager.shutdown();
    }
}
