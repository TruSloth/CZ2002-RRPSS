package com.CZ2002.exceptions;

/**
 * The {@code InvalidCreateOrderException} is thrown to indicate that an order cannot be created
 */
public class InvalidCreateOrderException extends Exception{
    public InvalidCreateOrderException() {
        super();
    }

    public InvalidCreateOrderException(String message) {
        super(message);
    }
}