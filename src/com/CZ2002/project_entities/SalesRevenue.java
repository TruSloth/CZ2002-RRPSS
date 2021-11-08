package com.CZ2002.project_entities;

public class SalesRevenue extends RestaurantEntity{
    //TODO: DOCSTRING
    private double revenue;

    public SalesRevenue(){
        this.revenue = 0.0;
    }

    public double getRevenue() {
        return this.revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
}
