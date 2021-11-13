package com.CZ2002.exceptions.order;

public class InvalidAddItemOrderException extends Exception {
    public InvalidAddItemOrderException() {
        super();
    }

    public InvalidAddItemOrderException(String message) {
        super(message);
    }
}