package com.CZ2002;

import com.CZ2002.project_boundaries.RestaurantManager;
import com.CZ2002.project_displays.GeneralConsole;
import com.CZ2002.project_entities.Restaurant;

import java.util.Scanner;

public class Main {
    public static  void main(String[] args) {
        Restaurant restaurant = new Restaurant("Best Burger Restaurant", 100, 10, 10);
        RestaurantManager restaurantManager = new RestaurantManager(restaurant);
        Scanner sc = new Scanner(System.in);

        GeneralConsole console = new GeneralConsole(restaurantManager, sc);
        // TODO: NOT TESTED TO BE DISCUSSED
    }
}
