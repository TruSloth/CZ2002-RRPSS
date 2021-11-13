package com.CZ2002.exceptions.order;

public class InvalidSetMembership extends Exception{
    public InvalidSetMembership() {
        super();
    }

    public InvalidSetMembership(String message) {
        super(message);
    }
}