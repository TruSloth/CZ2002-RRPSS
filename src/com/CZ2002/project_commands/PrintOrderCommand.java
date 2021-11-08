package com.CZ2002.project_commands;

import com.CZ2002.project_boundaries.OrderManager;
import com.CZ2002.project_exceptions.InvalidPrintOrderException;
import com.CZ2002.project_interfaces.ICommand;

import java.util.NoSuchElementException;

/**
 * This class implements {@link ICommand} to complete the 'Print Order' action.
 */
public class PrintOrderCommand implements ICommand<Void , InvalidPrintOrderException> {
    private OrderManager orderManager;
    private int tablePrint;

    /**
     * @param orderManager The OrderManager object that controls Order objects
     * @param tablePrint The table Number which Order that is to be printed
     */
    public PrintOrderCommand(OrderManager orderManager, int tablePrint){
        this.orderManager = orderManager;
        this.tablePrint = tablePrint;
    }

    /**
     * Printing out the list of MenuItem object that customer at the table ordered
     * Executes the method to printOrder in OrderManager
     */
    @Override
    public Void execute() throws InvalidPrintOrderException{
        try {
            orderManager.printOrder(tablePrint);
        } catch (NoSuchElementException e) {
            throw new InvalidPrintOrderException("Invalid table");
        }

        return null;
    }
}