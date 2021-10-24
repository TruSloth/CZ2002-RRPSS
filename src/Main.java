import java.util.Scanner;

import Display.MainMenu.MainMenuConsole;
import ManagerClasses.RestaurantManager;
import RestaurantClasses.Restaurant;
import Utils.MenuView;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        
        Restaurant restaurant = new Restaurant("RestaurantA", 40, 5, 10);
        RestaurantManager restaurantManager = new RestaurantManager(restaurant);
        MainMenuConsole mainMenuConsole = new MainMenuConsole(restaurantManager);

        MenuView view = MenuView.CURRENT_MENU;
        
        do {
            mainMenuConsole.displayMainMenuOptions();
            view = mainMenuConsole.handleMainMenuOptions(sc);
        } while (view != MenuView.PROGRAM_END);

        restaurantManager.shutdown();
    }
}
