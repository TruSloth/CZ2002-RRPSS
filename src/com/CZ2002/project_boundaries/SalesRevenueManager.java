package com.CZ2002.project_boundaries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.CZ2002.project_entities.Order;
import com.CZ2002.project_entities.PackageItem;
import com.CZ2002.project_entities.SalesRevenue;
import com.CZ2002.project_exceptions.InvalidSalesRevenueQueryException;
import com.CZ2002.project_utils.MenuBuilder;

/**
 * A Control class to execute the logics of SalesRevenue - Print and Calculate
 */
public class SalesRevenueManager extends Manager<SalesRevenue>{
    // Constructor
    public SalesRevenueManager(){
        int numDays = Year.of(2021).length();
        entities = new ArrayList<SalesRevenue>(numDays); //ArrayList revenueList

        // Prepopulate List of Revenue
        for (int i=0; i<numDays; i++){
            SalesRevenue temp = new SalesRevenue();
            entities.add(temp);
        }
    }

    /** To add the revenue of an order into the ArrayList of revenues
     * @param order Items ordered by a customer
     */
    public void addOrder(Order order){
        // Deconstruct DD-MM-YY
        Calendar cal = Calendar.getInstance();
        cal.setTime(order.getDate());
        int dayOfYear = cal.get(Calendar.DAY_OF_YEAR) - 1;
        double bill = order.getBill();
        
        
        // Update Final Bill and Add Order
        double finalTotal = entities.get(dayOfYear).getRevenue() + bill;
        SalesRevenue temp = entities.get(dayOfYear);
        temp.setRevenue(finalTotal);
        temp.addOrderToList(order);

        entities.set(dayOfYear, temp);
    }

    public SalesRevenue getSalesRevenueByDay(Date date, boolean monthly) throws InvalidSalesRevenueQueryException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfYear = cal.get(Calendar.DAY_OF_YEAR) - 1;
        try {
            return entities.get(dayOfYear);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidSalesRevenueQueryException("There is no sales revenue data for this date yet.");
        }
    } 

    /**
     * To print the sales revenue by the date given
     * @param date GregorianCalender of the SalesRevenue
     */
    // public void printByDay(Date date, boolean monthly) throws ParseException, InvalidSalesRevenueQueryException {
    //     // Tabulate by Day
    //     SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yy");
    //     String dateAsString = fmt.format(date);
    //     int count = 1;
    //     Calendar cal = Calendar.getInstance();
    //     cal.setTime(date);
    //     int dayOfYear = cal.get(Calendar.DAY_OF_YEAR) - 1;
    //     SalesRevenue queryRevenue = entities.get(dayOfYear);
    //     ArrayList<Order> tempOrderList = queryRevenue.getOrderList();

    //     String title = "Daily Revenue Report";
    //     ArrayList<String> options = new ArrayList<String>();
    //     ArrayList<String> optionHeaders = new ArrayList<String>();
    //     String[] optionsArr = new String[options.size()];
    //     String[] optionHeadersArr = new String[optionHeaders.size()];
    //     int LONGEST_WIDTH = 40;

    //     if (tempOrderList != null) {
    //         System.out.printf(MenuBuilder.buildMenu(title, LONGEST_WIDTH));
    //         options.add(dateAsString);
    //         optionHeaders.add("Date");
    //         optionsArr = new String[options.size()];
    //         optionHeadersArr = new String[optionHeaders.size()];
    //         System.out.println(MenuBuilder.buildMenu(LONGEST_WIDTH, options.toArray(optionsArr), optionHeaders.toArray(optionHeadersArr), "-"));
    //         for (int i=0; i<tempOrderList.size(); i++){
    //             //System.out.printf("Order %d\n", count++);
    //             options.clear();
    //             optionHeaders.clear();
    //             Order tempOrder = tempOrderList.get(i);
    //             optionHeaders.add("Order");
    //             options.add(String.format("%d", count++));
    //             for (int j=0; j<tempOrder.ordered.size(); j++){
    //                 if(tempOrder.ordered.get(j) instanceof PackageItem){
    //                     options.add("Package");
    //                 }
    //                 else {
    //                     options.add("Ala Carte");
    //                 }
    //                 optionHeaders.add(tempOrder.ordered.get(j).getName());
    //             }
    //             optionsArr = new String[options.size()];
    //             optionHeadersArr = new String[optionHeaders.size()];
    //             System.out.println(MenuBuilder.buildMenu(LONGEST_WIDTH, options.toArray(optionsArr), optionHeaders.toArray(optionHeadersArr), "-"));
    //         }
            
    //     } else {
    //         System.out.println("No Orders On " + date);
    //     }


    //     if (!monthly){
    //         double bill;
    //         bill = entities.get(dayOfYear).getRevenue();
    //         String[] revenue = {String.format("%.2f", bill)};
    //         String[] revenueHeader = {"Daily Total Revenue"};
    //         System.out.println(MenuBuilder.buildMenu(LONGEST_WIDTH, revenue, revenueHeader, "="));
    //     }
    // }

    public ArrayList<SalesRevenue> getSalesRevenueByMonth(Date startDate, Date endDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        int startDayOfTheYear = cal.get(Calendar.DAY_OF_YEAR) - 1;

        cal.setTime(endDate);
        int endDayOfTheYear = cal.get(Calendar.DAY_OF_YEAR);

        ArrayList<SalesRevenue> monthlySalesRevenues = new ArrayList<SalesRevenue>();

        for (int i = startDayOfTheYear; i < endDayOfTheYear; i++) {
            monthlySalesRevenues.add(entities.get(i));
        }

        return monthlySalesRevenues;
    }

    /**
     * To print the sales revenue by month given
     * @param startDate GregorianCalender of the start of the month
     * @param endDate GregorianCalender of the end of the month
     */
    // public void printByMonth(Date startDate, Date endDate) throws ParseException, InvalidSalesRevenueQueryException {
    //     // Tabulate by Month
    //     int LONGEST_WIDTH = 40;


    //     System.out.println("Period: " + startDate + " - " + endDate);
    //     Calendar cal = Calendar.getInstance();
    //     cal.setTime(startDate);
    //     int startDayOfTheYear = cal.get(Calendar.DAY_OF_YEAR) - 1;

    //     cal.setTime(endDate);
    //     int endDayOfTheYear = cal.get(Calendar.DAY_OF_YEAR);

    //     String title = "Monthly Revenue Report";
    //     System.out.printf(MenuBuilder.buildMenu(title, LONGEST_WIDTH));

    //     double tabulatedBill = 0;
    //     for(int i=startDayOfTheYear; i<endDayOfTheYear; i++){
    //         tabulatedBill += entities.get(i).getRevenue();
    //         System.out.println();
    //         printByDay(startDate, true);
    //         startDate = addDate(startDate);
    //     }
    //     System.out.println();
    //     System.out.println("=".repeat(LONGEST_WIDTH + 5));
    //     String[] revenue = {String.format("%.2f", tabulatedBill)};
    //     String[] revenueHeader = {"Total Revenue"};
    //     System.out.println(MenuBuilder.buildMenu(LONGEST_WIDTH, revenue, revenueHeader, "="));
    // }

    // public static Date addDate(Date date){
    //     Calendar calendar = Calendar.getInstance();
    //     calendar.setTime(date);
    //     calendar.add(Calendar.DATE,1);
    //     return calendar.getTime();
    // }
}