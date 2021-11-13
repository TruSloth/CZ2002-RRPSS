package com.CZ2002.commands.order;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashSet;

import com.CZ2002.boundaries.OrderManager;
import com.CZ2002.boundaries.StaffManager;
import com.CZ2002.boundaries.TableManager;
import com.CZ2002.boundaries.ReservationManager;
import com.CZ2002.entities.Order;
import com.CZ2002.entities.Staff;
import com.CZ2002.exceptions.InvalidStaffException;
import com.CZ2002.exceptions.order.InvalidCreateOrderException;
import com.CZ2002.interfaces.ICommand;

/**
 * This class implements {@link ICommand} to complete the 'Create a new Order' action.
 */
public class CreateOrderCommand implements ICommand<Order , InvalidCreateOrderException>  {
    private StaffManager staffManager;
    private OrderManager orderManager;
    private ReservationManager reservationManager;
    private TableManager tableManager;
    private int pax;
    private Staff server;
    private int serverId;

    /**
     * Constructor that accepts the necessary parameters for {@code execute} to successfully complete.
     * @param staffManager The StaffManager object that controls Staff objects
     * @param orderManager The OrderManager object that controls Order objects
     * @param pax Number of diners seated at table
     * @param serverId The ID of the Staff who is/was serving them
     */
    public CreateOrderCommand( StaffManager staffManager ,
                               OrderManager orderManager ,
                               ReservationManager reservationManager,
                               TableManager tableManager,
                               int pax , int serverId ) {
        this.orderManager = orderManager;
        this.staffManager = staffManager;
        this.reservationManager = reservationManager;
        this.tableManager = tableManager;
        this.pax = pax;
        this.serverId = serverId;
    }

    /**
     * Completes the 'Create Order' action.
     *
     * @return the newly created {@link Order}
     * @throws InvalidCreateOrderException  if the order could not be created
     */
    @Override
    public Order execute() throws InvalidCreateOrderException {
        try{
            this.server = staffManager.findStaffById(serverId);

            // Get the table numbers that are reserved for the current time
            Integer[] reservedTables = Arrays.stream(reservationManager.getUnavailableTables(new GregorianCalendar())).boxed().toArray(Integer[]::new);
                                        
            // Get the table numbers that are occupied for the current time
            Integer[] occupiedTables = Arrays.stream(tableManager.getOccupiedTableNos()).boxed().toArray(Integer[]::new);

            // Create a set to remove duplicate table numbers
            HashSet<Integer> set = new HashSet<>();
            set.addAll(Arrays.asList(reservedTables));
            set.addAll(Arrays.asList(occupiedTables));

            int[] unavailableTableNos = set.stream().mapToInt(Integer::intValue).toArray();

            int tableNumber = tableManager.getAvailableTable(unavailableTableNos, pax);

            tableManager.occupyTable(tableNumber);

            orderManager.createNewOrder(tableNumber,pax,server);

            return orderManager.getOrder(tableNumber);

        } catch (InvalidStaffException e){
            throw new InvalidCreateOrderException("Staff Does Not Exist!");
        } catch (IllegalArgumentException e) {
            throw new InvalidCreateOrderException(e.getMessage());
        }
        
    }
}