package ManagerClasses;

import java.util.GregorianCalendar;
import java.util.NoSuchElementException;

import Exceptions.InvalidReservationException;
import Exceptions.ReservationsFullException;
import RestaurantClasses.Reservation;
import RestaurantClasses.Restaurant;

public class RestaurantManager {
    private ReservationManager reservationManager;
    private TableManager tableManager;
    // TODO: Add other manager classesvoid

    private Restaurant restaurant;

    public RestaurantManager(Restaurant restaurant) {
        this.restaurant = restaurant;

        reservationManager = new ReservationManager();
        int numOfTables = restaurant.getNumOfTables();
        System.out.println(numOfTables);
        tableManager = new TableManager(numOfTables, numOfTables / 5, numOfTables / 5 * 2 , numOfTables / 5, numOfTables / 10, numOfTables / 10); // (2,4,6,8,10-seater proportions in 20%, 40%, 20%, 10%, 10%)
    }

    // Reservation Methods
    public void addReservation(GregorianCalendar reservationPeriod, int pax, String name, String contact) throws ReservationsFullException, IllegalArgumentException, NullPointerException {
        int[] unavailableTableNos = reservationManager.getUnavailableTables(reservationPeriod);

        if (unavailableTableNos.length >= restaurant.getNumOfTables()) {
            throw new ReservationsFullException();
        }

        try {
            int bookedTableNo = tableManager.getAvailableTable(unavailableTableNos, pax);
            reservationManager.createNewReservation(reservationPeriod, pax, name, contact, bookedTableNo);
        } catch (NullPointerException e) {
            throw new NullPointerException("There are no suitable tables available at this time");
        }
    }

    public Reservation getReservationDetails(String name, String contact, GregorianCalendar reservationPeriod) throws NoSuchElementException {
        return reservationManager.findReservation(name, contact, reservationPeriod);
    }

    public boolean removeReservation(String name, String contact, GregorianCalendar reservationPeriod) throws NoSuchElementException {
        Reservation reservation = reservationManager.findReservation(name, contact, reservationPeriod);
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

    public boolean updateReservation(Reservation reservation, int choice, int newField) throws IllegalArgumentException, ReservationsFullException, NullPointerException {
        switch (choice) {
            case 1: // Pax
                int[] unavailableTableNos = reservationManager.getUnavailableTables(reservation.getReservationPeriod());
                
                if (unavailableTableNos.length >= restaurant.getNumOfTables()) {
                    throw new ReservationsFullException("No reservations available for this number of guests at this time.");
                }
                
                int bookedTableNo = tableManager.getAvailableTable(unavailableTableNos, reservation.getPax());
                reservationManager.modifyReservationTableNo(reservation, bookedTableNo);
                return reservationManager.modifyReservationPax(reservation, newField);
            case 2: // TableNo
                tableManager.checkTableSuitability(newField, reservation.getPax());
                reservationManager.modifyReservationTableNo(reservation, newField);
                return true;
            default:
                return false;
        }
    }

    public boolean updateReservation(Reservation reservation, GregorianCalendar newReservationPeriod) throws InvalidReservationException {
        return reservationManager.modifyReservationPeriod(reservation, newReservationPeriod);
    }

    public void shutdown() {
        // Called when option to quit is selected.
        reservationManager.cancelAllReservationFutures();
    }
}
