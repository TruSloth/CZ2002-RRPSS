package com.CZ2002.commands.order;

import com.CZ2002.entities.Order;
import com.CZ2002.exceptions.InvalidPrintOrderException;
import com.CZ2002.interfaces.ICommand;
import com.CZ2002.managers.OrderManager;

import java.util.NoSuchElementException;

/**
 * This class implements {@link ICommand} to complete the 'Print Order' action.
 */
public class PrintOrderCommand implements ICommand<Order , InvalidPrintOrderException> {
    private OrderManager orderManager;
    private int tablePrint;

    /**
     * Constructor that accepts the necessary parameters for {@code execute} to successfully complete.
     * @param orderManager The OrderManager object that controls Order objects
     * @param tablePrint The table Number which Order that is to be printed
     */
    public PrintOrderCommand(OrderManager orderManager, int tablePrint){
        this.orderManager = orderManager;
        this.tablePrint = tablePrint;
    }

    /**
     * Completes the 'Print Order' action.
     *
     * @return Order The Order Object that is being deleted
     * @throws InvalidPrintOrderException  if the table could not be located
     */
    @Override
    public Order execute() throws InvalidPrintOrderException{
        try {
            return orderManager.getOrder(tablePrint);
        } catch (NoSuchElementException e) {
            throw new InvalidPrintOrderException("Table Not Found!");
        }
    }
}