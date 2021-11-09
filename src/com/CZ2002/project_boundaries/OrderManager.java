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
        boolean found = false;
        for ( int i  = 0 ; i < entities.size() ; i++){
            if ( entities.get(i).getTable() == tableNumber ){
                entities.get(i).printOrdered();
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Table Does Not Exist");
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
                if (menuItem != null) {
                    entities.get(i).addItem(menuItem);
                } else {
                    System.out.println("No Such Item");
                }
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
                if (menuItem != null) {
                    entities.get(i).removeItem(menuItem);
                } else {
                    System.out.println("No Such Item");
                }
                break;
            }
        }
    }

    /**
     * To delete the Order from the ArrayList of active Orders
     * @param tableNumber An integer representing the table number which the Order belongs to
     */
    public void deleteOrder ( int tableNumber ){
        boolean found = false;

        for ( int i  = 0 ; i < entities.size() ; i++ ){
            if ( entities.get(i).getTable() == tableNumber ){
                printOrder(tableNumber);
                entities.remove(i);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Table Does Not Exists");
        }
    }

    /** 
     * Returns the {@link Order} instance found at index position {@code index} in {@code entities}. 
     *  
     * @param index the index of the {@code Order} instance in {@code entities} 
     * @return the {@code Order} instance desired 
     */ 
    public Order getOrderByIndex(int index) { 
        return entities.get(index); 
    } 
 
    /** 
     * Returns the number of active {@link Order} instances found in {@code entities}. 
     *  
     * @return the number of active {@code Order} instances 
     */ 
    public int getNumOfOrders() { 
        return entities.size(); 
    }
    /**
     * Setting membership status
     * @param tableNumber An integer representing the table number which the Order belongs to
     * @param tableSetMembership An integer value denoting membership(1) or not (0)
     */

    public void setMembership (int tableNumber, int tableSetMembership){
        boolean found = false;
        for ( int i  = 0 ; i < entities.size() ; i++ ){
            if ( entities.get(i).getTable() == tableNumber ){
                found = true;
                if (tableSetMembership == 1){
                    entities.get(i).setMembership(true);
                }
                else if (tableSetMembership == 0){
                    entities.get(i).setMembership(false);
                }
                break;
            }
        }
        if (!found) {
            System.out.println("Table Does Not Exists");
        }
    }
}
