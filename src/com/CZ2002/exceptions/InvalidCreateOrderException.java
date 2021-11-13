package com.CZ2002.exceptions;

public class InvalidCreateOrderException extends Exception{
    public InvalidCreateOrderException() {
        super();
    }

    public InvalidCreateOrderException(String message) {
        super(message);
    }
}