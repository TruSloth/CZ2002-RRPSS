package Commands;

import java.util.GregorianCalendar;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Exceptions.InvalidReservationException;
import ManagerClasses.ReservationManager;
import RestaurantClasses.Reservation;

public class RemoveReservationCommand implements iCommand<Boolean, InvalidReservationException>, iGregorianCalendarFormatter {
    private ReservationManager reservationManager;

    private Scanner sc;

    private GregorianCalendar reservationPeriod;
    private String name;
    private String contact;

    public RemoveReservationCommand(
        ReservationManager reservationManager,
        String name, String contact, GregorianCalendar reservationPeriod,
        Scanner sc) {

        this.reservationManager = reservationManager;

        this.name = name;
        this.contact = contact;
        this.reservationPeriod = reservationPeriod;

        this.sc = sc;
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
    

    // private void getInput() {
    //     System.out.println("Which Reservation would you like to remove?");
    //     sc.nextLine(); // Throw away \n in buffer
    //     System.out.printf("Name: ");
    //     name = sc.nextLine();
    //     System.out.printf("Contact: ");
    //     contact = sc.nextLine();
        
    //     reservationPeriod = format(sc, "Reservation Period");
    // }
}
