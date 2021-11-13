package com.CZ2002.commands.menu;

import com.CZ2002.entities.AlaCarteItem;
import com.CZ2002.entities.PackageItem;
import com.CZ2002.exceptions.InvalidMenuItemException;
import com.CZ2002.interfaces.ICommand;
import com.CZ2002.managers.MenuManager;

/**
 * This class completes the 'Add ala carte item to package' action.
 */
public class AddToPackageCommand implements ICommand<Void, InvalidMenuItemException>{
    private MenuManager menuManager;
    private String name;
    private String subName;

    /**
     * Constructor that accepts the necessary parameters for {@code execute} to successfully complete.
     *
     * @param menuManager  the reference to the Restaurant's {@link MenuManager}
     * @param name  the name of PackgeItem
     * @param subName the name of AlaCarteItem to be added
     */
    public AddToPackageCommand(MenuManager menuManager, String name,String subName) {
        this.menuManager = menuManager;
        this.name = name;
        this.subName = subName;
    }

    /**
     * Completes the 'Add ala carte item to package' action.
     */
    public Void execute() throws InvalidMenuItemException {
        menuManager.addItemToPackage(name, subName);
        if(!(menuManager.getItem(name) instanceof PackageItem) || menuManager.getItem(name) ==null) {
            throw new InvalidMenuItemException("Requested Package Item Does Not Exist!");
        }
        if (!(menuManager.getItem(subName) instanceof AlaCarteItem) || menuManager.getItem(subName) ==null) {
            throw new InvalidMenuItemException("Requested Ala Carte Item Does Not Exist!");
        }
        return null;
    }
}
