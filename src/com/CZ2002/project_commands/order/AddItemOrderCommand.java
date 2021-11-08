package com.CZ2002.project_commands.order;

import com.CZ2002.project_boundaries.MenuManager;
import com.CZ2002.project_boundaries.OrderManager;
import com.CZ2002.project_entities.MenuItem;
import com.CZ2002.project_exceptions.InvalidAddItemOrderException;
import com.CZ2002.project_interfaces.ICommand;

import java.util.NoSuchElementException;

/**
 * This class implements {@link ICommand} to complete the 'Add Item to Order' action.
 */
public class AddItemOrderCommand implements ICommand<Void , InvalidAddItemOrderException> {
    private MenuManager menuManager;
    private String item;
    private MenuItem menuItem;
    private OrderManager orderManager;
    private int tableAdd;


    /**
     * To obtain the MenuItem object to be added to the Order
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
     * Executes the method to addItem to Order in OrderManager
     */
    @Override
    public Void execute() throws InvalidAddItemOrderException{
        try {
            this.menuItem = menuManager.getItem(item);
        } catch (NoSuchElementException e){
            throw new InvalidAddItemOrderException("invalid item");
        }
        try {
            orderManager.addItemOrder( menuItem , tableAdd );
        } catch (NoSuchElementException e) {
            throw new InvalidAddItemOrderException("Invalid table");
        }

        return null;
    }
}