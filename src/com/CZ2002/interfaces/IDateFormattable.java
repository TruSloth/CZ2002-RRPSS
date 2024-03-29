package com.CZ2002.interfaces;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Scanner;

/**
 * {@link IDateFormattable} provides functionality to parse
 * the given {@code prompt} into the {@link Date} instance
 * that presents it.
 * 
 */
public interface IDateFormattable {

    /**
     * This method formats the given {@code prompt}.
     * 
     * The given {@code prompt} must follow the 
     * pattern {@code dd/MM/yy}.
     * 
     * @param sc  the {@link Scanner} instance used in the application
     * @param prompt  the String to formatted into a {@code Date} instance
     * @return  the {@code Date} instance representing the given {@code prompt}
     */
    public default Date format(Scanner sc, String prompt){
        Date formatDate = null;
        boolean done; // Exit loop only if no exception is caught
        do {
            System.out.printf("%s(DD/MM/YY): ", prompt);
            String queryDate = sc.nextLine();
            try {
                formatDate = new SimpleDateFormat("dd/MM/yy").parse(queryDate);
                done = true;
            } catch (DateTimeParseException | ParseException e) {
                System.out.println("Invalid Date Format! (DD/MM/YY)");
                done = false;
            }
        } while (!done);

        return formatDate;
    }
}
