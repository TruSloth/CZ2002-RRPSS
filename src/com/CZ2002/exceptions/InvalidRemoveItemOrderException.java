package com.CZ2002.exceptions;
/**
 * The {@code InvalidRemoveItemOrderException} is thrown to indicate that the Menu Item does not exist
 */
public class InvalidRemoveItemOrderException extends Exception{
    public InvalidRemoveItemOrderException() {
        super();
    }

    public InvalidRemoveItemOrderException(String message) {
        super(message);
    }
}