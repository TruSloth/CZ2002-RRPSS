package com.CZ2002;


import java.text.ParseException;
import java.util.Scanner;

import com.CZ2002.project_boundaries.RestaurantManager;
import com.CZ2002.project_displays.GeneralConsole;
import com.CZ2002.project_entities.Restaurant;
import com.CZ2002.project_enums.MenuView;
import com.CZ2002.project_exceptions.InvalidSalesRevenueQueryException;
import com.CZ2002.project_exceptions.InvalidStaffException;

public class Main {
    public static void main(String[] args) throws InvalidStaffException, ParseException, InvalidSalesRevenueQueryException {
        Scanner sc = new Scanner(System.in);

        Restaurant restaurant = new Restaurant("RestaurantA", 40, 5, 10);
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
