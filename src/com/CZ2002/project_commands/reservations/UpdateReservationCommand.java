package com.CZ2002.project_commands.reservations;

import java.util.GregorianCalendar;
import java.util.Scanner;

import com.CZ2002.project_boundaries.ReservationManager;
import com.CZ2002.project_boundaries.TableManager;
import com.CZ2002.project_entities.Reservation;
import com.CZ2002.project_enums.MenuView;
import com.CZ2002.project_exceptions.InvalidReservationException;
import com.CZ2002.project_interfaces.ICommand;
import com.CZ2002.project_interfaces.IGregorianCalendarFormatter;

/**
 * This class implements {@link ICommand} to complete the 'update reservation' action.
 * <p>
 * There are several ways to update a {@link Reservation} instance:
 * <ul>
 * <li>Name
 * <li>Contact
 * <li>Pax
 * <li>Table Number
 * <li>Reservation Period
 * </ul>
 * This class handles each one seperately and so must accept input to decide which field to update.
 */
public class UpdateReservationCommand implements ICommand<MenuView, InvalidReservationException>, IGregorianCalendarFormatter {
    private ReservationManager reservationManager;
    private TableManager tableManager;
    private Reservation reservation;
    private Scanner sc;

    /**
     * Constructor that accepts the necessary parameters for {@code execute} to successfully complete.
     *
     * @param reservationManager  the reference to the Restaurant's {@link ReservationManager}
     * @param tableManager  the reference to the Restaurant's {@link TableManager}
     * @param reservation  the reference to the specific {@link Reservation} instance to be updated
     * @param sc  the {@link Scanner} used by the boundary layer
     */
    public UpdateReservationCommand(ReservationManager reservationManager, TableManager tableManager, Reservation reservation, Scanner sc) {
        this.reservationManager = reservationManager;
        this.tableManager = tableManager;
        this.reservation = reservation;
        this.sc = sc;
    }

    /**
     * Completes the 'update reservation' action.
     * <p>
     * This method requests for additional input to decide which field to update.
     * <p>
     * The method also performs the necessary checks to ensure that the new value with
     * with which the field is to be updated is valid.
     *
     * @return  {@code MenuView#PREVIOUS_MENU} if the option to return is chosen, {@code MenuView#CURRENT_MENU} otherwise
     * @throws  InvalidReservationException  if the desired field of the {@link Reservation} instance cannot be updated with the new value
     */
    @Override
    public MenuView execute() throws InvalidReservationException {
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                System.out.print("Name: ");
                sc.nextLine(); // Throw away \n in buffer
                String newName = sc.nextLine();
                reservationManager.modifyReservationName(reservation, newName);
                break;
            case 2:
                System.out.print("Contact: ");
                sc.nextLine(); // Throw away \n in buffer
                String newContact = sc.nextLine();
                reservationManager.modifyReservationContact(reservation, newContact);
                break;
            case 3:
                System.out.print("Pax: ");
                int newPax = sc.nextInt();

                int[] unsuitableTableNos = reservationManager.getUnavailableTables(reservation.getReservationPeriod());

                try {
                    if (unsuitableTableNos.length >= tableManager.getMaxTables()) {
                        throw new InvalidReservationException("No Reservations Available for This Number of Guests at This Time.");
                    }

                    int bookedTableNo = tableManager.getAvailableTable(unsuitableTableNos, newPax);

                    reservationManager.modifyReservationTableNo(reservation, bookedTableNo);
                    reservationManager.modifyReservationPax(reservation, newPax);
                } catch (IllegalArgumentException e) {
                    throw new InvalidReservationException(String.format("Unable to Change to %d Pax.", newPax));
                } catch (NullPointerException e) {
                    throw new InvalidReservationException("We Could Not Find a Suitable Table at This Time.");
                }

                break;
            case 4:
                System.out.print("Table No: ");
                int newTableNo = sc.nextInt();
                try {
                    tableManager.checkTableSuitability(newTableNo, reservation.getPax());
                    reservationManager.modifyReservationTableNo(reservation, newTableNo);
                } catch (IllegalArgumentException e) {
                    throw new InvalidReservationException("Unable to Change to This Table No.");
                }

                break;
            case 5:
                sc.nextLine(); // Throw away \n in buffer
                GregorianCalendar newReservationPeriod = format(sc, "Reservation Period");

                int[] unavailableTableNos = reservationManager.getUnavailableTables(newReservationPeriod);

                try {
                    if (unavailableTableNos.length >= tableManager.getMaxTables()) {
                        throw new InvalidReservationException("No Reservations Available for This Number of Guests at This Time.");
                    }

                    int bookedTableNo = tableManager.getAvailableTable(unavailableTableNos, reservation.getPax());

                    if (bookedTableNo != reservation.getTableNo()) {
                        reservationManager.modifyReservationTableNo(reservation, bookedTableNo);
                    }

                    reservationManager.modifyReservationPeriod(reservation, newReservationPeriod);
                } catch (IllegalArgumentException | NullPointerException e) {
                    throw new InvalidReservationException("We Could Not Find a Suitable Table at This Time.");
                }

                break;
            case 6:
                return MenuView.PREVIOUS_MENU;
        }
        return MenuView.CURRENT_MENU;
    }
}