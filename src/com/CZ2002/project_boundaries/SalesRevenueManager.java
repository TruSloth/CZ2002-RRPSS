package com.CZ2002.project_boundaries;

import java.text.ParseException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.CZ2002.project_entities.Order;
import com.CZ2002.project_entities.PackageItem;
import com.CZ2002.project_entities.SalesRevenue;
import com.CZ2002.project_exceptions.InvalidSalesRevenueQueryException;

/**
 * A Control class to execute the logics of SalesRevenue - Print and Calculate
 */
public class SalesRevenueManager extends Manager<SalesRevenue>{
    // Attributes

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

    /**
     * To print the sales revenue by the date given
     * @param date GregorianCalender of the SalesRevenue
     */
    public void printByDay(Date date, boolean monthly) throws ParseException, InvalidSalesRevenueQueryException {
        // Tabulate by Day
        System.out.println("Date: " + date);
        int count = 1;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfYear = cal.get(Calendar.DAY_OF_YEAR) - 1;
        SalesRevenue queryRevenue = entities.get(dayOfYear);
        ArrayList<Order> tempOrderList = queryRevenue.getOrderList();

        if (tempOrderList != null) {
            for (int i=0; i<tempOrderList.size(); i++){
                System.out.printf("Order %d\n", count++);
                Order tempOrder = tempOrderList.get(i);
                for (int j=0; j<tempOrder.ordered.size(); j++){
                    if(tempOrder.ordered.get(j) instanceof PackageItem){
                        System.out.println(tempOrderList.get(i).ordered.get(j) + " (Package)");
                    }
                    else {
                        System.out.println(tempOrderList.get(i).ordered.get(j) + " (Ala Carte)");
                    }
                }
            }
        } else {
            System.out.println("No Orders On " + date);
        }


        if (!monthly){
            double bill;
            bill = entities.get(dayOfYear).getRevenue();
            System.out.println("Total Revenue is: " + bill);
        }
    }

    /**
     * To print the sales revenue by month given
     * @param startDate GregorianCalender of the start of the month
     * @param endDate GregorianCalender of the end of the month
     */
    public void printByMonth(Date startDate, Date endDate) throws ParseException, InvalidSalesRevenueQueryException {
        // Tabulate by Month
        System.out.println("Period: " + startDate + " - " + endDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        int startDayOfTheYear = cal.get(Calendar.DAY_OF_YEAR) - 1;

        cal.setTime(endDate);
        int endDayOfTheYear = cal.get(Calendar.DAY_OF_YEAR) - 1;

        double tabulatedBill = 0;
        for(int i=startDayOfTheYear; i<endDayOfTheYear; i++){
            tabulatedBill += entities.get(i).getRevenue();
            printByDay(startDate, true);
            startDate = addDate(startDate);
        }
        System.out.println("Total Revenue is: " + tabulatedBill);
    }

    public static Date addDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,1);
        return calendar.getTime();
    }
}

