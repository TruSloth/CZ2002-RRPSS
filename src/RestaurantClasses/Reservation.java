package RestaurantClasses;

import java.util.GregorianCalendar;

public class Reservation {
    private GregorianCalendar reservationPeriod;
    private int pax;
    private String name;
    private String contactNumber;
    private int tableNo; // Might want to use a better datatype for hashing

    public Reservation(GregorianCalendar reservationPeriod, int pax, String name, String contactNumber, int tableNo) {
        this.reservationPeriod = reservationPeriod;
        this.pax = pax;
        this.name = name;
        this.contactNumber = contactNumber;
        this.tableNo = tableNo;
    }

    public GregorianCalendar getReservationPeriod() {
        return reservationPeriod;
    }

    public void setReservationPeriod(GregorianCalendar reservationPeriod) {
        this.reservationPeriod = reservationPeriod;
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

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public int getTableNo() {
        return tableNo;
    }

    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }
}