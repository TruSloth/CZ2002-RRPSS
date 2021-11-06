package Commands;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.GregorianCalendar;
import java.util.Scanner;

public interface iGregorianCalendarFormatter {
    default GregorianCalendar format(Scanner sc, String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
        boolean done = false; // Exit loop only if no exception is caught
        LocalDateTime localDateTime = LocalDateTime.now();
        do {
            System.out.printf("%s(DD/MM/YY HH:MM): ", prompt);
            String reservationPeriodString = sc.nextLine();
            try {
                localDateTime = LocalDateTime.parse(reservationPeriodString, formatter);
                if (localDateTime.isBefore(LocalDateTime.now())) {

                }
                done = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date-time format!");
                done = false;
            }
        } while (!done);
        
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Singapore"));
        GregorianCalendar reservationPeriod = GregorianCalendar.from(zonedDateTime);
        return reservationPeriod;
    }
}