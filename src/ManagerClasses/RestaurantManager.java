package ManagerClasses;

import RestaurantClasses.Restaurant;

public class RestaurantManager implements iMainManager {
    // TODO: Add other manager classes

    private Restaurant restaurant;

    public RestaurantManager(Restaurant restaurant) {
        this.restaurant = restaurant;

        subManagers.putIfAbsent("reservationManager", new ReservationManager());

        int numOfTables = restaurant.getNumOfTables();

        subManagers.putIfAbsent("tableManager", new TableManager(numOfTables, numOfTables / 5, numOfTables / 5 * 2 , numOfTables / 5, numOfTables / 10, numOfTables / 10)); // (2,4,6,8,10-seater proportions in 20%, 40%, 20%, 10%, 10%)
    }

    public void shutdown() {
        // Called when option to quit is selected.
        //reservationManager.cancelAllReservationFutures();
        getSubManager("reservationManager", ReservationManager.class).cancelAllReservationFutures();
    }

    @Override
    public <T> T getSubManager(String manager, Class<? extends T> type) {
        return type.cast(subManagers.get(manager));
    }
}
