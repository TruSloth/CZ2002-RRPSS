package com.CZ2002.project_exceptions.order;

public class InvalidAddItemOrderException extends Exception {
    public InvalidAddItemOrderException() {
        super();
    }

    public InvalidAddItemOrderException(String message) {
        super(message);
    }
}