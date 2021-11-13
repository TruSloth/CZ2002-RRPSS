package com.CZ2002.project_exceptions;

public class InvalidMenuItemException extends Exception{
	public InvalidMenuItemException() {
        super();
    }

    public InvalidMenuItemException(String message) {
        super(message);
    }
}
