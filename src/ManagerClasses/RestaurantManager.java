package ManagerClasses;

import java.util.GregorianCalendar;

import RestaurantClasses.Reservation;
import RestaurantClasses.Restaurant;

public class RestaurantManager {
    private ReservationManager reservationManager;
    private TableManager tableManager;
    // TODO: Add other manager classes

    private Restaurant restaurant;

    public RestaurantManager(Restaurant restaurant) {
        this.restaurant = restaurant;

        reservationManager = new ReservationManager();
        tableManager = new TableManager(restaurant.getNumOfTables(), 200, 400, 200, 100, 100); // (2,4,6,8,10-seater proportions in 20%, 40%, 20%, 10%, 10%)
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

    // Better way to handle updating of reservation? 
    public boolean updateReservation(Reservation reservation, int choice, String newField) {
        switch (choice) {
            case 1: // Name
                return reservationManager.modifyReservationName(reservation, newField);
            case 2: // Contact
                return reservationManager.modifyReservationContact(reservation, newField);
            default:
                return false;
        }
    }

    public boolean updateReservation(Reservation reservation, int choice, int newField) {
        switch (choice) {
            case 1: // Pax
                return reservationManager.modifyReservationPax(reservation, newField);
            case 2: // TableNo
                return reservationManager.modifyReservationTableNo(reservation, newField);
            default:
                return false;
        }
    }

    public boolean updateReservation(Reservation reservation, GregorianCalendar newReservationPeriod) {
        return reservationManager.modifyReservationPeriod(reservation, newReservationPeriod);
    }
}
