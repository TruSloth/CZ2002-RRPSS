package com.CZ2002.project_commands;

import com.CZ2002.project_boundaries.OrderManager;
import com.CZ2002.project_entities.MenuItem;

/**
 * A Control Class that executes the AddItemOrder Command
 */
public class AddItemOrderCommand {
    private MenuManager menuManager;
    private String item;
    private MenuItem menuItem;
    private OrderManager orderManager;
    private int tableAdd;


    /**
     * To obtain the MenuItem object to be added to the Order
     * @param menuManager The MenuManager object that controls MenuItem objects
     * @param orderManager The OrderManager object that controls Order objects
     * @param table_add The table Number which the item is to be added to the Order
     * @param addItem The name of the MenuItem object that is to be added to the Order
     */
    public AddItemOrderCommand( MenuManager menuManager , OrderManager orderManager
            , int tableAdd  , String addItem){
        this.menuManager = menuManager;
        this.item = addItem;
        this.menuItem = menuManager.getItem(addItem);
        this.orderManager = orderManager;
        this.tableAdd = tableAdd;
    }

    /**
     * Executes the method to addItem to Order in OrderManager
     */
    public void execute(){
        orderManager.addItemOrder( menuItem , tableAdd );
    }
}