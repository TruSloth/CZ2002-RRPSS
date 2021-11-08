package com.CZ2002.project_commands.menu;

import com.CZ2002.project_boundaries.MenuManager;

/**
 * This class completes the 'Remove ala carte item from package' action.
 */
public class RemoveFromPackageCommand {
    private MenuManager menuManager;
    private String name;
    private String subname;

    /**
     * Constructor that accepts the necessary parameters for {@code execute} to successfully complete.
     *
     * @param menuManager  the reference to the Restaurant's {@link MenuManager}
     * @param name  the name of PackgeItem
     * @param subName the name of AlaCarteItem to be removed
     */
    public RemoveFromPackageCommand(MenuManager menuManager, String name,String subName) {
        this.menuManager = menuManager;
        this.name = name;
        this.subname = subName;
    }

    /**
     * Completes the 'Remove ala carte item from package' action.
     */
    public void execute() {
        menuManager.removeItemFromPackage(name, subname);
    }
}