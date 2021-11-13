package com.CZ2002.project_interfaces;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Scanner;

public interface IDateFormattable {
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
