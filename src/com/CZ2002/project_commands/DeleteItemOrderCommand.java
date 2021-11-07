package com.CZ2002.project_commands;

import com.CZ2002.project_boundaries.OrderManager;
import com.CZ2002.project_boundaries.SalesRevenueManager;

/**
 * A Control Class that executes the DeleteOrder Command
 */
public class DeleteItemOrderCommand {
    private SalesRevenueManager salesRevenueManager;
    private OrderManager orderManager;
    private int tableClose;

    /**
     * @param orderManager The OrderManager object that controls Orders objects
     * @param salesRevenueManager The SalesRevenueManager object that controls SaleRevenue objects
     * @param tableClose The table Number which the Order is being closed
     */
    public DeleteItemOrderCommand(OrderManager orderManager, SalesRevenueManager salesRevenueManager, int tableClose){
        this.orderManager = orderManager;
        this.salesRevenueManager = salesRevenueManager;
        this.tableClose = tableClose;
    }

    /**
     * Adds the order to SalesRevenue Manager for archive
     * Removing an order from the ArrayList of active orders
     * Executes the method to deleteOrder in OrderManager
     */
    public void execute(){
        for ( int i  = 0 ; i < orderManager.order_list.size() ; i++)
        {
            salesRevenueManager.addOrder(orderManager.order_list.get(i));
            orderManager.deleteOrder(tableClose);
        }
    }
}