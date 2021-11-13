package com.CZ2002.exceptions;

public class InvalidMenuItemException extends Exception{
	public InvalidMenuItemException() {
        super();
    }

    public InvalidMenuItemException(String message) {
        super(message);
    }
}
