package Unused;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import RestaurantClasses.Reservation;
import Utils.MenuBuilder;

public class ReservationDetailsDisplay extends ConsoleDisplay{

    final int LONGEST_WIDTH = 20;
    String[] optionHeaders = {"Name", "Contact", "Pax", "Table No.", "Reservation Period"};
    
    public ReservationDetailsDisplay(Reservation reservation) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
        LocalDateTime ldt = reservation.getReservationPeriod().toInstant().atZone(ZoneId.of("Asia/Singapore")).toLocalDateTime();
        super.options = new String[] {reservation.getName(), reservation.getContactNumber(), String.valueOf(reservation.getPax()), String.valueOf(reservation.getTableNo()), fmt.format(ldt)};
        super.title = "Reservation Details";
    }

    @Override
    public int displayMenu() {
        System.out.println(MenuBuilder.buildMenu(title, optionHeaders, options, LONGEST_WIDTH));

        return super.options.length;
    }
}
