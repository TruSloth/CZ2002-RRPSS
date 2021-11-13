package com.CZ2002.project_commands.menu;

import com.CZ2002.project_exceptions.InvalidMenuItemException;
import com.CZ2002.project_interfaces.ICommand;
import com.CZ2002.project_managers.MenuManager;

/**
 * This class completes the 'Remove item' action.
 */
public class RemoveMenuItemCommand implements ICommand<Void, InvalidMenuItemException> {
    private MenuManager menuManager;
    private String name;

    /**
     * Constructor that accepts the necessary parameters for {@code execute} to successfully complete.
     *
     * @param menuManager  the reference to the Restaurant's {@link MenuManager}
     * @param name  the name of MenuItem
     */
    public RemoveMenuItemCommand(MenuManager menuManager, String name) {
        this.menuManager = menuManager;
        this.name = name;
    }

    /**
     * Completes the 'Remove item' action.
     */
    public Void execute() throws InvalidMenuItemException {
        if(menuManager.getItem(name)==null) {
            throw new InvalidMenuItemException("Requested MenuItem Does Not Exist!");
        }
        menuManager.removeItem(name);
    	return null;
    }
}
