package Commands;

import java.util.GregorianCalendar;
import java.util.Scanner;

import Exceptions.InvalidReservationException;
import Exceptions.ReservationsFullException;
import ManagerClasses.ReservationManager;
import ManagerClasses.TableManager;
import RestaurantClasses.Reservation;
import Utils.MenuView;

public class UpdateReservationCommand implements iCommand<MenuView>, iGregorianCalendarFormatter {
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
    public MenuView execute() {
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

                int[] unavailableTableNos = reservationManager.getUnavailableTables(reservation.getReservationPeriod());
                
                try {
                    if (unavailableTableNos.length >= tableManager.getMaxTables()) {
                        throw new ReservationsFullException("No reservations available for this number of guests at this time.");
                    }

                    int bookedTableNo = tableManager.getAvailableTable(unavailableTableNos, newPax);

                    reservationManager.modifyReservationTableNo(reservation, bookedTableNo);
                    reservationManager.modifyReservationPax(reservation, newPax);
                } catch (ReservationsFullException e) {
                    System.out.println(e.getMessage());
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                }

                break;
            case 4:
                System.out.printf("Table No: ");
                int newTableNo = sc.nextInt();
                try {
                    tableManager.checkTableSuitability(newTableNo, reservation.getPax());
                    reservationManager.modifyReservationTableNo(reservation, newTableNo);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                
                break;
            case 5:
                sc.nextLine(); // Throw away \n in buffer 
                GregorianCalendar newReservationPeriod = format(sc, "Reservation Period");
                try {
                    reservationManager.modifyReservationPeriod(reservation, newReservationPeriod);
                } catch (InvalidReservationException e) {
                    System.out.println(e.getMessage());
                }
                
                break;
            case 6:
                return MenuView.PREVIOUS_MENU;
        }
        return MenuView.CURRENT_MENU;
    }
}
