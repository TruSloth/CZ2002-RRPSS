package com.CZ2002.interfaces;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * {@link IGregorianCalendarFormattable} provides functionality to parse
 * the given {@code prompt} into the {@link GregorianCalendar} instance
 * that presents it.
 * 
 */
public interface IGregorianCalendarFormattable {
    
    /**
     * This method formats the given {@code prompt}.
     * 
     * The given {@code prompt} must follow the 
     * pattern {@code dd/MM/yy HH:mm}.
     * <p>
     * This method additionally formats {@code prompt}
     * using the {@link java.time.ZoneId} {@code "Asia/Singapore"}.
     * 
     * @param sc  the {@link Scanner} instance used in the application
     * @param prompt  the String to formatted into a {@code GregorianCalendar} instance
     * @return  the {@code GregorianCalendar} instance representing the given {@code prompt}
     */
    public default GregorianCalendar format(Scanner sc, String prompt){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
        boolean done; // Exit loop only if no exception is caught
        LocalDateTime localDateTime = LocalDateTime.now();
        do {
            System.out.printf("%s(DD/MM/YY HH:MM): ", prompt);
            String reservationPeriodString = sc.nextLine();
            try {
                localDateTime = LocalDateTime.parse(reservationPeriodString, formatter);
                localDateTime.isBefore(LocalDateTime.now());
                done = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date-time format!");
                done = false;
            }
        } while (!done);

        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Singapore"));
        return GregorianCalendar.from(zonedDateTime);
    }
}
