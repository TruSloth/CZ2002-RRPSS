package RestaurantClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
public class Restaurant {
    private final int NUM_OF_TABLES = 40; // Num of tables in restaurant
    private String name;
    private int maxCapacity; // Should be a final as well
    private int numStaff;
    private int numMenuItems; // Do we need this?
    private int occupancy;
    //private ArrayList<SalesRevenue> salesRevenueList;

    public Restaurant(String name, int maxCapacity, int numStaff, int numMenuItems) {
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.numStaff = numStaff;
        this.numMenuItems = numMenuItems;
        //this.menu = menu;
        occupancy = 0;
        //salesRevenueList = new ArrayList<SalesRevenue>(Arrays.asList(new SalesRevenue(new GregorianCalendar())));
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

    // public ArrayList<SalesRevenue> getSalesRevenueList() {
    //     return salesRevenueList;
    // }
    
    // public double getTotalSalesRevnue(GregorianCalendar startDate, GregorianCalendar endDate) {
    //     return salesRevenueList
    //     .stream()
    //     .filter(salesRevenue -> (salesRevenue.getDate().after(startDate) && salesRevenue.getDate().before(endDate)) == true)
    //     .mapToDouble(salesRevenue -> salesRevenue.calculateTotalRevenue())
    //     .sum();
    // }
}
