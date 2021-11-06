package Commands;

import java.util.GregorianCalendar;
import Exceptions.InvalidReservationException;
import ManagerClasses.ReservationManager;
import ManagerClasses.TableManager;

public class AddReservationCommand implements iCommand<Void, InvalidReservationException>, iGregorianCalendarFormatter {

    private ReservationManager reservationManager;
    private TableManager tableManager;

    private GregorianCalendar reservationPeriod;
    private int pax;
    private String name;
    private String contact;

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

    @Override
    public Void execute() throws InvalidReservationException {
        int[] unavailableTableNos = reservationManager.getUnavailableTables(reservationPeriod);
        try {
            if (unavailableTableNos.length >= tableManager.getMaxTables()) {
                throw new InvalidReservationException("Reservations are full at this time.");
            }

            int bookedTableNo = tableManager.getAvailableTable(unavailableTableNos, pax);
            reservationManager.createNewReservation(reservationPeriod, pax, name, contact, bookedTableNo);
        } catch (NullPointerException e) {
            throw new InvalidReservationException("There are no suitable tables available at this time");
        }

        return null;
    }
}