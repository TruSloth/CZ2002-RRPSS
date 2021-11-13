package com.CZ2002.project_commands.order;

import com.CZ2002.project_entities.MenuItem;
import com.CZ2002.project_exceptions.order.InvalidAddItemOrderException;
import com.CZ2002.project_interfaces.ICommand;
import com.CZ2002.project_managers.MenuManager;
import com.CZ2002.project_managers.OrderManager;

/**
 * This class implements {@link ICommand} to complete the 'Add Item to Order' action.
 */
public class AddItemOrderCommand implements ICommand<Void , InvalidAddItemOrderException> {
    private MenuManager menuManager;
    private String item;
    private OrderManager orderManager;
    private int tableAdd;


    /**
     * Constructor that accepts the necessary parameters for {@code execute} to successfully complete.
     * @param menuManager The MenuManager object that controls MenuItem objects
     * @param orderManager The OrderManager object that controls Order objects
     * @param tableAdd The table Number which the item is to be added to the Order
     * @param addItem The name of the MenuItem object that is to be added to the Order
     */
    public AddItemOrderCommand( MenuManager menuManager , OrderManager orderManager
            , int tableAdd  , String addItem){
        this.menuManager = menuManager;
        this.item = addItem;
        this.orderManager = orderManager;
        this.tableAdd = tableAdd;
    }

    /**
     * Completes the 'Add Item To Order' action.
     *
     * @return Void
     * @throws InvalidAddItemOrderException  if the item could not be located
     */
    @Override
    public Void execute() throws InvalidAddItemOrderException{
        MenuItem menuItem = menuManager.getItem(item);
        if (menuItem == null){
            throw new InvalidAddItemOrderException("Item Not Found in Menu!");
        }
        if (orderManager.addItemOrder( menuItem , tableAdd ) == -1 ){
            throw new InvalidAddItemOrderException("Table Not Found!");
        }
        return null;
    }
}