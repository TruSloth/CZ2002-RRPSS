package com.CZ2002.project_boundaries;

import com.CZ2002.project_entities.AlaCarteItem;
import com.CZ2002.project_entities.Order;
import com.CZ2002.project_entities.PackageItem;
import com.CZ2002.project_entities.SalesRevenue;
import com.CZ2002.project_exceptions.InvalidSalesRevenueQueryException;

import java.text.ParseException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A Control class to execute the logics of SalesRevenue - Print and Calculate
 */
public class SalesRevenueManager extends Manager<SalesRevenue>{
    // Attributes
    private OrderManager orderManager; // TODO: REMOVE THIS TO? HAVE TO REMOVE

    // Constructor
    public SalesRevenueManager(){
        int numDays = Year.of(2021).length();
        entities = new ArrayList<>(numDays); //ArrayList revenueList
        this.orderManager = new OrderManager();

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

        // Update Final Bill
        double finalTotal = entities.get(dayOfYear).getRevenue() + bill;
        SalesRevenue temp = entities.get(dayOfYear);
        temp.setRevenue(finalTotal);

        entities.set(dayOfYear, temp);
    }

    /**
     * To print the sales revenue by the date given
     * @param date GregorianCalender of the SalesRevenue
     */
    public void printByDay(Date date) throws ParseException, InvalidSalesRevenueQueryException {
        // Tabulate by Day
        System.out.println("Date: " + date);
        int count = 1;
        for(int i=0; i<orderManager.entities.size(); i++){
            if(orderManager.entities.get(i).getDate()==date){
                System.out.printf("Order %d\n",count);
                count++;
                for(int j=0; j<orderManager.entities.get(i).ordered.size(); j++)
                {
                    if(orderManager.entities.get(i).ordered.get(j) instanceof PackageItem){
                        System.out.println(orderManager.entities.get(i).ordered.get(j) + " (Package)");
                    }
                    else if(orderManager.entities.get(i).ordered.get(j) instanceof AlaCarteItem){
                        System.out.println(orderManager.entities.get(i).ordered.get(j) + " (Ala Carte)");
                    }
                }
            }
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfYear = cal.get(Calendar.DAY_OF_YEAR) - 1;
        double bill;
        bill = entities.get(dayOfYear).getRevenue();

        System.out.println("Total Revenue is: " + bill);
    }

    /**
     * To print the sales revenue by month given
     * @param startDate GregorianCalender of the start of the month
     * @param endDate GregorianCalender of the end of the month
     */
    public void printByMonth(Date startDate, Date endDate) throws ParseException {
        // Tabulate by Month
        System.out.println("Period: " + startDate + " - " + endDate);
        int count = 1;
        for(int i=0; i<orderManager.entities.size(); i++){
            if(!orderManager.entities.get(i).getDate().before(startDate) && !orderManager.entities.get(i).getDate().after(endDate) ){
                System.out.printf("Order %d\n",count);
                count++;
                for(int j=0; j<orderManager.entities.get(i).ordered.size(); j++)
                {
                    if(orderManager.entities.get(i).ordered.get(j) instanceof PackageItem){
                        System.out.println(orderManager.entities.get(i).ordered.get(j) + " (Package)");
                    }
                    else if(orderManager.entities.get(i).ordered.get(j) instanceof AlaCarteItem){
                        System.out.println(orderManager.entities.get(i).ordered.get(j) + " (Ala Carte)");
                    }
                }
            }
        }
        Calendar cal = Calendar.getInstance();

        cal.setTime(startDate);
        int startDayOfTheYear = cal.get(Calendar.DAY_OF_YEAR) - 1;

        cal.setTime(endDate);
        int endDayOfTheYear = cal.get(Calendar.DAY_OF_YEAR) - 1;

        double tabulatedBill = 0;
        for(int i=startDayOfTheYear; i<endDayOfTheYear; i++){
            tabulatedBill += entities.get(i).getRevenue();
        }
        System.out.println("Total Revenue is: " + tabulatedBill);
    }
}

