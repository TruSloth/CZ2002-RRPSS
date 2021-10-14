package RestaurantClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;

public class Restaurant {
    private final int NUM_OF_TABLES = 1000;
    private int maxCapacity; // Should be a final as well
    private String name;
    private int numStaff;
    private int numMenuItems; // Do we need this?
    private ArrayList<MenuItem> menu;
    private int occupancy;
    private Table[] tables;
    private ArrayList<SalesRevenue> salesRevenueList;

    public Restaurant(String name, int maxCapacity, int numStaff, int numMenuItems, ArrayList<MenuItem> menu) {
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.numStaff = numStaff;
        this.numMenuItems = numMenuItems;
        this.menu = menu;
        occupancy = 0;
        tables = new Table[NUM_OF_TABLES];
        salesRevenueList = new ArrayList<SalesRevenue>(Arrays.asList(new SalesRevenue(new GregorianCalendar())));
    }

    // Default getters & setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getNumOfTables() {
        return NUM_OF_TABLES;
    }

    public int getNumStaff() {
        return numStaff;
    }

    public void setNumStaff(int numStaff) {
        this.numStaff = numStaff;
    }

    public ArrayList<MenuItem> getMenu() {
        return menu;
    }

    public ArrayList<SalesRevenue> getSalesRevenueList() {
        return salesRevenueList;
    }

    // Methods
    public Table[] getOccupiedTables() {
        ArrayList<Table> tablesList = new ArrayList<Table>(Arrays.asList(tables.clone()));
        return (Table[]) tablesList.stream().filter(table -> table.isOccupied() == true).toArray();
    }

    public Table[] getUnoccupiedTables() {
        ArrayList<Table> tablesList = new ArrayList<Table>(Arrays.asList(tables.clone()));
        return (Table[]) tablesList.stream().filter(table -> table.isOccupied() == false).toArray();
    }

    public void occupyTable(int tableNumber) {
        tables[tableNumber - 1].occupyTable();
    }

    public void unoccupyTable(int tableNumber) {
        tables[tableNumber - 1].unoccupyTable();
    }

    public double getTotalSalesRevnue(GregorianCalendar startDate, GregorianCalendar endDate) {
        return salesRevenueList
        .stream()
        .filter(salesRevenue -> (salesRevenue.getDate().after(startDate) && salesRevenue.getDate().before(endDate)) == true)
        .mapToDouble(salesRevenue -> salesRevenue.calculateTotalRevenue())
        .sum();
    }
}
