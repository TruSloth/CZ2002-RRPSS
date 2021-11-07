package com.CZ2002.project_displays;
import com.CZ2002.project_boundaries.SalesRevenueManager;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;


/**
 * A boundary class that takes in inputs from the user
 */
public class SalesRevenueConsole {

    private SalesRevenueManager s;
    /**
     * SalesRevenueConsole to print out the display console
     * Only takes in inputs and produces outputs
     * Does not process any logics
     * @throws ParseException if parsing fails
     */
    public void SalesRevenueConsole() throws ParseException{
        int choice;
        Scanner sc = new Scanner(System.in);

        Date d;
        Date startDate;
        Date endDate;
        do{
            System.out.println("Welcome to Sales Revenue Manager!");
            System.out.println("Enter the number of your choice!");
            System.out.println("(1) Print Sales Revenue by Day");
            System.out.println("(2) Print Sales Revenue by Month");
            System.out.println("(3) Quit Manager");
            choice = sc.nextInt();

            switch(choice)
            {
                case 1:
                    System.out.println("Please enter a date");
                    String date = sc.next();
                    d = StringToDate(date);
                    s.printByDay(d);
                    break;
                case 2:
                    System.out.println("Please enter the start date");
                    String sDate = sc.next();
                    startDate = StringToDate(sDate);
                    System.out.println("Please enter the end date");
                    String eDate = sc.next();
                    endDate = StringToDate(eDate);
                    s.printByMonth(startDate, endDate);
                    break;
                case 3:
                    break;
            }
        }while(choice<3);
    }

    /**
     * To convert a string input into Date
     * @param date String input of the date
     * @return Date value of the input
     * @throws ParseException if parsing fails
     */
    public static Date StringToDate(String date) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yy");
        Date date1 = formatter.parse(date);
        return date1;
    }
}