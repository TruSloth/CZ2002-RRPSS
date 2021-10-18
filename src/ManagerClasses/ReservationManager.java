package ManagerClasses;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;

import RestaurantClasses.Reservation;

public class ReservationManager {
    private ArrayList<Reservation> reservations;

    public ReservationManager() {
        reservations = new ArrayList<Reservation>();
    }

    public int[] getUnavailableTables(GregorianCalendar reservationPeriod) {
        // Assume that a reservation will take 2 hours. This means that a table reserved for 8PM will be unavailable for reservation from 6PM to 10PM.
        class ReservationPeriod {
            GregorianCalendar blockedReservationPeriod(GregorianCalendar reservationPeriod, int addHour) {
                reservationPeriod.roll(Calendar.HOUR, addHour);
                return reservationPeriod;
            }

            boolean clash(GregorianCalendar existingReservationPeriod, GregorianCalendar newReservationPeriod) {
                return (newReservationPeriod.after(blockedReservationPeriod(existingReservationPeriod, -2)) 
                        && newReservationPeriod.before(blockedReservationPeriod(existingReservationPeriod, 2)));
            }
        }

        return reservations.stream()
                    .filter(reservation -> (new ReservationPeriod().clash(reservation.getReservationPeriod(), reservationPeriod)) == false)
                    .mapToInt(reservation -> reservation.getTableNo())
                    .toArray();
    }

    public void createNewReservation(GregorianCalendar reservationPeriod, int pax, String name, String contact, int tableNo) {
        reservations.add(new Reservation(reservationPeriod, pax, name, contact, tableNo));
    }

    public Reservation findReservation(String name, String contact, GregorianCalendar reservationPeriod) {
        try {
            return reservations
            .stream()
            .filter(reservation -> reservation.getName().equals(name) && reservation.getContactNumber().equals(contact) && reservation.getReservationPeriod().equals(reservationPeriod))
            .findFirst()
            .get();
        } catch (NoSuchElementException e) {
            //TODO: handle exception
            return null;
        }
        
    }

    public void deleteReservation(Reservation reservation) {
        reservations.remove(reservation);
    }

    public boolean modifyReservationName(Reservation reservation, String newName) {
        reservation.setName(newName);
        return true;
    }

    public boolean modifyReservationContact(Reservation reservation, String newContact) {
        reservation.setContactNumber(newContact);
        return true;
    }

    public boolean modifyReservationPeriod(Reservation reservation, GregorianCalendar newReservationPeriod) {
        reservation.setReservationPeriod(newReservationPeriod);
        return true;
    }

    public boolean modifyReservationTableNo(Reservation reservation, int tableNo) {
        // TODO: Handle unbooking of old table no. and booking of new one
        // The method should throw an exception if the new tableNo refers to a table that cannot satisfy the reservation requirements
        reservation.setTableNo(tableNo);
        return true;
    }

    public boolean modifyReservationPax(Reservation reservation, int pax) {
        reservation.setPax(pax);
        return true;
    }
}
