package com.CZ2002.project_commands.order;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashSet;

import com.CZ2002.project_boundaries.OrderManager;
import com.CZ2002.project_boundaries.StaffManager;
import com.CZ2002.project_boundaries.TableManager;
import com.CZ2002.project_boundaries.ReservationManager;
import com.CZ2002.project_entities.Staff;
import com.CZ2002.project_exceptions.InvalidStaffException;
import com.CZ2002.project_exceptions.order.InvalidCreateOrderException;
import com.CZ2002.project_interfaces.ICommand;

/**
 * This class implements {@link ICommand} to complete the 'Create a new Order' action.
 */
public class CreateOrderCommand implements ICommand<Void , InvalidCreateOrderException>  {
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
     * @return Void
     * @throws InvalidCreateOrderException  if the order could not be created
     */
    @Override
    public Void execute() throws InvalidCreateOrderException {
        try{
            this.server = staffManager.findStaffById(serverId);

            // Get the table numbers that are reserved for the current time
            Integer[] reservedTables = Arrays.stream(reservationManager.getUnavailableTables(new GregorianCalendar())).boxed().toArray(Integer[]::new);
                                        
            // Get the table numbers that are occupied for the current time
            Integer[] occupiedTables = Arrays.stream(tableManager.getOccupiedTableNos()).boxed().toArray(Integer[]::new);

            // Create a set to remove duplicate table numbers
            HashSet<Integer> set = new HashSet<Integer>();
            set.addAll(Arrays.asList(reservedTables));
            set.addAll(Arrays.asList(occupiedTables));

            int[] unavailableTableNos = set.stream().mapToInt(Integer::intValue).toArray();

            for (int i : unavailableTableNos) {
                System.out.printf("%d ", i);
            }

            int tableNumber = tableManager.getAvailableTable(unavailableTableNos, pax);

            System.out.println("Table Number is :" + tableNumber);

            tableManager.occupyTable(tableNumber);

            orderManager.createNewOrder(tableNumber,pax,server);

        } catch (InvalidStaffException e){
            throw new InvalidCreateOrderException("There is no staff with that ID.");
        } catch (IllegalArgumentException e) {
            throw new InvalidCreateOrderException(e.getMessage());
        }
        
        return null;
    }
}