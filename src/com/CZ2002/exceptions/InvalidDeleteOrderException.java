package com.CZ2002.exceptions;

public class InvalidDeleteOrderException extends Exception{
    public InvalidDeleteOrderException() {
        super();
    }

    public InvalidDeleteOrderException(String message) {
        super(message);
    }
}