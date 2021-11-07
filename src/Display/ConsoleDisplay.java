package Display;

import java.util.Scanner;

import ManagerClasses.iMainManager;
import Utils.MenuView;

/**
 * The {@code ConsoleDisplay} class represents the interface that a user will interact with.
 * <p>
 * All {@code ConsoleDisplay} implementations maintain their own reference to the {@link iMainManager}
 * and must implement {@link #displayConsoleOptions()} and {@link #handleConsoleOptions()}.
 */
public abstract class ConsoleDisplay {
    /**
     * The reference to the {@link iMainManager} instance to be used.
     */
    protected iMainManager mainManager;
    protected Scanner sc;

    /**
     * The method to call to produce output to be shown to the user.
     * <p>
     * The last option to be displayed to the user should be the 'back' option
     * or something equivalent.
     * 
     * @return  the number of options to be displayed
     */
    public abstract int displayConsoleOptions();

    /**
     * The method to call to accept input from the user.
     * <p>
     * This method then performs the necessary transition of control from this
     * {@code ConsoleDisplay} to another or to a {@link iCommand} instance.
     * 
     * @return  the {@link MenuView} that represents the state of the interface
     */
    public abstract MenuView handleConsoleOptions();
}
