package com.CZ2002.project_exceptions;

public class InvalidCreateOrderException extends Exception{
    public InvalidCreateOrderCommandException() {
        super();
    }

    public InvalidCreateOrderCommandException(String message) {
        super(message);
    }
}
