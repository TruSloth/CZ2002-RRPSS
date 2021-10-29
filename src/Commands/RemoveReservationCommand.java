package Commands;

import java.util.GregorianCalendar;
import java.util.NoSuchElementException;
import Exceptions.InvalidReservationException;
import ManagerClasses.ReservationManager;
import RestaurantClasses.Reservation;

public class RemoveReservationCommand implements iCommand<Boolean, InvalidReservationException>, iGregorianCalendarFormatter {
    private ReservationManager reservationManager;

    private GregorianCalendar reservationPeriod;
    private String name;
    private String contact;

    public RemoveReservationCommand(
        ReservationManager reservationManager,
        String name, String contact, GregorianCalendar reservationPeriod) {

        this.reservationManager = reservationManager;

        this.name = name;
        this.contact = contact;
        this.reservationPeriod = reservationPeriod;
    }

    @Override
    public Boolean execute() throws InvalidReservationException {
        //getInput();

        try {
            Reservation reservation = reservationManager.findReservation(name, contact, reservationPeriod);
            reservationManager.deleteReservation(reservation);
        } catch (NoSuchElementException e) {
            throw new InvalidReservationException("The requested reservation does not exist.");
        }
        
        return true;
    }
}
