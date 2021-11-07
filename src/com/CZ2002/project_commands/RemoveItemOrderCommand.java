package com.CZ2002.project_commands;

import com.CZ2002.project_boundaries.MenuManager;
import com.CZ2002.project_boundaries.OrderManager;
import com.CZ2002.project_entities.MenuItem;
import com.CZ2002.project_exceptions.InvalidRemoveItemOrderException;
import com.CZ2002.project_interfaces.ICommand;

/**
 * A Control Class that executes the RemoveItemOrder Command
 */
public class RemoveItemOrderCommand implements ICommand<Void, InvalidRemoveItemOrderException> {
    private MenuManager menuManager;
    private String item;
    private MenuItem menuItem;
    private OrderManager orderManager;
    private int tableRemove;

    /**
     * To obtain the MenuItem object to be removed from the Order
     * @param menuManager The MenuManager object that controls MenuItem objects
     * @param orderManager The OrderManager object that controls Order objects
     * @param tableRemove The table Number which the item is to be remove from the Order
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
    @Override
    public Void execute() throws InvalidRemoveItemOrderException{
        orderManager.removeItemOrder( menuItem , tableRemove );
        return null;
    }
}