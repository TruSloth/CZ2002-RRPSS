package com.CZ2002.exceptions;

public class InvalidSetMembership extends Exception{
    public InvalidSetMembership() {
        super();
    }

    public InvalidSetMembership(String message) {
        super(message);
    }
}