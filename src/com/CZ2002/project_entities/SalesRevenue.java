package com.CZ2002.project_entities;

import java.util.ArrayList;

public class SalesRevenue extends RestaurantEntity{
    //TODO: DOCSTRING
    private double revenue;
    private ArrayList<Order> orderList;

    public SalesRevenue(){
        this.revenue = 0.0;
        orderList = new ArrayList<Order>();
    }

    public double getRevenue() {
        return this.revenue;
    }

    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public void setOrderList(ArrayList<Order> orderList) {
        this.orderList = orderList;
    }

    public void addOrderToList(Order order){
        this.orderList.add(order);
    }
}
