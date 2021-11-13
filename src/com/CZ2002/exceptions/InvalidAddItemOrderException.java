package com.CZ2002.exceptions;

/**
 * The {@code InvalidAddItemOrderException} is thrown to indicate that an order cannot be added
 */
public class InvalidAddItemOrderException extends Exception {
    public InvalidAddItemOrderException() {
        super();
    }

    public InvalidAddItemOrderException(String message) {
        super(message);
    }
}