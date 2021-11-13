package com.CZ2002.entities;

import java.util.ArrayList;

/**
 * The {@code SalesRevenue} class represents a day of sales revenue made by the restaurant.
 * A sales revenuve must be specified by the amount of revenue and the orders made.
 * <p>
 * The {@code SalesRevenue} class is designed to only hold all data related to
 * a sales revenue and should not implement any logic in relation to sales revenue.
 */
public class SalesRevenue extends RestaurantEntity{
    private double revenue;
    private ArrayList<Order> orderList;

    /**
     * Constructs new sales revenue that represents a day of sales made by the restaurant
     * The constructor takes in no parameters
     * */
    public SalesRevenue(){
        this.revenue = 0.0;
        orderList = new ArrayList<Order>();
    }

    /**
     * Gets the total revenue made in the day.
     * @return total revenue made in the day
     */
    public double getRevenue() {
        return this.revenue;
    }

    /**
     * Gets an ArrayList of orders made in the day
     * @return all the orders made in the day
     */
    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    /**
     * Sets the total renuve
     * @param revenue representing the new revenue amount
     */
    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    /**
     * Sets the orders made
     * @param order representing the order made in the day
     */
    public void addOrderToList(Order order){
        this.orderList.add(order);
    }
}
