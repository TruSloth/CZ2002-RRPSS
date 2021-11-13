package com.CZ2002.project_boundaries;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import com.CZ2002.project_entities.MenuItem;
import com.CZ2002.project_entities.Order;
import com.CZ2002.project_entities.Staff;
import com.CZ2002.project_entities.Table;

/**
 * A Control class to execute the logics of Order - Create Read Update Destruction
 * OrderManager will only keep track of active orders
 */
public class OrderManager extends Manager<Order>{
    public OrderManager() {
        entities = new ArrayList<>();
    }

    /** To Create new active Order object to add into the ArrayList of active orders
     * @param tableNumber The Table Number ( 1 to 20 ) which diners are seated at
     * @param pax Number of diners seated at table
     * @param server Staff who is/was serving them
     */
    public void createNewOrder( int tableNumber, int pax, Staff server){
        Order newOrder = new Order( tableNumber, pax, server);
        entities.add(newOrder);
    }

    /**
     * Returns the {@link Order} made by the {@link Table} identified by {@code tableNumber}.
     *
     * @param tableNumber An integer representing the table number which the Order belongs to
     * @return The Order based on tableNumber
     * @throws NoSuchElementException Throws exception when Table is not found
     */
    public Order getOrder(int tableNumber) throws NoSuchElementException {
        return entities
                .stream()
                .filter(o -> o.getTable() == tableNumber)
                .findFirst()
                .get();
    }

    /**
     * Removes an Item from the Order based on table number
     * @param menuItem The MenuItem Object that is to be deleted from the Order
     * @param tableNumber An integer representing the table number which the Order belongs to
     * @return An Integer value to indicate if Item is successfully added
     */
    public int addItemOrder ( MenuItem menuItem, int tableNumber)
    {
        for (Order entity : entities) {
            if (entity.getTable() == tableNumber) {
                if (menuItem != null) {
                    entity.addItem(menuItem);
                    return 1;
                } else {
                    return 0;
                }
            }
        }
        return -1;
    }

    /**
     * Adds an Item from the Order based on table number
     * @param menuItem A MenuItem Object that is to be added to the Order
     * @param tableNumber An integer representing the table number which the Order belongs to
     * @return An Integer value to indicate if an Item is successfully removed
     */
    public int removeItemOrder ( MenuItem menuItem, int tableNumber)
    {
        MenuItem temp;
        for (Order entity : entities) {
            if (entity.getTable() == tableNumber) {
                if (menuItem != null) {
                    temp = entity.removeItem(menuItem);
                    if (temp == null) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            }
        }
        return -1;
    }

    /**
     * To delete the {@link Order} from the {@link ArrayList} of active Orders
     * 
     * @param tableNumber An integer representing the table number which the Order belongs to
     * @return The {@code Order} that was deleted
     */
    public Order deleteOrder ( int tableNumber){
        for (int i=0; i < entities.size(); i++){
            if ( entities.get(i).getTable() == tableNumber){
                Order order = entities.get(i);
                entities.remove(i);
                return order;
            }
        }
        return null;
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
     * @return An Integer value to indicate if membership is successfully set
     */
    public int setMembership (int tableNumber, int tableSetMembership){
        for (Order entity : entities) {
            if (entity.getTable() == tableNumber) {
                if (tableSetMembership == 1) {
                    entity.setMembership(true);
                } else if (tableSetMembership == 0) {
                    entity.setMembership(false);
                }
                return 1;
            }
        }
        return -1;
    }
}
