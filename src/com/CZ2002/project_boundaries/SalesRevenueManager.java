package com.CZ2002.project_boundaries;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.CZ2002.project_entities.Order;
import com.CZ2002.project_entities.SalesRevenue;
import com.CZ2002.project_exceptions.InvalidSalesRevenueQueryException;

/**
 * A Control class to execute the logics of SalesRevenue - Print and Calculate
 */
public class SalesRevenueManager extends Manager<SalesRevenue>{
    // Constructor
    public SalesRevenueManager(){
        int numDays = Year.of(2021).length();
        entities = new ArrayList<>(numDays); //ArrayList revenueList

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
     * Returns the {@link SalesRevenue} for the specified {@link Date} instance.
     * 
     * @param date the {@code Date} for which the {@code SalesRevenue} is for 
     * @return the requested {@code SalesRevenue} 
     * @throws InvalidSalesRevenueQueryException if there is no {@code SalesRevenue} data for the specified {@code Date}
     */
    public SalesRevenue getSalesRevenueByDay(Date date) throws InvalidSalesRevenueQueryException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfYear = cal.get(Calendar.DAY_OF_YEAR) - 1;
        try {
            return entities.get(dayOfYear);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidSalesRevenueQueryException("No existing sales revenue record for " + date);
        }
    } 

    /**
     * Returns the {@link ArrayList} that contains the relevant {@code SalesRevenue} instances.
     * 
     * @param startDate the {@code Date} for which the {@code SalesRevenue} starts
     * @param endDate the {@code Date} for which the {@code SalesRevenue} ends
     * @return the {@code ArrayList} of {@code SalesRevenue} for the specified period
     */
    public ArrayList<SalesRevenue> getSalesRevenueByMonth(Date startDate, Date endDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        int startDayOfTheYear = cal.get(Calendar.DAY_OF_YEAR) - 1;

        cal.setTime(endDate);
        int endDayOfTheYear = cal.get(Calendar.DAY_OF_YEAR);

        ArrayList<SalesRevenue> monthlySalesRevenues = new ArrayList<>();

        for (int i = startDayOfTheYear; i < endDayOfTheYear; i++) {
            monthlySalesRevenues.add(entities.get(i));
        }

        return monthlySalesRevenues;
    }
}