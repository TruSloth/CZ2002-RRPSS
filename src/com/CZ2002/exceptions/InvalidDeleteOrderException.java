package com.CZ2002.exceptions;

/**
 * The {@code InvalidDeleteOrderException} is thrown to indicate that an order cannot be deleted
 */
public class InvalidDeleteOrderException extends Exception{
    public InvalidDeleteOrderException() {
        super();
    }

    public InvalidDeleteOrderException(String message) {
        super(message);
    }
}