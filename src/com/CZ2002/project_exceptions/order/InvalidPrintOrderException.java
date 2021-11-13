package com.CZ2002.project_exceptions.order;

public class InvalidPrintOrderException extends Exception{
    public InvalidPrintOrderException() {
        super();
    }

    public InvalidPrintOrderException(String message) {
        super(message);
    }
}