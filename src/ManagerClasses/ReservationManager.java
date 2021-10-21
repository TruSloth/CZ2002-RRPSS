package ManagerClasses;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import RestaurantClasses.Reservation;

public class ReservationManager {
    private ArrayList<Reservation> reservations;

    private ArrayList<ScheduledExecutorService> executors;

    final private long expiryTimeMS = 60000;

    public ReservationManager() {
        reservations = new ArrayList<Reservation>();
        executors = new ArrayList<ScheduledExecutorService>();
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

    private void setReservationExpiry(Reservation reservation) {
        // Use a ScheduledThreadPool to remove reservation at expiryTimeMS time after start of reservation (Reservation Expires)
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
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

    public void createNewReservation(GregorianCalendar reservationPeriod, int pax, String name, String contact, int tableNo) {
        final Reservation reservation = new Reservation(reservationPeriod, pax, name, contact, tableNo);

        reservations.add(reservation);

        setReservationExpiry(reservation);

        // ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        // executors.add(reservations.indexOf(reservation), executor);

        // long timeDifference = reservationPeriod.getTimeInMillis() - System.currentTimeMillis() + expiryTimeMS;

        // Callable<Void> c = () -> {
        //     System.out.println("Removing reservation at " + new Date());
        //     executors.remove(reservations.indexOf(reservation));
        //     reservations.remove(reservation);
        //     return null;
        // };

        // try {
        //     reservation.setExpiry(executor.schedule(c, timeDifference, TimeUnit.MILLISECONDS));
        // } catch (Exception e) {
        //     e.printStackTrace();
        // } 

        // executor.shutdown(); 
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

    public boolean modifyReservationPeriod(Reservation reservation, GregorianCalendar newReservationPeriod) {
        reservation.getExpiry().cancel(true);
        executors.remove(reservations.indexOf(reservation));
        reservation.setReservationPeriod(newReservationPeriod);

        setReservationExpiry(reservation);

        // ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        // executors.add(reservations.indexOf(reservation), executor);

        // long timeDifference = newReservationPeriod.getTimeInMillis() - System.currentTimeMillis() + expiryTimeMS;

        // Callable<Void> c = () -> {
        //     System.out.println("Removing reservation at " + new Date());
        //     executors.remove(reservations.indexOf(reservation));
        //     reservations.remove(reservation);
        //     return null;
        // };

        // try {
        //     reservation.setExpiry(executor.schedule(c, timeDifference, TimeUnit.MILLISECONDS));
        // } catch (Exception e) {
        //     e.printStackTrace();
        // } 

        // executor.shutdown();

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
