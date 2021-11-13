package com.CZ2002.commands.order;

import com.CZ2002.entities.Order;
import com.CZ2002.exceptions.InvalidDeleteOrderException;
import com.CZ2002.interfaces.ICommand;
import com.CZ2002.managers.OrderManager;
import com.CZ2002.managers.SalesRevenueManager;
import com.CZ2002.managers.TableManager;

/**
 * This class implements {@link ICommand} to complete the 'Delete Order' action.
 */
public class DeleteOrderCommand implements ICommand<Order , InvalidDeleteOrderException> {
    private SalesRevenueManager salesRevenueManager;
    private TableManager tableManager;
    private OrderManager orderManager;
    private int tableClose;

    /**
     * Constructor that accepts the necessary parameters for {@code execute} to successfully complete.
     * @param orderManager The OrderManager object that controls Orders objects
     * @param salesRevenueManager The SalesRevenueManager object that controls SaleRevenue objects
     * @param tableClose The table Number which the Order is being closed
     * @param tableManager The TableManager object that controls table objects
     */
    public DeleteOrderCommand(OrderManager orderManager, TableManager tableManager, SalesRevenueManager salesRevenueManager, int tableClose){
        this.orderManager = orderManager;
        this.tableManager = tableManager;
        this.salesRevenueManager = salesRevenueManager;
        this.tableClose = tableClose;
    }

    /**
     * Completes the following actions:
     * Adds the order to SalesRevenue Manager for archive
     * Removing an order from the ArrayList of active orders
     * Indicate/Unoccupy table in TableManager
     * @return Void
     * @throws InvalidDeleteOrderException If table could not be located
     */
    @Override
    public Order execute() throws InvalidDeleteOrderException{
        for ( int i  = 0 ; i < orderManager.getNumOfOrders(); i++)
        {
            if ( orderManager.getOrderByIndex(i).getTable() == tableClose ){
                salesRevenueManager.addOrder(orderManager.getOrderByIndex(i));
                tableManager.unoccupyTable(tableClose);
                return orderManager.deleteOrder(tableClose);
            }
        }
        throw new InvalidDeleteOrderException("Table Not Found!" );
    }
}