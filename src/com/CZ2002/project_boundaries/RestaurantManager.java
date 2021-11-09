package com.CZ2002.project_boundaries;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.CZ2002.DataStore;
import com.CZ2002.project_entities.Restaurant;
import com.CZ2002.project_enums.Gender;
import com.CZ2002.project_enums.Type;
import com.CZ2002.project_interfaces.IMainManager;

/**
 * The {@code RestaurantManager} class provides the access point to all {@link Manager} classes the Restaurant uses.
 */
public class RestaurantManager implements IMainManager {
    // TODO: Documentation
    private Restaurant restaurant;

    /**
     * Initialises this {@code RestaurantManager} and the {@link Manager} instances it handles.
     *
     * @param restaurant  the reference to the {@link Restaurant} instance
     */
    public RestaurantManager(Restaurant restaurant) {
        this.restaurant = restaurant;
        int numOfTables = restaurant.getNumOfTables();

        // Load MenuManager
        try {
            Path menuDataPath = Paths.get(DataStore.getDataDirPath().toString(), "menuData.txt");
            subManagers.putIfAbsent("menuManager", DataStore.loadManagerFromFile(menuDataPath));
        } catch (FileNotFoundException e) {
            subManagers.putIfAbsent("menuManager", new MenuManager());

            getSubManager("menuManager", MenuManager.class).addAlaCarteItem("Battered Fish & Chips", "Battered fish fillets lightly seasoned, fried to a crisp golden brown and served with creamy tartar sauce.", 12.5, Type.MAIN);
            getSubManager("menuManager", MenuManager.class).addAlaCarteItem("Battered Fish & Chips", "Battered fish fillets lightly seasoned, fried to a crisp golden brown and served with creamy tartar sauce.", 12.5, Type.MAIN);
            getSubManager("menuManager", MenuManager.class).addAlaCarteItem("BBQ Chicken", "Super tender juicy grilled chicken generously brushed with our in-house guava BBQ sauce.", 12.50, Type.MAIN);
            getSubManager("menuManager", MenuManager.class).addAlaCarteItem("Grilled Fish Sambal", "Tender fish fillet seasoned with spices for a mildly fiery kick, grilled to perfection.", 13.50, Type.MAIN);
            getSubManager("menuManager", MenuManager.class).addAlaCarteItem("Lamb Chops", "Cuts of grilled lamb marinated with our homemade herbs and spices.", 14.00, Type.MAIN);
            getSubManager("menuManager", MenuManager.class).addAlaCarteItem("Ribeye Steak", "Grilled ribeye steak with choice of pineapple BBQ, sambal or black pepper sauce.", 18.00,Type.MAIN);
            getSubManager("menuManager", MenuManager.class).addAlaCarteItem("Cookie Summit", "Mountain of Cookies 'N' Cream and Butterscotch ice cream, covered by cold fudge and chocolate chips.", 3.00, Type.DESSERT);
            getSubManager("menuManager", MenuManager.class).addAlaCarteItem("Double Berry", "Strawberry ice cream with layers of blueberry topping.", 3.00, Type.DESSERT);
            getSubManager("menuManager", MenuManager.class).addAlaCarteItem("Mango Peach Tropics", "Mango and peach flavoured sparkling drink.", 3.00, Type.DRINK);
            getSubManager("menuManager", MenuManager.class).addAlaCarteItem("Sparkling Pink Lemonade", "Lemon and strawberry flavoured sparkling drink.", 3.00, Type.DRINK);
        }

        // Load SalesRevenueManager
        try {
            Path salesRevenueDataPath = Paths.get(DataStore.getDataDirPath().toString(), "salesRevenueData.txt");
            subManagers.putIfAbsent("salesRevenueManager", DataStore.loadManagerFromFile(salesRevenueDataPath));
        } catch (FileNotFoundException e) {
            subManagers.putIfAbsent("salesRevenueManager", new SalesRevenueManager());
        }

        // Load OrderManager
        try {
            Path orderDataPath = Paths.get(DataStore.getDataDirPath().toString(), "orderData.txt");
            subManagers.putIfAbsent("orderManager", DataStore.loadManagerFromFile(orderDataPath));
        } catch (FileNotFoundException e) {
            subManagers.putIfAbsent("orderManager", new OrderManager());
        }

        // Load ReservationManager
        try {
            Path reservationDataPath = Paths.get(DataStore.getDataDirPath().toString(), "reservationData.txt");
            subManagers.putIfAbsent("reservationManager", DataStore.loadManagerFromFile(reservationDataPath));
            getSubManager("reservationManager", ReservationManager.class).startAllReservationFutures(); // Restart all Reservation Futures
        } catch (FileNotFoundException e) {
            subManagers.putIfAbsent("reservationManager", new ReservationManager());
        }

        // Load TableManager
        try {
            Path tableDataPath = Paths.get(DataStore.getDataDirPath().toString(), "tableData.txt");
            subManagers.putIfAbsent("tableManager", DataStore.loadManagerFromFile(tableDataPath));
        } catch (FileNotFoundException e) {
            // (2,4,6,8,10-seater proportions in 20%, 40%, 20%, 10%, 10%)
            subManagers.putIfAbsent("tableManager",  new TableManager(numOfTables, numOfTables / 5, numOfTables / 5 * 2 , numOfTables / 5, numOfTables / 10, numOfTables / 10));
        }

        // Load StaffManager
        try {
            Path staffDataPath = Paths.get(DataStore.getDataDirPath().toString(), "staffData.txt");
            subManagers.putIfAbsent("staffManager", DataStore.loadManagerFromFile(staffDataPath));
        } catch (FileNotFoundException e) {
            subManagers.putIfAbsent("staffManager",  new StaffManager());

            getSubManager("staffManager", StaffManager.class).addStaff("Cindy", Gender.FEMALE, "Waiter");
            getSubManager("staffManager", StaffManager.class).addStaff("John", Gender.MALE, "Waiter");
        }
    }

    /**
     * The method to be called when the option to 'quit' is selected.
     * <p>
     * This method must be called for the program to shutdown successfully.
     */
    public void shutdown() {
        // Called when option to quit is selected.
        getSubManager("reservationManager", ReservationManager.class).cancelAllReservationFutures();

        // Serialize Managers
        DataStore.saveManagerToFile(getSubManager("reservationManager", ReservationManager.class), "reservationData");
        DataStore.saveManagerToFile(getSubManager("orderManager", OrderManager.class), "orderData");
        DataStore.saveManagerToFile(getSubManager("staffManager", StaffManager.class), "staffData");
        DataStore.saveManagerToFile(getSubManager("menuManager", MenuManager.class), "menuData");
        DataStore.saveManagerToFile(getSubManager("tableManager", TableManager.class), "tableData");
        DataStore.saveManagerToFile(getSubManager("salesRevenueManager", SalesRevenueManager.class), "salesRevenueData");
    }
        

    
    @Override
    public <T> T getSubManager(String manager, Class<? extends T> type) {
        return type.cast(subManagers.get(manager));
    }
}