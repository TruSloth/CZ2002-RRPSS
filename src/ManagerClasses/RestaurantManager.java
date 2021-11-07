package ManagerClasses;

import RestaurantClasses.Restaurant;

/**
 * The {@code RestaurantManager} class provides the access point to all {@link Manager} classes the Restaurant uses.
 */
public class RestaurantManager implements iMainManager {
    // TODO: Documentation
    // TODO: Add other manager classes

    private Restaurant restaurant;

    /**
     * Initialises this {@code RestaurantManager} and the {@link Manager} instances it handles.
     * 
     * @param restaurant  the reference to the {@link Restaurant} instance
     */
    public RestaurantManager(Restaurant restaurant) {
        this.restaurant = restaurant;

        subManagers.putIfAbsent("reservationManager", new ReservationManager());

        int numOfTables = restaurant.getNumOfTables();

        // (2,4,6,8,10-seater proportions in 20%, 40%, 20%, 10%, 10%)
        subManagers.putIfAbsent("tableManager", new TableManager(numOfTables, numOfTables / 5, numOfTables / 5 * 2 , numOfTables / 5, numOfTables / 10, numOfTables / 10));
    }

    /**
     * The method to be called when the option to 'quit' is selected.
     * <p>
     * This method must be called for the program to shutdown successfully.
     */
    public void shutdown() {
        // Called when option to quit is selected.
        getSubManager("reservationManager", ReservationManager.class).cancelAllReservationFutures();
    }

    @Override
    public <T> T getSubManager(String manager, Class<? extends T> type) {
        return type.cast(subManagers.get(manager));
    }
}
