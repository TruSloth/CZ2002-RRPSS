package com.CZ2002.project_commands;

import com.CZ2002.project_boundaries.MenuManager;
import com.CZ2002.project_boundaries.OrderManager;
import com.CZ2002.project_entities.MenuItem;

/**
 * A Control Class that executes the RemoveItemOrder Command
 */
public class RemoveItemOrderCommand {
    private MenuManager menuManager;
    private String item;
    private MenuItem menuItem;
    private OrderManager orderManager;
    private int tableRemove;

    /**
     * To obtain the MenuItem object to be removed from the Order
     * @param menuManager The MenuManager object that controls MenuItem objects
     * @param orderManager The OrderManager object that controls Order objects
     * @param table_remove The table Number which the item is to be remove from the Order
     * @param removeItem The name of the MenuItem object that is to be removed from the Order
     */
    public RemoveItemOrderCommand(MenuManager menuManager , OrderManager orderManager
            , int tableRemove  , String removeItem){
        this.menuManager = menuManager;
        this.item = removeItem;
        this.menuItem = menuManager.getItem(removeItem);
        this.orderManager = orderManager;
        this.tableRemove = tableRemove;
    }

    /**
     * Executes the method to removeItem from Order in OrderManager
     */
    public void execute(){
        orderManager.removeItemOrder( menuItem , tableRemove );
    }
}