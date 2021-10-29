package Commands;

import java.util.GregorianCalendar;
import java.util.NoSuchElementException;
import Exceptions.InvalidReservationException;
import ManagerClasses.ReservationManager;
import RestaurantClasses.Reservation;

public class FindReservationCommand implements iCommand<Reservation, InvalidReservationException>, iGregorianCalendarFormatter {
    private ReservationManager reservationManager;

    private String name;
    private String contact;
    private GregorianCalendar reservationPeriod;

    public FindReservationCommand(
        ReservationManager reservationManager, 
        String name, String contact, GregorianCalendar reservationPeriod) {

        this.reservationManager = reservationManager;

        this.name = name;
        this.contact = contact;
        this.reservationPeriod = reservationPeriod;
    }

    @Override
    public Reservation execute() throws InvalidReservationException {
        //getInput();
        Reservation reservation;
        try {
            reservation = reservationManager.findReservation(name, contact, reservationPeriod);
        } catch (NoSuchElementException e) {
            throw new InvalidReservationException("The requested reservation does not exist.");
        }

        return reservation;
    }

    // private void getInput() {
    //     System.out.println("Which Reservation are you looking for?");
    //     sc.nextLine(); // Throw away \n in buffer
    //     System.out.printf("Name: ");
    //     name = sc.nextLine();
    //     System.out.printf("Contact: ");
    //     contact = sc.nextLine();
        
    //     reservationPeriod = format(sc, "Reservation Period");
    // }
}
