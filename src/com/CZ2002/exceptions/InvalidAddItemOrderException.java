package com.CZ2002.exceptions;

public class InvalidAddItemOrderException extends Exception {
    public InvalidAddItemOrderException() {
        super();
    }

    public InvalidAddItemOrderException(String message) {
        super(message);
    }
}