package RestaurantClasses;

import java.util.GregorianCalendar;

public class Reservation {
    private GregorianCalendar reservationPeriod;
    private int tableSize;
    private int pax;
    private String name;
    private String contactNumber;
    private int reservationID; // Might want to use a better datatype for hashing

    public Reservation(GregorianCalendar reservationPeriod, int tableSize, int pax, String name, String contactNumber) {
        this.reservationPeriod = reservationPeriod;
        this.tableSize = tableSize;
        this.pax = pax;
        this.name = name;
        this.contactNumber = contactNumber;
        // TODO: Implement reservationID auto generation
    }

    public int getPax() {
        return pax;
    }

    public void setPax(int pax) {
        this.pax = pax;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTableSize() {
        return tableSize;
    }

    public void setTableSize(int tableSize) {
        this.tableSize = tableSize;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    // These 2 methods need to be moved to another class
    public void addReservation(GregorianCalendar reservationPeriod, int tableSize, String name, String contactNumber) {
        // Do we need this?
    }
    
    public void deleteReservation(int reservationID) {
        // Do we need this?
    }
}
