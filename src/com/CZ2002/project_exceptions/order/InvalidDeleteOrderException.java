package com.CZ2002.project_exceptions.order;

public class InvalidDeleteOrderException extends Exception{
    public InvalidDeleteOrderException() {
        super();
    }

    public InvalidDeleteOrderException(String message) {
        super(message);
    }
}