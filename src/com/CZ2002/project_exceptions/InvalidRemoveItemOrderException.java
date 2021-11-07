package com.CZ2002.project_exceptions;

public class InvalidRemoveItemOrderException extends Exception{
    public InvalidRemoveItemOrderException() {
        super();
    }

    public InvalidRemoveItemOrderException(String message) {
        super(message);
    }
}
