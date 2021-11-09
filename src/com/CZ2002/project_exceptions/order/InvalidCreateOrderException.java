package com.CZ2002.project_exceptions.order;

public class InvalidCreateOrderException extends Exception{
    public InvalidCreateOrderException() {
        super();
    }

    public InvalidCreateOrderException(String message) {
        super(message);
    }
}