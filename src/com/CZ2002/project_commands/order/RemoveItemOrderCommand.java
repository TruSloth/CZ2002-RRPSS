package com.CZ2002.project_commands.order;

import com.CZ2002.project_boundaries.MenuManager;
import com.CZ2002.project_boundaries.OrderManager;
import com.CZ2002.project_entities.MenuItem;
import com.CZ2002.project_exceptions.InvalidRemoveItemOrderException;
import com.CZ2002.project_interfaces.ICommand;

import java.util.NoSuchElementException;

/**
 * This class implements {@link ICommand} to complete the 'Remove item from order' action.
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
        this.orderManager = orderManager;
        this.tableRemove = tableRemove;
    }

    /**
     * Executes the method to removeItem from Order in OrderManager
     */
    @Override
    public Void execute() throws InvalidRemoveItemOrderException{
        try {
            this.menuItem = menuManager.getItem(item);
        } catch (NoSuchElementException e){
            throw new InvalidRemoveItemOrderException("invalid item");
        }
        try {
            orderManager.removeItemOrder( menuItem , tableRemove );
        } catch (NoSuchElementException e) {
            throw new InvalidRemoveItemOrderException("Invalid table");
        }

        return null;
    }
}