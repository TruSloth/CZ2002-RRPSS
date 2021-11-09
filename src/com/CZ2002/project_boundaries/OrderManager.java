package com.CZ2002.project_boundaries;
import com.CZ2002.project_entities.Order;
import com.CZ2002.project_entities.MenuItem;
import com.CZ2002.project_entities.Staff;

import java.util.ArrayList;

/**
 * A Control class to execute the logics of Order - Create Read Update Destruction
 * OrderManager will only keep track of active orders
 */
public class OrderManager extends Manager<Order>{
    public OrderManager() {
        entities = new ArrayList<Order>();
    }
    
    /** To Create new active Order object to add into the ArrayList of active orders
     * @param tableNumber The Table Number ( 1 to 20 ) which diners are seated at
     * @param pax Number of diners seated at table
     * @param server Staff who is/was serving them
     */
    public void createNewOrder( int tableNumber , int pax , Staff server ){
        Order newOrder = new Order( tableNumber , pax , server );
        entities.add(newOrder);
        System.out.println("New Order for Table " + tableNumber + " Created");
    }

    /** To Read in the Order to show a summary of Order for the table
     * @param tableNumber An integer representing the table number which the Order belongs to
     */
    public void printOrder ( int tableNumber ){
        for ( int i  = 0 ; i < entities.size() ; i++){
            if ( entities.get(i).getTable() == tableNumber ){
                System.out.println("Table number: " + tableNumber);
                entities.get(i).printOrdered();
                System.out.print("Total Bill:");
                System.out.println(entities.get(i).getBill());
                break;
            }
        }
    }

    /**
     * Removes an Item from the Order based on table number
     * @param menuItem The MenuItem Object that is to be deleted from the Order
     * @param tableNumber An integer representing the table number which the Order belongs to
     */
    public void addItemOrder ( MenuItem menuItem , int tableNumber )
    {
        for ( int i  = 0 ; i < entities.size() ; i++) {
            if (entities.get(i).getTable() == tableNumber) {
                entities.get(i).addItem(menuItem);
                break;
            }
        }
    }

    /**
     * Adds an Item from the Order based on table number
     * @param menuItem A MenuItem Object that is to be added to the Order
     * @param tableNumber An integer representing the table number which the Order belongs to
     */
    public void removeItemOrder ( MenuItem menuItem , int tableNumber )
    {
        for ( int i  = 0 ; i < entities.size() ; i++) {
            if (entities.get(i).getTable() == tableNumber) {
                entities.get(i).removeItem(menuItem);
                break;
            }
        }
    }

    /**
     * To delete the Order from the ArrayList of active Orders
     * @param tableNumber An integer representing the table number which the Order belongs to
     */
    public void deleteOrder ( int tableNumber ){
        for ( int i  = 0 ; i < entities.size() ; i++ ){
            if ( entities.get(i).getTable() == tableNumber ){
                System.out.println("Bill for Table " + tableNumber);
                printOrder(tableNumber);
                entities.remove(i);
                break;
            }
        }
    }

  
    }
}