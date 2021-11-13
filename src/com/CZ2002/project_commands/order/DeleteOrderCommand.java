package com.CZ2002.project_commands.order;

import com.CZ2002.project_boundaries.OrderManager;
import com.CZ2002.project_boundaries.SalesRevenueManager;
import com.CZ2002.project_boundaries.TableManager;
import com.CZ2002.project_entities.Order;
import com.CZ2002.project_exceptions.order.InvalidDeleteOrderException;
import com.CZ2002.project_interfaces.ICommand;

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
                Order o = orderManager.deleteOrder(tableClose);
                return o;
            }
        }
        throw new InvalidDeleteOrderException("Table Not Found!" );
    }
}