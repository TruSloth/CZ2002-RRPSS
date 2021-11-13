package com.CZ2002.project_interfaces;

import java.text.ParseException;

import com.CZ2002.project_consoles.ConsoleDisplay;

/**
 * The {@code iCommand} interface defines the contract between the boundary and control layers.
 * <p>
 * More specifically, it defines the possible actions that can be taken from a specific {@link ConsoleDisplay} subclass.
 * Implementations of this class should pass in all necessary parameters through the class constructor and
 * should avoid, as far as possible, requesting input or displaying output within the {@code iCommand} class itself.
 * <p>
 * The {@code iCommand} class primarily calls the relevant methods of the necessary control classes
 * required to complete the action for which it is designed. This coordination of methods should be
 * encapsulated within {@link #execute()}. In this way, the boundary layer only needs to instantiate a new
 * {@code iCommand} class and call {@code execute()} to perform the desired action.
 *
 * @param <T>  The type returned by {@code execute()}
 * @param <E>  The possible {@link Exception} type thrown
 */
public interface ICommand<T, E extends Exception> {

    /**
     * Coordinates and calls the necessary methods to complete the action for whihc this {@code ICommand} is designed.
     *
     * @return T the type returned by {@code execute()}, {@link Void} if nothing is returned
     * @throws E  if {@code execute()} could not be completed successfully
     */
    abstract T execute() throws E, ParseException;
}