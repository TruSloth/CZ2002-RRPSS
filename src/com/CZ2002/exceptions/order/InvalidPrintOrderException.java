package com.CZ2002.exceptions.order;

public class InvalidPrintOrderException extends Exception{
    public InvalidPrintOrderException() {
        super();
    }

    public InvalidPrintOrderException(String message) {
        super(message);
    }
}