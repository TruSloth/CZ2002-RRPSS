package com.CZ2002.commands.order;

import com.CZ2002.boundaries.MenuManager;
import com.CZ2002.boundaries.OrderManager;
import com.CZ2002.entities.MenuItem;
import com.CZ2002.exceptions.InvalidRemoveItemOrderException;
import com.CZ2002.interfaces.ICommand;

/**
 * This class implements {@link ICommand} to complete the 'Remove item from order' action.
 */
public class RemoveItemOrderCommand implements ICommand<Void, InvalidRemoveItemOrderException> {
    private MenuManager menuManager;
    private String item;
    private OrderManager orderManager;
    private int tableRemove;

    /**
     * Constructor that accepts the necessary parameters for {@code execute} to successfully complete.
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
     * Completes the 'Remove Item From Order' action.
     *
     * @return Void
     * @throws InvalidRemoveItemOrderException  if the item could not be located
     */
    @Override
    public Void execute() throws InvalidRemoveItemOrderException{
        MenuItem menuItem = menuManager.getItem(item);
        if ( menuItem == null ){
            throw new InvalidRemoveItemOrderException("Item Not Found in Menu!");
        }
        int temp;
        temp = orderManager.removeItemOrder( menuItem , tableRemove );
        if (orderManager.removeItemOrder( menuItem , tableRemove ) == -1){
            throw new InvalidRemoveItemOrderException("Table Not Found!");
        }
        else if (temp == 0){
            throw new InvalidRemoveItemOrderException("Item Not Found in Order!");
        }
        return null;
    }
}