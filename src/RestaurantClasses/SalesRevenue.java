package RestaurantClasses;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class SalesRevenue {
    // Stores the TotalSalesRevenue for a particular day
    // Should be generated at the beginning of each working day and orders should be added to as the day progresses
    private ArrayList<Order> orderList;
    private GregorianCalendar date;

    public SalesRevenue(GregorianCalendar date) {
        this.date = date;
    }

    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public void addOrder(Order order) {
        orderList.add(order);
    }

    public double calculateTotalRevenue() {
        double totalRevenue = 0;
        for (Order order : orderList) {
            totalRevenue += order.getBill();
        }
        return totalRevenue;
    }
}
