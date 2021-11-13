package com.CZ2002.project_commands.reservations;

import java.util.GregorianCalendar;
import java.util.NoSuchElementException;
import com.CZ2002.project_exceptions.InvalidReservationException;
import com.CZ2002.project_boundaries.ReservationManager;
import com.CZ2002.project_entities.Reservation;
import com.CZ2002.project_interfaces.ICommand;
import com.CZ2002.project_interfaces.IGregorianCalendarFormatter;

/**
 * This class implements {@link ICommand} to complete the 'remove reservation' action.
 */
public class RemoveReservationCommand implements ICommand<Boolean, InvalidReservationException>, IGregorianCalendarFormatter {
    private ReservationManager reservationManager;
    private GregorianCalendar reservationPeriod;
    private String name;
    private String contact;


    /**
     * Constructor that accepts the necessary parameters for {@code execute} to successfully complete.
     *
     * @param reservationManager  the reference to the Restaurant's {@link ReservationManager}
     * @param name  the String to address the contact person
     * @param contact  the String representing the phone number to contact the guest at
     * @param reservationPeriod  the {@link GregorianCalendar} start time of this reservation
     */
    public RemoveReservationCommand(
            ReservationManager reservationManager,
            String name, String contact, GregorianCalendar reservationPeriod) {

        this.reservationManager = reservationManager;

        this.name = name;
        this.contact = contact;
        this.reservationPeriod = reservationPeriod;
    }

    /**
     * Completes the 'remove reservation' action.
     *
     * @return  true if the requested {@code Reservation} instance was successfully removed
     * @throws  InvalidReservationException  if the requested {@code Reservation} could not be found
     */
    @Override
    public Boolean execute() throws InvalidReservationException {
        try {
            Reservation reservation = reservationManager.findReservation(name, contact, reservationPeriod);
            reservationManager.deleteReservation(reservation);
        } catch (NoSuchElementException e) {
            throw new InvalidReservationException("The requested reservation does not exist.");
        }

        return true;
    }
}