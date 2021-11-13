package com.CZ2002.boundaries;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import com.CZ2002.entities.RestaurantEntity;
import com.CZ2002.exceptions.InvalidReservationException;
import com.CZ2002.entities.Reservation;

/**
 * The {@link Manager} class that maintains the state of and handles all
 * logic surrounding all {@link Reservation} instances in the Restaurant.
 * <p>
 * This class implements all necessary functionality surrounding {@code Reservation}
 * instances and provides the access point to all {@code Reservation} instances
 * in the Restaurant.
 * <p>
 * The {@code} ReservationManager is set up to cause each {@code Reservation} to expire after
 * {@code #EXPIRYTIME_MS} and ensure that each {@code Reservation} must be made
 * 
 * <p>
 * In most cases, each Restaurant should only have a single {@code ReservationManager}
 * instance, although this is not enforced. In this way, this {@code ReservationManager}
 * provides the only access point to all {@code Reservation} instances.
 */
public class ReservationManager extends Manager<Reservation> {
    private ArrayList<ScheduledExecutorService> executors;

    final private long EXPIRYTIME_MS = 900000; // Time after the start of a reservation upon which reservation will expire - 15 mins

    final private int ADVANCED_HRS = 24; // Number of hours in advance that a reservation must be made
    /**
     * Constructs a new {@code ReservationManager} to manage {@link Reservation} instances.
     * <p>
     * Initializes {@code entities} and {@code executors} to be an empty {@link ArrayList}.
     */
    public ReservationManager() {
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
        advancedReservationPeriod.add(Calendar.HOUR, ADVANCED_HRS); // Must make reservation 24 hours in advance

        if (reservationPeriod.before(advancedReservationPeriod)) {
            throw new InvalidReservationException(String.format("Reservation can only be made at least %d hours in advance.", ADVANCED_HRS));
        }
    }


    /**
     * Returns all table numbers that are unavailable for booking for the desired start time.
     * <p>
     * This method assumes that each reservation takes at most 2 hours for dining. As such, the method
     * marks table numbers that have reservations within the 2 hour window before and after {@code reservationPeriod}
     * as being unavailable.
     *
     * @param reservationPeriod  the {@link GregorianCalendar} start time of the {@code Reservation}
     * @return the array consisting of all table numbers that are not suitable to be booked for {@code reservationPeriod}
     */
    public int[] getUnavailableTables(GregorianCalendar reservationPeriod) {
        // Assume that a reservation will take 2 hours. This means that a table reserved for 8PM will be unavailable for reservation from 6PM to 10PM.
        class ReservationPeriod {
            GregorianCalendar blockedReservationPeriod(GregorianCalendar reservationPeriod, int addHour) {
                // Create a temporary GregorianCalendar set to reservationPeriod
                GregorianCalendar reservationPeriodClone = new GregorianCalendar(
                    reservationPeriod.get(Calendar.YEAR), reservationPeriod.get(Calendar.MONTH), reservationPeriod.get(Calendar.DAY_OF_MONTH),
                    reservationPeriod.get(Calendar.HOUR_OF_DAY), reservationPeriod.get(Calendar.MINUTE), reservationPeriod.get(Calendar.SECOND)
                    );
                reservationPeriodClone.add(Calendar.HOUR, addHour);
                return reservationPeriodClone;
            }

            boolean clash(GregorianCalendar existingReservationPeriod, GregorianCalendar newReservationPeriod) {
                return (newReservationPeriod.after(blockedReservationPeriod(existingReservationPeriod, -2))
                        && newReservationPeriod.before(blockedReservationPeriod(existingReservationPeriod, 2)));
            }
        }

        return entities.stream()
                .filter(reservation -> (new ReservationPeriod().clash(reservation.getReservationPeriod(), reservationPeriod)))
                .mapToInt(Reservation::getTableNo)
                .toArray();
    }


    /**
     * Creates a new {@link Reservation} and adds it to {@link RestaurantEntity}.
     * <p>
     * This method checks to see if the {@code Reservation} to be made is advanced (ahead of time).
     * If the {@code Reservation} is successfully created, also creates a {@link java.util.concurrent.ScheduledFuture}
     * that cancels the {@code Reservation} when it expires.
     *
     * @param reservationPeriod  the {@link GregorianCalendar} start time of the {@code Reservation}
     * @param pax  the number of guests
     * @param name  the String to address the contact person
     * @param contact  the String representing the phone number to contact the guest at
     * @param tableNo  the table number reserved
     * @throws InvalidReservationException  if the time of creating the {@code Reservation} is too close to {@code reservationPeriod}
     */
    public void createNewReservation(GregorianCalendar reservationPeriod, int pax, String name, String contact, int tableNo) throws InvalidReservationException {
        isAdvancedReservation(reservationPeriod);

        final Reservation reservation = new Reservation(reservationPeriod, pax, name, contact, tableNo);

        entities.add(reservation);

        setReservationExpiry(reservation);
    }


    /**
     * Retrieves a specific {@link Reservation} from {@code entities}.
     * <p>
     * This method searches for the first {@code Reservation} in {@code entities} whose {@code name}, {@code contactNumber}
     * and {@code reservationPeriod} matches the given parameters.
     * <p>
     * It is assumed that this combination of parameters will identify a
     * unique {@code Reservation} in {@code entities}.
     *
     * @param name  the String to address the contact person
     * @param contact  the String representing the phone number to contact the guest at
     * @param reservationPeriod  the {@link GregorianCalendar} start time of the {@code Reservation}
     * @return the requested {@code Reservation}
     * @throws NoSuchElementException if the requested {@code Reservation} does not exist
     */
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


    /**
     * Cancels a specific {@link Reservation} by removing it from {@code entities}.
     * <p>
     * This method also cancels the {@link java.util.concurrent.ScheduledFuture} that causes this {@code reservation}
     * to expire and the {@link ScheduledExecutorService} that handles it.
     *
     * @param reservation  the {@code reservation} to be removed
     */
    public void deleteReservation(Reservation reservation) {
        reservation.getExpiry().cancel(true);
        reservation.setExpiry(null);
        executors.remove(entities.indexOf(reservation));
        entities.remove(reservation);
    }


    /**
     * Updates the {@code name} field of a specified {@link Reservation}.
     *
     * @param reservation  the {@code reseservation} to be modified
     * @param newName  the new String to address the contact person
     * @return  true if the modification was successful
     */
    public boolean modifyReservationName(Reservation reservation, String newName) {
        reservation.setName(newName);
        return true;
    }


    /**
     * Updates the {@code contactNumber} field of a specified {@link Reservation}.
     *
     * @param reservation  the {@code reseservation} to be modified
     * @param newContact  the new String representing the phone number to contact the guest at
     * @return  true if the modification was successful
     */
    public boolean modifyReservationContact(Reservation reservation, String newContact) {
        reservation.setContactNumber(newContact);
        return true;
    }


    /**
     * Updates the {@code reservationPeriod} field of a specified {@link Reservation}.
     * <p>
     * This method checks to see if {@code newReservationPeriod} is advanced (ahead of time).
     * Additionally, the exisiting {@link ScheduledExecutorService} is cancelled and
     * a new one is created for {@code newReservationPeriod}.
     *
     * @param reservation  the {@code reseservation} to be modified
     * @param newReservationPeriod  the new {@link GregorianCalendar} start time of the {@code Reservation}
     * @return  true if the modification was successful
     * @throws InvalidReservationException  if the time of modifying the {@code Reservation} is too close to {@code newReservationPeriod}
     */
    public boolean modifyReservationPeriod(Reservation reservation, GregorianCalendar newReservationPeriod) throws InvalidReservationException {
        isAdvancedReservation(newReservationPeriod);

        reservation.getExpiry().cancel(true);
        executors.remove(entities.indexOf(reservation));
        reservation.setReservationPeriod(newReservationPeriod);

        setReservationExpiry(reservation);

        return true;
    }


    /**
     * Updates the {@code tableNo} field of a specified {@link Reservation}.
     * <p>
     * This method checks if the {@code tableNo} is already reserved for this {@code reservationPeriod}.
     *
     * @param reservation  the {@code reseservation} to be modified
     * @param tableNo  the new table number reserved
     * @return  true if the modification was successful
     * @throws IllegalArgumentException  if new {@code tableNo} is already reserved for this {@code reservationPeriod}
     */
    public boolean modifyReservationTableNo(Reservation reservation, int tableNo) throws IllegalArgumentException {
        // The method should throw an exception if the new tableNo refers to a table that cannot satisfy the reservation requirements
        if (Arrays.stream(getUnavailableTables(reservation.getReservationPeriod()))
                .anyMatch(unavailableTableNo -> unavailableTableNo == tableNo)) {
            throw new IllegalArgumentException("This table is already reserved for this time.");
        }
        reservation.setTableNo(tableNo);
        return true;
    }


    /**
     * Updates the {@code pax} field of a specified {@link Reservation}.
     *
     * @param reservation  the {@code reseservation} to be modified
     * @param pax  the new number of guests
     * @return  true if the modification was successful
     */
    public boolean modifyReservationPax(Reservation reservation, int pax) {
        reservation.setPax(pax);
        return true;
    }

    public void startAllReservationFutures() {
        // Called on start up when loading from data storage to restart all ReservationFutures.
        for (Reservation reservation : entities) {
            setReservationExpiry(reservation);
        }
    }

    /**
     * Cancels the existing {@link java.util.concurrent.ScheduledFuture} instances found in the {@code expiry}
     * field of all {@link Reservation} instances stored in {@code entities}.
     * <p>
     * This method must be called when attempting to exit the entire program.
     * <p>
     * Assumes that there are no {@code Reservation} instances held in {@code entities} with a null {@code expiry}
     * prior to calling this method. If this cannot be guaranteed, a {@link NullPointerException} may be
     * thrown and must be handled.
     */
    public void cancelAllReservationFutures() {
        // Called on shutting down to kill all live threads, otherwise JVM will not shutdown.
        for (Reservation reservation : entities) {
            reservation.getExpiry().cancel(true);
            reservation.setExpiry(null);
        }
        executors.clear();
    }
}