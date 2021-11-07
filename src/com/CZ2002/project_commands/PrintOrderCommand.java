package com.CZ2002.project_commands;

import com.CZ2002.project_boundaries.OrderManager;

/**
 * A Control Class that executes the PrintOrder Command
 */
public class PrintOrderCommand {
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
    public void execute() {
        orderManager.printOrder(tablePrint);
    }
}