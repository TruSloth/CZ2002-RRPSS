package com.CZ2002.project_commands.order;

import com.CZ2002.project_boundaries.OrderManager;
import com.CZ2002.project_boundaries.StaffManager;
import com.CZ2002.project_entities.Staff;
import com.CZ2002.project_exceptions.InvalidStaffException;
import com.CZ2002.project_interfaces.ICommand;

import java.util.NoSuchElementException;

/**
 * This class implements {@link ICommand} to complete the 'Create a new Order' action.
 */
public class CreateOrderCommand implements ICommand<Void , InvalidStaffException>  {
    private StaffManager staffManager;
    private OrderManager orderManager;
    private int tableNumber;
    private int pax;
    private Staff server;
    private int serverId;

    /**
     * @param staffManager The StaffManager object that controls Staff objects
     * @param orderManager The OrderManager object that controls Order objects
     * @param tableNumber The table Number which the new customer is being seated at
     * @param pax Number of diners seated at table
     * @param serverId The ID of the Staff who is/was serving them
     */
    public CreateOrderCommand( StaffManager staffManager ,
                               OrderManager orderManager , int tableNumber
            , int pax , int serverId){
        this.orderManager = orderManager;
        this.staffManager = staffManager;
        this.tableNumber = tableNumber;
        this.pax = pax;
        this.serverId = serverId;
    }

    /**
     * Creating a new active Order to add into the ArrayList of active orders
     * Executes the method to createNewOrder in OrderManager
     */
    @Override
    public Void execute() throws InvalidStaffException {
        try{
            this.server = staffManager.findStaffById(serverId);
        } catch (NoSuchElementException e){
            throw new InvalidStaffException("Invalid Staff ID");
        };
        orderManager.createNewOrder(tableNumber,pax,server);
        return null;
    }
}