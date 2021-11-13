package com.CZ2002.exceptions;
/**
 * The {@code InvalidPrintOrderException} is thrown to indicate that the order does not exist for the input table number
 */
public class InvalidPrintOrderException extends Exception{
    public InvalidPrintOrderException() {
        super();
    }

    public InvalidPrintOrderException(String message) {
        super(message);
    }
}