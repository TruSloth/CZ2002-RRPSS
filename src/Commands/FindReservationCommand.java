package Commands;

import java.util.GregorianCalendar;
import java.util.NoSuchElementException;
import Exceptions.InvalidReservationException;
import ManagerClasses.ReservationManager;
import RestaurantClasses.Reservation;


/**
 * This class implements {@link iCommand} to complete the 'find reservation' action.
 */
public class FindReservationCommand implements iCommand<Reservation, InvalidReservationException>, iGregorianCalendarFormatter {
    private ReservationManager reservationManager;

    private String name;
    private String contact;
    private GregorianCalendar reservationPeriod;

    /**
     * Constructor that accepts the necessary parameters for {@code execute} to successfully complete.
     * 
     * @param reservationManager  the reference to the Restaurant's {@link ReservationManager}
     * @param name  the String to address the contact person
     * @param contact  the String representing the phone number to contact the guest at
     * @param reservationPeriod  the {@link GregorianCalendar} start time of this reservation
     */
    public FindReservationCommand(
        ReservationManager reservationManager, 
        String name, String contact, GregorianCalendar reservationPeriod) {

        this.reservationManager = reservationManager;

        this.name = name;
        this.contact = contact;
        this.reservationPeriod = reservationPeriod;
    }

    /**
     * Completes the 'find reservation' action.
     * 
     * @return  the requested {@code Reservation} instance
     * @throws InvalidReservationException  if the requested {@code Reservation} could not be found
     */
    @Override
    public Reservation execute() throws InvalidReservationException {
        Reservation reservation;
        try {
            reservation = reservationManager.findReservation(name, contact, reservationPeriod);
        } catch (NoSuchElementException e) {
            throw new InvalidReservationException("The requested reservation does not exist.");
        }

        return reservation;
    }
}
