package com.CZ2002.project_commands.reservations;

import java.util.GregorianCalendar;

import com.CZ2002.project_exceptions.InvalidReservationException;
import com.CZ2002.project_interfaces.ICommand;
import com.CZ2002.project_interfaces.IGregorianCalendarFormattable;
import com.CZ2002.project_managers.ReservationManager;
import com.CZ2002.project_managers.TableManager;

/**
 * This class implements {@link ICommand} to complete the 'add reservation' action.
 */
public class AddReservationCommand implements ICommand<Void, InvalidReservationException>, IGregorianCalendarFormattable {
    private ReservationManager reservationManager;
    private TableManager tableManager;
    private GregorianCalendar reservationPeriod;
    private int pax;
    private String name;
    private String contact;

    /**
     * Constructor that accepts the necessary parameters for {@code execute} to successfully complete.
     *
     * @param reservationManager  the reference to the Restaurant's {@link ReservationManager}
     * @param tableManager  the reference to the Restaurant's {@link TableManager}
     * @param name  the String to address the contact person
     * @param contact  the String representing the phone number to contact the guest at
     * @param pax  the number of guests
     * @param reservationPeriod  the {@link GregorianCalendar} start time of this reservation
     */
    public AddReservationCommand(
            ReservationManager reservationManager, TableManager tableManager,
            String name, String contact, int pax, GregorianCalendar reservationPeriod) {
        this.reservationManager = reservationManager;
        this.tableManager = tableManager;
        this.name = name;
        this.contact = contact;
        this.pax = pax;
        this.reservationPeriod = reservationPeriod;
    }

    /**
     * Completes the 'add reservation' action.
     *
     * @return Void
     * @throws InvalidReservationException  if there are no tables available or suitable
     */
    @Override
    public Void execute() throws InvalidReservationException {
        int[] unavailableTableNos = reservationManager.getUnavailableTables(reservationPeriod);
        try {
            if (unavailableTableNos.length >= tableManager.getMaxTables()) {
                throw new InvalidReservationException("Reservations Are Full At This Time!");
            }
            int bookedTableNo = tableManager.getAvailableTable(unavailableTableNos, pax);
            reservationManager.createNewReservation(reservationPeriod, pax, name, contact, bookedTableNo); 
        } catch (IllegalArgumentException e) {
            throw new InvalidReservationException(e.getMessage());
        } catch (NullPointerException e) {
            throw new InvalidReservationException("There Are No Suitable Tables Available At This Time.");
        }

        return null;
    }
}