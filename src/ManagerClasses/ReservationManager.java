package ManagerClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import Exceptions.InvalidReservationException;
import RestaurantClasses.Reservation;

public class ReservationManager extends Manager<Reservation> {
    //private ArrayList<Reservation> reservations;

    private ArrayList<ScheduledExecutorService> executors;

    final private long EXPIRYTIME_MS = 900000; // Time after the start of a reservation upon which reservation will expire - 15 mins
    public ReservationManager() {
        //reservations = new ArrayList<Reservation>();
        entities = new ArrayList<Reservation>();
        executors = new ArrayList<ScheduledExecutorService>();
    }

    private void setReservationExpiry(Reservation reservation) {
        /* 
         * Used internally whenever a new reservation is made or changes to a reservation's reservationPeriod is made.
         * Schedules the Callable removeExpiredReservation, which removes a Reservation from the list of active reservations
         * as well as its corresponding ScheduledExecutorService, when the reservationExpires, as defined by EXPIRYTIME_MS.
         * The method also sets the ScheduledFuture as the expiry value of that Reservation, to hold a reference to the ScheduledFuture
         * to allow a way to cancel it when necessary. 
         */ 
        // Use a ScheduledThreadPool to remove reservation at expiryTimeMS time after start of reservation (Reservation Expires)
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.setRemoveOnCancelPolicy(true); // Set to true to allow tasks to be immediately removed from the work queue upon cancellation
        //executors.add(reservations.indexOf(reservation), executor);
        executors.add(entities.indexOf(reservation), executor);

        long timeDifference = reservation.getReservationPeriod().getTimeInMillis() - System.currentTimeMillis() + EXPIRYTIME_MS;

        Callable<Void> removeExpiredReservation = () -> {
            System.out.println("Removing reservation at " + new Date());
            executors.remove(entities.indexOf(reservation));
            entities.remove(reservation);
            return null;
        };

        try {
            reservation.setExpiry(executor.schedule(removeExpiredReservation, timeDifference, TimeUnit.MILLISECONDS));
        } catch (Exception e) {
            e.printStackTrace();
        } 

        executor.shutdown();
    }

    private void isAdvancedReservation(GregorianCalendar reservationPeriod) throws InvalidReservationException {
        /*
         * Checks to see if reservationPeriod is before the current time + some buffer time.
         * If reservationPeriod is within current time + the buffer time, then an exception is thrown
         */ 

        Calendar advancedReservationPeriod = Calendar.getInstance();
        advancedReservationPeriod.add(Calendar.HOUR, 24); // Must make reservation 24 hours in advance

        if (reservationPeriod.before(advancedReservationPeriod)) {
            throw new InvalidReservationException("A reservation can only be made at least 24 hours in advance.");
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

        return entities.stream()
                    .filter(reservation -> (new ReservationPeriod().clash(reservation.getReservationPeriod(), reservationPeriod)) == true)
                    .mapToInt(reservation -> reservation.getTableNo())
                    .toArray();
    }

    public void createNewReservation(GregorianCalendar reservationPeriod, int pax, String name, String contact, int tableNo) throws InvalidReservationException {
        isAdvancedReservation(reservationPeriod);
        
        final Reservation reservation = new Reservation(reservationPeriod, pax, name, contact, tableNo);

        entities.add(reservation);

        setReservationExpiry(reservation);
    }

    public Reservation findReservation(String name, String contact, GregorianCalendar reservationPeriod) throws NoSuchElementException {
        try {
            return entities
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
        executors.remove(entities.indexOf(reservation));  
        entities.remove(reservation);
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
        executors.remove(entities.indexOf(reservation));
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

    public void cancelAllReservationFutures() {
        // Called on shutting down to kill all live threads, otherwise JVM will not shutdown.
        for (Reservation reservation : entities) {
            reservation.getExpiry().cancel(true);
            reservation.setExpiry(null);
            executors.remove(entities.indexOf(reservation));
        }
    }
}
