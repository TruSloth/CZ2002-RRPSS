package ManagerClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import Exceptions.InvalidReservationException;
import RestaurantClasses.Reservation;

public class ReservationManager {
    private ArrayList<Reservation> reservations;

    private ArrayList<ScheduledExecutorService> executors;

    final private long expiryTimeMS = 60000; // Time after the start of a reservation upon which reservation will expire

    public ReservationManager() {
        reservations = new ArrayList<Reservation>();
        executors = new ArrayList<ScheduledExecutorService>();
    }

    private void setReservationExpiry(Reservation reservation) {
        // Use a ScheduledThreadPool to remove reservation at expiryTimeMS time after start of reservation (Reservation Expires)
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.setRemoveOnCancelPolicy(true); // Set to true to allow tasks to be immediately removed from the work queue upon cancellation
        executors.add(reservations.indexOf(reservation), executor);

        long timeDifference = reservation.getReservationPeriod().getTimeInMillis() - System.currentTimeMillis() + expiryTimeMS;

        Callable<Void> c = () -> {
            System.out.println("Removing reservation at " + new Date());
            executors.remove(reservations.indexOf(reservation));
            reservations.remove(reservation);
            return null;
        };

        try {
            reservation.setExpiry(executor.schedule(c, timeDifference, TimeUnit.MILLISECONDS));
        } catch (Exception e) {
            e.printStackTrace();
        } 

        executor.shutdown();
    }

    private void isAdvancedReservation(GregorianCalendar reservationPeriod) throws InvalidReservationException {
        Calendar advancedReservationPeriod = Calendar.getInstance();
        advancedReservationPeriod.add(Calendar.MINUTE, 1); // Must make reservation 1 hour in advance

        if (reservationPeriod.before(advancedReservationPeriod)) {
            throw new InvalidReservationException("A reservation can only be made at least 1 minute in advance.");
        }
    }

    public int[] getUnavailableTables(GregorianCalendar reservationPeriod) {
        // Assume that a reservation will take 2 hours. This means that a table reserved for 8PM will be unavailable for reservation from 6PM to 10PM.
        class ReservationPeriod {
            GregorianCalendar blockedReservationPeriod(GregorianCalendar reservationPeriod, int addHour) {
                GregorianCalendar reservationPeriodClone = new GregorianCalendar(reservationPeriod.getTimeZone());
                reservationPeriodClone.add(Calendar.HOUR, addHour);
                return reservationPeriodClone;
            }

            boolean clash(GregorianCalendar existingReservationPeriod, GregorianCalendar newReservationPeriod) {
                return (newReservationPeriod.after(blockedReservationPeriod(existingReservationPeriod, -2)) 
                        && newReservationPeriod.before(blockedReservationPeriod(existingReservationPeriod, 2)));
            }
        }

        return reservations.stream()
                    .filter(reservation -> (new ReservationPeriod().clash(reservation.getReservationPeriod(), reservationPeriod)) == true)
                    .mapToInt(reservation -> reservation.getTableNo())
                    .toArray();
    }

    public void createNewReservation(GregorianCalendar reservationPeriod, int pax, String name, String contact, int tableNo) throws InvalidReservationException {
        isAdvancedReservation(reservationPeriod);
        
        final Reservation reservation = new Reservation(reservationPeriod, pax, name, contact, tableNo);

        reservations.add(reservation);

        setReservationExpiry(reservation);
    }

    public Reservation findReservation(String name, String contact, GregorianCalendar reservationPeriod) throws NoSuchElementException {
        try {
            return reservations
            .stream()
            .filter(reservation -> reservation.getName().equals(name) && reservation.getContactNumber().equals(contact) && reservation.getReservationPeriod().equals(reservationPeriod))
            .findFirst()
            .get();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("The requested reservation does not exist!");
        }
    }

    public void deleteReservation(Reservation reservation) {
        reservation.getExpiry().cancel(true);
        reservation.setExpiry(null);
        executors.remove(reservations.indexOf(reservation));  
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

    public boolean modifyReservationPeriod(Reservation reservation, GregorianCalendar newReservationPeriod) throws InvalidReservationException {
        isAdvancedReservation(newReservationPeriod);
        
        reservation.getExpiry().cancel(true);
        executors.remove(reservations.indexOf(reservation));
        reservation.setReservationPeriod(newReservationPeriod);

        setReservationExpiry(reservation);

        return true;
    }

    public boolean modifyReservationTableNo(Reservation reservation, int tableNo) throws IllegalArgumentException {
        // TODO: Handle unbooking of old table no. and booking of new one
        // The method should throw an exception if the new tableNo refers to a table that cannot satisfy the reservation requirements
        if (Arrays.stream(getUnavailableTables(reservation.getReservationPeriod()))
            .anyMatch(unavailableTableNo -> unavailableTableNo == tableNo)) {
                throw new IllegalArgumentException("This table is already reserved for this time.");
        }
        reservation.setTableNo(tableNo);
        return true;
    }

    public boolean modifyReservationPax(Reservation reservation, int pax) {
        // TODO: Handle automatic shifting of table such that the new table is the smallest one that can fit pax
        // The method should throw an exception if there are no tables that can accomodate pax and should inform the user if the tableNo was changed
        reservation.setPax(pax);
        return true;
    }
}
