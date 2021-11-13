package com.CZ2002.consoles;
import com.CZ2002.boundaries.RestaurantManager;
import com.CZ2002.boundaries.SalesRevenueManager;
import com.CZ2002.commands.revenue.PrintRevenueByDay;
import com.CZ2002.commands.revenue.PrintRevenueByMonth;
import com.CZ2002.entities.Order;
import com.CZ2002.entities.PackageItem;
import com.CZ2002.entities.SalesRevenue;
import com.CZ2002.enums.MenuView;
import com.CZ2002.exceptions.InvalidSalesRevenueQueryException;
import com.CZ2002.interfaces.ICommand;
import com.CZ2002.interfaces.IDateFormatter;
import com.CZ2002.utils.MenuBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
                ICommand<SalesRevenue, InvalidSalesRevenueQueryException> printRevenueByDayCommand = new PrintRevenueByDay(mainManager.getSubManager("salesRevenueManager", SalesRevenueManager.class), queryD);
                try {
                    SalesRevenue revenue = printRevenueByDayCommand.execute();
                    displayDailySalesRevenue(revenue, queryD);
                } catch(InvalidSalesRevenueQueryException e){
                    System.out.println(e.getMessage());
                }
                break;
            case 2:
                // Print by Month
                sc.nextLine();
                Date startDate = format(sc, "Start Day");
                Date endDate = format(sc, "End Day");
                ICommand<ArrayList<SalesRevenue>, InvalidSalesRevenueQueryException> printRevenueByMonthCommand = new PrintRevenueByMonth(mainManager.getSubManager("salesRevenueManager", SalesRevenueManager.class), startDate, endDate);
                try {
                    ArrayList<SalesRevenue> monthlySalesRevenue = printRevenueByMonthCommand.execute();
                    displayMonthlySalesRevenue(monthlySalesRevenue, startDate, endDate);
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
                "Back"
        };
        String title = "Restaurant Reservation & Point of Sale System";

        System.out.println(MenuBuilder.buildMenu(title, options));

        return options.length;
    }

    private void displayDailySalesRevenue(SalesRevenue revenue, Date queryDate) {
        // Tabulate by Day
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yy");
        String dateAsString = fmt.format(queryDate);
        int count = 1;
        ArrayList<Order> tempOrderList = revenue.getOrderList();

        String title = "Daily Revenue Report";
        ArrayList<String> options = new ArrayList<String>();
        ArrayList<String> optionHeaders = new ArrayList<String>();
        String[] optionsArr;
        String[] optionHeadersArr;
        int LONGEST_WIDTH = 40;

        System.out.print(MenuBuilder.buildMenu(title, LONGEST_WIDTH));
        options.add(dateAsString);
        optionHeaders.add("Date");
        optionsArr = new String[options.size()];
        optionHeadersArr = new String[optionHeaders.size()];
        System.out.println(MenuBuilder.buildMenu(LONGEST_WIDTH, options.toArray(optionsArr), optionHeaders.toArray(optionHeadersArr), "-"));

        for (Order order : tempOrderList) {
            options.clear();
            optionHeaders.clear();
            Order tempOrder = order;
            optionHeaders.add("ORDER");
            options.add(String.format("#%d", count++));
            optionHeaders.add(""); // For Formatting
            options.add(""); // For Formatting
            for (int j = 0; j < tempOrder.getOrdered().size(); j++) {
                if (tempOrder.getOrdered().get(j) instanceof PackageItem) {
                    options.add("Package");
                } else {
                    options.add("Ala Carte");
                }
                optionHeaders.add(tempOrder.getOrdered().get(j).getName());
            }
            optionsArr = new String[options.size()];
            optionHeadersArr = new String[optionHeaders.size()];
            System.out.println(MenuBuilder.buildMenu(LONGEST_WIDTH, options.toArray(optionsArr), optionHeaders.toArray(optionHeadersArr), "-"));
        }

            double bill;
            bill = revenue.getRevenue();
            String[] totalRevenue = {String.format("%.2f", bill)};
            String[] totalRevenueHeader = {"Daily Total Revenue"};
            System.out.println(MenuBuilder.buildMenu(LONGEST_WIDTH, totalRevenue, totalRevenueHeader, "="));
    }

    private void displayMonthlySalesRevenue(ArrayList<SalesRevenue> monthlySalesRevenue, Date startDate, Date endDate) {
        // Tabulate by Month
        int LONGEST_WIDTH = 40;

        System.out.println("Period: " + startDate + " - " + endDate);
        Calendar cal = Calendar.getInstance();

        cal.setTime(endDate);
        int endDayOfTheYear = cal.get(Calendar.DAY_OF_YEAR);

        cal.setTime(startDate);
        int startDayOfTheYear = cal.get(Calendar.DAY_OF_YEAR) - 1;

        String title = "Monthly Revenue Report";
        System.out.print(MenuBuilder.buildMenu(title, LONGEST_WIDTH));
        
        String[] spacerOption = {""};
        String[] spacerOptionHeader = {""};

        double tabulatedBill = 0;
        for(int i=startDayOfTheYear; i<endDayOfTheYear; i++){
            tabulatedBill += monthlySalesRevenue.get(i - startDayOfTheYear).getRevenue();
            System.out.print(MenuBuilder.buildMenu(LONGEST_WIDTH, spacerOption, spacerOptionHeader, ""));
            displayDailySalesRevenue(monthlySalesRevenue.get(i - startDayOfTheYear), cal.getTime());
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
        
        System.out.print(MenuBuilder.buildMenu(LONGEST_WIDTH, spacerOption, spacerOptionHeader, ""));
        System.out.println("=".repeat(LONGEST_WIDTH + 5));
        String[] revenue = {String.format("%.2f", tabulatedBill)};
        String[] revenueHeader = {"Total Revenue"};
        System.out.println(MenuBuilder.buildMenu(LONGEST_WIDTH, revenue, revenueHeader, "="));
    }
}