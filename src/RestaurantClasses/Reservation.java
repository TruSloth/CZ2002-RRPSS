package RestaurantClasses;

import java.util.GregorianCalendar;
import java.util.concurrent.ScheduledFuture;

/**
 * The {@code Reservation} class represents a booking made at the restaurant.
 * A reservation must be specified by a name, contact number, pax, table number and
 * reservation period and can be identified by using these attributes. 
 * <p>
 * The {@code Reservation} class is designed to only hold all data related to
 * a reservation and should not implement any logic in relation to reservations.
 */
public class Reservation extends RestaurantEntity {
    private GregorianCalendar reservationPeriod;
    private int pax;
    private String name;
    private String contactNumber;
    private int tableNo; // Might want to use a better datatype for hashing
    private ScheduledFuture<Void> expiry;

    /**
     * Constructs a new reservation that represents the booking made for a particular time.
     * This constructor initialises the expiry of the reservation to null.
     * @param reservationPeriod  the {@link java.util.GregorianCalendar} start time of this reservation
     * @param pax  the number of guests
     * @param name  the String to address the contact person
     * @param contactNumber  the String representing the phone number to contact the guest at
     * @param tableNo  the table number reserved
     */
    public Reservation(GregorianCalendar reservationPeriod, int pax, String name, String contactNumber, int tableNo) {
        this.reservationPeriod = reservationPeriod;
        this.pax = pax;
        this.name = name;
        this.contactNumber = contactNumber;
        this.tableNo = tableNo;
        expiry = null;
    }

    
    /** 
     * Returns the start time of this reservation.
     * @return the {@link java.util.GregorianCalendar} representing the start time of this reservation
     */
    public GregorianCalendar getReservationPeriod() {
        return reservationPeriod;
    }

    
    /** 
     * Updates the start time of this reservation.
     * @param reservationPeriod  the start time of this reservation
     */
    public void setReservationPeriod(GregorianCalendar reservationPeriod) {
        this.reservationPeriod = reservationPeriod;
    }

    
    /**
     * Returns the number of guests this reservation has been made for.
     * @return the number of guests in this reservation
     */
    public int getPax() {
        return pax;
    }

    
    /** 
     * Updates the number of guests in this reservation.
     * @param pax  the number of guests in this reservation
     */
    public void setPax(int pax) {
        this.pax = pax;
    }

    
    /** 
     * Returns the String representation to address the contact person.
     * @return the string to address the contact person
     */
    public String getName() {
        return name;
    }

    
    /** 
     * Updates the String representation to address the contact person.
     * @param name  the string to address the contact person
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /** 
     * Returns the String representing the phone number of the contact person.
     * @return the string representing the phone number to contact the guest at
     */
    public String getContactNumber() {
        return contactNumber;
    }

    
    /** 
     * Updates the String representing the phone number of the contact person.
     * @param contactNumber  the string representing the phone number to contact the guest at 
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    
    /** 
     * Returns the table number of the {@link RestaurantClasses.Table} reserved for this reservation.
     * @return the table number reserved
     */
    public int getTableNo() {
        return tableNo;
    }

    
    /** 
     * Updates the table number of the {@link RestaurantClasses.Table} reserved for this reservation.
     * @param tableNo  the table number reserved
     */
    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }

    
    /** 
     * Returns the {@code ScheduledFuture} that triggers this reservation's expiry.
     * The expiry should be cancelled by calling {@link java.util.concurrent.Future#cancel(boolean)}
     * on the {@code ScheduledFuture} returned by this method if this reservation is 
     * cancelled or its reservation period has been changed.
     * @return the {@code ScheduledFuture} representing the pending expiry
     * of a reservation
     */
    public ScheduledFuture<Void> getExpiry() {
        return expiry;
    }

    
    /** 
     * Updates the reference to the {@code ScheduledFuture} representing this reservation's expiry.
     * @param expiry  the {@code ScheduledFuture} representing the result of this reservation's expiry
     */
    public void setExpiry(ScheduledFuture<Void> expiry) {
        this.expiry = expiry;
    }
}
