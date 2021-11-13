package com.CZ2002.boundaries;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.CZ2002.entities.MenuItem;
import com.CZ2002.entities.Order;
import com.CZ2002.entities.Reservation;
import com.CZ2002.entities.Restaurant;
import com.CZ2002.entities.SalesRevenue;
import com.CZ2002.entities.Staff;
import com.CZ2002.entities.Table;
import com.CZ2002.enums.Gender;
import com.CZ2002.enums.Type;
import com.CZ2002.exceptions.InvalidStaffException;
import com.CZ2002.interfaces.IMainManager;
import com.CZ2002.utils.DataStore;

/**
 * The {@code RestaurantManager} class provides the access point to all {@link Manager} classes the Restaurant uses.
 * 
 * {@code RestaurantManager} additionally saves and loads the {@code Manager} instances upon start up and shutdown.
 */
public class RestaurantManager implements IMainManager {
    private Restaurant restaurant;

    @Override
    public void saveManagers() {
        for (Manager<?> manager : subManagers.values()) {
            String fileName = manager.getClass().getSimpleName();
            fileName = fileName.substring(0, 1).toLowerCase() + fileName.substring(1).replace("Manager", "") + "Data";
            DataStore.saveToFile(manager, fileName);
        }
    }

    /**
     * Initialises this {@code RestaurantManager} and the {@link Manager} instances it handles.
     * 
     * Loads the {@code Manager} instances from data files located at {@code data/} if they can be found.
     * Otherwise, the {@code Manager} instances are initialised with default {@code entities}.
     *
     * @param restaurant  the reference to the {@link Restaurant} instance
     */public RestaurantManager(Restaurant restaurant) throws InvalidStaffException {
        this.restaurant = restaurant;
        int numOfTables = restaurant.getNumOfTables();

        // Initializes a Thread with a run method that gets called when option to quit is 
        // selected or when the program is terminated (Ctrl + c).
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                saveManagers();
            }
        });

        // Load MenuManager
        try {
            Path menuDataPath = Paths.get(DataStore.getDataDirPath().toString(), "menuData.txt");
            subManagers.putIfAbsent("menuManager",(Manager<MenuItem>) DataStore.loadFromFile(menuDataPath));
        } catch (IOException e) {
            subManagers.putIfAbsent("menuManager", new MenuManager());

            // Populate MenuItems from Data Files
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

        // Load StaffManager
        try {
            Path staffDataPath = Paths.get(DataStore.getDataDirPath().toString(), "staffData.txt");
            subManagers.putIfAbsent("staffManager", (Manager<Staff>) DataStore.loadFromFile(staffDataPath));
        } catch (IOException e) {
            subManagers.putIfAbsent("staffManager",  new StaffManager());

            // Populate Staff from Data Files
            getSubManager("staffManager", StaffManager.class).addStaff("Cindy", Gender.FEMALE, "Waiter");
            getSubManager("staffManager", StaffManager.class).addStaff("John", Gender.MALE, "Waiter");
        }

        // Load SalesRevenueManager
        try {
            Path salesRevenueDataPath = Paths.get(DataStore.getDataDirPath().toString(), "salesRevenueData.txt");
            subManagers.putIfAbsent("salesRevenueManager", (Manager<SalesRevenue>) DataStore.loadFromFile(salesRevenueDataPath));
        } catch (IOException e) {
            subManagers.putIfAbsent("salesRevenueManager", new SalesRevenueManager());
        }

        // Load OrderManager
        try {
            Path orderDataPath = Paths.get(DataStore.getDataDirPath().toString(), "orderData.txt");
            subManagers.putIfAbsent("orderManager", (Manager<Order>) DataStore.loadFromFile(orderDataPath));
        } catch (IOException e) {
            subManagers.putIfAbsent("orderManager", new OrderManager());
        }

        // Load ReservationManager
        try {
            Path reservationDataPath = Paths.get(DataStore.getDataDirPath().toString(), "reservationData.txt");
            subManagers.putIfAbsent("reservationManager", (Manager<Reservation>) DataStore.loadFromFile(reservationDataPath));
            getSubManager("reservationManager", ReservationManager.class).startAllReservationFutures(); // Restart all Reservation Futures
        } catch (IOException e) {
            subManagers.putIfAbsent("reservationManager", new ReservationManager());
        }

        // Load TableManager
        try {
            Path tableDataPath = Paths.get(DataStore.getDataDirPath().toString(), "tableData.txt");
            subManagers.putIfAbsent("tableManager", (Manager<Table>) DataStore.loadFromFile(tableDataPath));
        } catch (IOException e) {
            // (2,4,6,8,10-seater proportions in 20%, 40%, 20%, 10%, 10%)
            subManagers.putIfAbsent("tableManager",  new TableManager(numOfTables, numOfTables / 5, numOfTables / 5 * 2 , numOfTables / 5, numOfTables / 10, numOfTables / 10));
        }

        
    }

    /**
     * The method to be called when the option to 'quit' is selected.
     * <p>
     * This method must be called for the program to shutdown successfully.
     */
    @Override
    public void shutdown() {
        // Called when option to quit is selected.
        getSubManager("reservationManager", ReservationManager.class).cancelAllReservationFutures();
    }

    @Override
    public <T> T getSubManager(String manager, Class<? extends T> type) {
        return type.cast(subManagers.get(manager));
    }
}