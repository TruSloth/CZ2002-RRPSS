package com.CZ2002.project_interfaces;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * Provides {@code format(Scanner sc, String prompt)} to parse the given {@code prompt} into a {@link GregorianCalendar} instance.
 */
interface IGregorianCalendarFormatter {
    /**
     * Parses the given {@code prompt} into a {@link GregorianCalendar} instance.
     * <p>
     * The given {@code prompt} must be in the format dd/MM/yy HH:mm.
     * <p>
     * This method uses the value of "Asia/Singapore" for its {@link ZonedDateTime}.
     *
     * @param sc  the {@link Scanner} instance the boundary layer uses
     * @param prompt  the String that should be parsed
     * @return  the {@code GregorianCalendar} instance that represents the time given by {@code prompt}
     */
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
        return GregorianCalendar.from(zonedDateTime);
    }
}