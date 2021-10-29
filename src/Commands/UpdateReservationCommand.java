package Commands;

import java.util.GregorianCalendar;
import java.util.Scanner;

import Exceptions.InvalidReservationException;
import ManagerClasses.ReservationManager;
import ManagerClasses.TableManager;
import RestaurantClasses.Reservation;
import Utils.MenuView;

public class UpdateReservationCommand implements iCommand<MenuView, InvalidReservationException>, iGregorianCalendarFormatter {
    private ReservationManager reservationManager;
    private TableManager tableManager;

    private Reservation reservation;

    private Scanner sc;

    public UpdateReservationCommand(ReservationManager reservationManager, TableManager tableManager, Reservation reservation, Scanner sc) {
        this.reservationManager = reservationManager;
        this.tableManager = tableManager;
        this.reservation = reservation;
        this.sc = sc;
    }

    @Override
    public MenuView execute() throws InvalidReservationException {
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                System.out.printf("Name: ");
                sc.nextLine(); // Throw away \n in buffer
                String newName = sc.nextLine();
                reservationManager.modifyReservationName(reservation, newName);
                break;
            case 2: 
                System.out.printf("Contact: ");
                sc.nextLine(); // Throw away \n in buffer
                String newContact = sc.nextLine();
                reservationManager.modifyReservationContact(reservation, newContact);
                break;
            case 3:
                System.out.printf("Pax: ");
                int newPax = sc.nextInt();

                int[] unsuitableTableNos = reservationManager.getUnavailableTables(reservation.getReservationPeriod());
                
                try {
                    if (unsuitableTableNos.length >= tableManager.getMaxTables()) {
                        throw new InvalidReservationException("No reservations available for this number of guests at this time.");
                    }

                    int bookedTableNo = tableManager.getAvailableTable(unsuitableTableNos, newPax);

                    reservationManager.modifyReservationTableNo(reservation, bookedTableNo);
                    reservationManager.modifyReservationPax(reservation, newPax);
                } catch (IllegalArgumentException e) {
                    throw new InvalidReservationException(String.format("Unable to change to %d pax.", newPax));
                } catch (NullPointerException e) {
                    throw new InvalidReservationException("We could not find a suitable table at this time.");
                }

                break;
            case 4:
                System.out.printf("Table No: ");
                int newTableNo = sc.nextInt();
                try {
                    tableManager.checkTableSuitability(newTableNo, reservation.getPax());
                    reservationManager.modifyReservationTableNo(reservation, newTableNo);
                } catch (IllegalArgumentException e) {
                    throw new InvalidReservationException("Unable to change to this table no.");
                }
                
                break;
            case 5:
                sc.nextLine(); // Throw away \n in buffer 
                GregorianCalendar newReservationPeriod = format(sc, "Reservation Period");

                int[] unavailableTableNos = reservationManager.getUnavailableTables(newReservationPeriod);

                try {
                    if (unavailableTableNos.length >= tableManager.getMaxTables()) {
                        throw new InvalidReservationException("No reservations available for this number of guests at this time.");
                    }

                    int bookedTableNo = tableManager.getAvailableTable(unavailableTableNos, reservation.getPax());

                    if (bookedTableNo != reservation.getTableNo()) {
                        reservationManager.modifyReservationTableNo(reservation, bookedTableNo);
                    }
                    
                    reservationManager.modifyReservationPeriod(reservation, newReservationPeriod);
                } catch (IllegalArgumentException e) {
                    throw new InvalidReservationException("We could not find a suitable table at this time.");
                } catch (NullPointerException e) {
                    throw new InvalidReservationException("We could not find a suitable table at this time.");
                }

                break;
            case 6:
                return MenuView.PREVIOUS_MENU;
        }
        return MenuView.CURRENT_MENU;
    }
}
