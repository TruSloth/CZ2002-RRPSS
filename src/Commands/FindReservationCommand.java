package Commands;

import java.util.GregorianCalendar;
import java.util.Scanner;

import ManagerClasses.ReservationManager;
import RestaurantClasses.Reservation;

public class FindReservationCommand implements iCommand<Reservation>, iGregorianCalendarFormatter {
    private ReservationManager reservationManager;

    private Scanner sc;

    private GregorianCalendar reservationPeriod;
    private String name;
    private String contact;

    public FindReservationCommand(ReservationManager reservationManager, Scanner sc) {
        this.reservationManager = reservationManager;
        this.sc = sc;
    }

    @Override
    public Reservation execute() {
        getInput();
        Reservation reservation;
        try {
            reservation = reservationManager.findReservation(name, contact, reservationPeriod);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

        return reservation;
    }

    private void getInput() {
        System.out.println("Which Reservation are you looking for?");
        sc.nextLine(); // Throw away \n in buffer
        System.out.printf("Name: ");
        name = sc.nextLine();
        System.out.printf("Contact: ");
        contact = sc.nextLine();
        
        reservationPeriod = format(sc, "Reservation Period");
    }
}
