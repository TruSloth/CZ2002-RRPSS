package RestaurantClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;

import ManagerClasses.ReservationManager;
import ManagerClasses.TableManager;

public class Restaurant {
    private final int NUM_OF_TABLES = 1000;
    private String name;
    private int maxCapacity; // Should be a final as well
    private int numStaff;
    private int numMenuItems; // Do we need this?
    private ArrayList<MenuItem> menu;
    private int occupancy;
    private ArrayList<SalesRevenue> salesRevenueList;
    private ReservationManager reservationManager;
    private TableManager tableManager;

    public Restaurant(String name, int maxCapacity, int numStaff, int numMenuItems, ArrayList<MenuItem> menu) {
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.numStaff = numStaff;
        this.numMenuItems = numMenuItems;
        this.menu = menu;
        occupancy = 0;
        salesRevenueList = new ArrayList<SalesRevenue>(Arrays.asList(new SalesRevenue(new GregorianCalendar())));
        reservationManager = new ReservationManager();
        tableManager = new TableManager(NUM_OF_TABLES, 200, 400, 200, 100, 100);
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

    // Reservation Methods
    public void addReservation(GregorianCalendar reservationPeriod, int pax, String name, String contact) {
        int[] unavailableTableNos = reservationManager.getUnavailableTables(reservationPeriod);
        int bookedTableNo = tableManager.bookTable(unavailableTableNos, pax);
        reservationManager.createNewReservation(reservationPeriod, pax, name, contact, bookedTableNo);
    }

    public Reservation getReservationDetails(String name, String contact, GregorianCalendar reservationPeriod) {
        return reservationManager.findReservation(name, contact, reservationPeriod);
    }

    public boolean removeReservation(String name, String contact, GregorianCalendar reservationPeriod) {
        Reservation reservation = reservationManager.findReservation(name, contact, reservationPeriod);
        if (reservation == null) {
            return false;
        }
        tableManager.unbookTable(reservation.getTableNo());
        reservationManager.deleteReservation(reservation);
        return true;
    }

    public boolean updateReservation(String name, String contact, GregorianCalendar oldReservationPeriod, int choice, String newField) {
        switch (choice) {
            case 1: // Name
                return reservationManager.modifyReservationName(name, contact, oldReservationPeriod, newField);
            case 2: // Contact
                return reservationManager.modifyReservationContact(name, contact, oldReservationPeriod, newField);
            default:
                return false;
        }
    }

    public boolean updateReservation(String name, String contact, GregorianCalendar oldReservationPeriod, int choice, int newField) {
        switch (choice) {
            case 1: // Pax
                return reservationManager.modifyReservationPax(name, contact, oldReservationPeriod, newField);
            case 2: // TableNo
                return reservationManager.modifyReservationTableNo(name, contact, oldReservationPeriod, newField);
            default:
                return false;
        }
    }

    public boolean updateReservation(String name, String contact, GregorianCalendar oldReservationPeriod, GregorianCalendar newReservationPeriod) {
        return reservationManager.modifyReservationPeriod(name, contact, oldReservationPeriod, newReservationPeriod);
    }

    
    public double getTotalSalesRevnue(GregorianCalendar startDate, GregorianCalendar endDate) {
        return salesRevenueList
        .stream()
        .filter(salesRevenue -> (salesRevenue.getDate().after(startDate) && salesRevenue.getDate().before(endDate)) == true)
        .mapToDouble(salesRevenue -> salesRevenue.calculateTotalRevenue())
        .sum();
    }
}
