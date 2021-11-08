package com.CZ2002.project_displays;
import com.CZ2002.project_boundaries.RestaurantManager;
import com.CZ2002.project_boundaries.SalesRevenueManager;
import com.CZ2002.project_commands.revenue.PrintRevenueByDay;
import com.CZ2002.project_commands.reservations.PrintRevenueByMonth;
import com.CZ2002.project_enums.MenuView;
import com.CZ2002.project_exceptions.InvalidSalesRevenueQueryException;
import com.CZ2002.project_interfaces.ICommand;
import com.CZ2002.project_interfaces.IDateFormatter;
import com.CZ2002.project_utils.MenuBuilder;
import java.util.Date;
import java.util.Scanner;
import java.text.ParseException;

/**
 * A boundary class that takes in inputs from the user
 */
public class SalesRevenueConsole extends ConsoleDisplay implements IDateFormatter {
    public SalesRevenueConsole(RestaurantManager restaurantManager, Scanner sc){
        super.mainManager = restaurantManager;
        super.sc = sc;
    }

    /**
     * SalesRevenueConsole to print out the display console
     * Only takes in inputs and produces outputs
     * Does not process any logics
     */

    @Override
    public MenuView handleConsoleOptions() throws ParseException {
        int choice = sc.nextInt();
        MenuView view = MenuView.SALES_REVENUE;

        switch(choice){
            case 1:
                // Print by Day
                sc.nextLine();
                Date queryD = format(sc, "Query Day");
                ICommand<Void, InvalidSalesRevenueQueryException> printRevenueByDayCommand = new PrintRevenueByDay(mainManager.getSubManager("salesRevenueManager", SalesRevenueManager.class), queryD);
                try {
                    printRevenueByDayCommand.execute();
                } catch(InvalidSalesRevenueQueryException e){
                    System.out.println(e.getMessage());
                }
                break;
            case 2:
                // Print by Month
                sc.nextLine();
                Date startDate = format(sc, "Start Day");
                Date endDate = format(sc, "End Day");
                ICommand<Void, InvalidSalesRevenueQueryException> printRevenueByMonthCommand = new PrintRevenueByMonth(mainManager.getSubManager("salesRevenueManager", SalesRevenueManager.class), startDate, endDate);
                try {
                    printRevenueByMonthCommand.execute();
                } catch(InvalidSalesRevenueQueryException e){
                    System.out.println(e.getMessage());
                }
                break;
            case 3:
                // Back
                view = MenuView.PREVIOUS_MENU;
                break;
        }
        return view;
    }

    @Override
    public int displayConsoleOptions() {
        String[] options = new String[] {
                "Print Sales Revenue by Day",
                "Print Sales Revenue by Month",
        };
        String title = "Restaurant Reservation & Point of Sale System";

        System.out.println(MenuBuilder.buildMenu(title, options));

        return options.length;
    }
}