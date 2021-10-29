package com.CZ2002.project_boundaries;
import com.CZ2002.project_entities.Order;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class SalesRevenueMgr {
    // Attributes
    private ArrayList<Double> revenueList;

    // Constructor
    public SalesRevenueMgr(){
        int numDays = Year.of(2021).length();
        this.revenueList = new ArrayList<>(numDays);
    }

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

    public void printByDay(Order order){
        // Tabulate by Day
        Calendar cal = Calendar.getInstance();
        cal.setTime(order.getDate());
        int dayOfYear = cal.get(Calendar.DAY_OF_YEAR) - 1;
        double bill = order.getBill();
        System.out.println(bill);
    }

    public void printByMonth(Date startDate, Date endDate){
        // Tabulate by Month
        Calendar cal = Calendar.getInstance();

        cal.setTime(startDate);
        int startDayOfTheYear = cal.get(Calendar.DAY_OF_YEAR) - 1;

        cal.setTime(endDate);
        int endDayOfTheYear = cal.get(Calendar.DAY_OF_YEAR) - 1;

        double tabulatedBill = 0;

        for(int i=startDayOfTheYear; i<endDayOfTheYear; i++){
            tabulatedBill += this.revenueList.get(i);
        }

        System.out.println(tabulatedBill);
    }
}
