package com.CZ2002.exceptions;
/**
 * The {@code InvalidMenuItemException} is thrown to indicate that a Menu Item cannot be added or removed
 */
public class InvalidMenuItemException extends Exception{
	public InvalidMenuItemException() {
        super();
    }

    public InvalidMenuItemException(String message) {
        super(message);
    }
}
