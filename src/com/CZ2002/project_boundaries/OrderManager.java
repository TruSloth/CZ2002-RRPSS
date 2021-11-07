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
    public ArrayList<Order> order_list = new ArrayList<Order>();
    /** To Create new active Order object to add into the ArrayList of active orders
     * @param table The Table Number ( 1 to 20 ) which diners are seated at
     * @param pax Number of diners seated at table
     * @param server Staff who is/was serving them
     */
    public void createNewOrder( int table , int pax , Staff server ){
        Order new_order = new Order( table , pax , server );
        order_list.add(new_order);
        System.out.println("new order for table " + table + " has been created");
    }

    /** To Read in the Order to show a summary of Order for the table
     * @param table_number An integer representing the table number which the Order belongs to
     */
    public void printOrder ( int table_number ){
        for ( int i  = 0 ; i < order_list.size() ; i++){
            if ( order_list.get(i).getTable() == table_number ){
                System.out.println("Table number: " + table_number);
                order_list.get(i).printOrdered();
                System.out.print("Total Bill:");
                System.out.println(order_list.get(i).getBill());
                break;
            }
        }
    }

    /**
     * Removes an Item from the Order based on table number
     * @param menuItem The MenuItem Object that is to be deleted from the Order
     * @param table_number An integer representing the table number which the Order belongs to
     */
    public void addItemOrder ( MenuItem menuItem , int table_number )
    {
        for ( int i  = 0 ; i < order_list.size() ; i++) {
            if (order_list.get(i).getTable() == table_number) {
                order_list.get(i).addItem(menuItem);
                break;
            }
        }
    }

    /**
     * Adds an Item from the Order based on table number
     * @param menuItem A MenuItem Object that is to be added to the Order
     * @param table_number An integer representing the table number which the Order belongs to
     */
    public void removeItemOrder ( MenuItem menuItem , int table_number )
    {
        for ( int i  = 0 ; i < order_list.size() ; i++) {
            if (order_list.get(i).getTable() == table_number) {
                order_list.get(i).removeItem(menuItem);
                break;
            }
        }
    }

    /**
     * To delete the Order from the ArrayList of active Orders
     * @param table_number An integer representing the table number which the Order belongs to
     */
    public void deleteOrder ( int table_number ){
        for ( int i  = 0 ; i < order_list.size() ; i++ ){
            if ( order_list.get(i).getTable() == table_number ){
                order_list.remove(i);
                break;
            }
        }
    }
}