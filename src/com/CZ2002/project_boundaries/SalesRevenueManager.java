package com.CZ2002.project_boundaries;

import com.CZ2002.project_entities.AlaCarteItem;
import com.CZ2002.project_entities.Order;
import com.CZ2002.project_entities.PackageItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A Control class to execute the logics of SalesRevenue - Print and Calculate
 */
public class SalesRevenueManager extends Manager<Order>{
    // Attributes
    private ArrayList<Double> revenueList;
    private OrderManager orderManager;


    // Constructor
    public SalesRevenueManager(){
        int numDays = Year.of(2021).length();
        this.revenueList = new ArrayList<Double>(numDays);
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
        double finalTotal = this.revenueList.get(dayOfYear) + bill;

        this.revenueList.set(dayOfYear, finalTotal);

    }

    /**
     * To remove the revenue of an order from an ArrayList of revenurs
     * @param order Items ordered by a customer
     */
    public void removeOrder(Order order){
        // Remove from Linked List
        Calendar cal = Calendar.getInstance();
        cal.setTime(order.getDate());
        int dayOfYear = cal.get(Calendar.DAY_OF_YEAR) - 1;
        double bill = order.getBill();

        // Update Final Bill
        double finalTotal = this.revenueList.get(dayOfYear) - bill;

        this.revenueList.set(dayOfYear, finalTotal);
    }

    /**
     * To print the sales revenue by the date given
     * @param date GregorianCalender of the SalesRevenue
     */
    public void printByDay(Date date) throws ParseException {
        // Tabulate by Day
        System.out.println("Date: " + date);
        int count = 1;
        for(int i=0; i<orderManager.order_list.size(); i++){
            if(orderManager.order_list.get(i).getDate()==date){
                System.out.printf("Order %d\n",count);
                count++;
                for(int j=0; j<orderManager.order_list.get(i).ordered.size(); j++)
                {
                    if(orderManager.order_list.get(i).ordered.get(j) instanceof PackageItem){
                        System.out.println(orderManager.order_list.get(i).ordered.get(j) + " (Package)");
                    }
                    else if(orderManager.order_list.get(i).ordered.get(j) instanceof AlaCarteItem){
                        System.out.println(orderManager.order_list.get(i).ordered.get(j) + " (Ala Carte)");
                    }
                }
            }
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfYear = cal.get(Calendar.DAY_OF_YEAR) - 1;
        double bill = revenueList.get(dayOfYear);

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
        for(int i=0; i<orderManager.order_list.size(); i++){
            if(!orderManager.order_list.get(i).getDate().before(startDate) && !orderManager.order_list.get(i).getDate().after(endDate) ){
                System.out.printf("Order %d\n",count);
                count++;
                for(int j=0; j<orderManager.order_list.get(i).ordered.size(); j++)
                {
                    if(orderManager.order_list.get(i).ordered.get(j) instanceof PackageItem){
                        System.out.println(orderManager.order_list.get(i).ordered.get(j) + " (Package)");
                    }
                    else if(orderManager.order_list.get(i).ordered.get(j) instanceof AlaCarteItem){
                        System.out.println(orderManager.order_list.get(i).ordered.get(j) + " (Ala Carte)");
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
            tabulatedBill += this.revenueList.get(i);
        }
        System.out.println("Total Revenue is: " + tabulatedBill);
    }
}

