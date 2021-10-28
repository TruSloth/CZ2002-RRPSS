package Commands;

import java.util.GregorianCalendar;
import java.util.Scanner;

import Exceptions.ReservationsFullException;
import ManagerClasses.ReservationManager;
import ManagerClasses.TableManager;

public class AddReservationCommand implements iCommand<Void>, iGregorianCalendarFormatter {

    private ReservationManager reservationManager;
    private TableManager tableManager;

    private Scanner sc;

    private GregorianCalendar reservationPeriod;
    private int pax;
    private String name;
    private String contact;

    public AddReservationCommand(ReservationManager reservationManager, TableManager tableManager, Scanner sc) {
        this.reservationManager = reservationManager;
        this.tableManager = tableManager;
        this.sc = sc;
    }

    @Override
    public Void execute() {
        getInput();
        
        int[] unavailableTableNos = reservationManager.getUnavailableTables(reservationPeriod);
        try {
            if (unavailableTableNos.length >= tableManager.getMaxTables()) {
                throw new ReservationsFullException();
            }

            int bookedTableNo = tableManager.getAvailableTable(unavailableTableNos, pax);
            reservationManager.createNewReservation(reservationPeriod, pax, name, contact, bookedTableNo);
        } catch (ReservationsFullException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            throw new NullPointerException("There are no suitable tables available at this time");
        }

        return null;
    }

    private void getInput() {
        System.out.println("Creating a new Reservation");
        sc.nextLine(); // Throw away \n in buffer
        System.out.printf("Name: ");
        name = sc.nextLine();
        System.out.printf("Contact: ");
        contact = sc.nextLine();
        System.out.printf("Pax: ");
        pax = sc.nextInt();
        sc.nextLine(); // Throw away \n in buffer
        
        reservationPeriod = format(sc, "Reservation Period");
    }
}
