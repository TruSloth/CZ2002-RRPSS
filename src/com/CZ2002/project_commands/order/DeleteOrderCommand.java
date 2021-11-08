package com.CZ2002.project_commands.order;

import com.CZ2002.project_boundaries.OrderManager;
import com.CZ2002.project_boundaries.SalesRevenueManager;
import com.CZ2002.project_boundaries.TableManager;
import com.CZ2002.project_exceptions.InvalidDeleteOrderException;
import com.CZ2002.project_interfaces.ICommand;

import java.util.NoSuchElementException;

/**
 * This class implements {@link ICommand} to complete the 'Delete Order' action.
 */
public class DeleteOrderCommand implements ICommand<Void , InvalidDeleteOrderException> {
    private SalesRevenueManager salesRevenueManager;
    private TableManager tableManager;
    private OrderManager orderManager;
    private int tableClose;

    /**
     * @param orderManager The OrderManager object that controls Orders objects
     * @param salesRevenueManager The SalesRevenueManager object that controls SaleRevenue objects
     * @param tableClose The table Number which the Order is being closed
     * @param tableManager The TableManager object that controls table objects
     */
    public DeleteOrderCommand(OrderManager orderManager, TableManager tableManager, SalesRevenueManager salesRevenueManager, int tableClose){
        this.orderManager = orderManager;
        this.salesRevenueManager = salesRevenueManager;
        this.tableClose = tableClose;
        this.tableManager = tableManager;
    }

    /**
     * Adds the order to SalesRevenue Manager for archive
     * Removing an order from the ArrayList of active orders
     * Indicate/Unoccupy table in TableManager
     * Executes the method to deleteOrder in OrderManager
     */
    @Override
    public Void execute() throws InvalidDeleteOrderException{
        try {
            for ( int i  = 0 ; i < orderManager.entities.size() ; i++)
            {
                if ( orderManager.entities.get(i).getTable() == tableClose ){
                    salesRevenueManager.addOrder(orderManager.entities.get(i));
                    tableManager.unoccupyTable(tableClose);
                    orderManager.deleteOrder(tableClose);
                }
            }
        } catch (NoSuchElementException e) {
            throw new InvalidDeleteOrderException("Order not found for Table " + tableClose );
        }

        return null;
    }
}