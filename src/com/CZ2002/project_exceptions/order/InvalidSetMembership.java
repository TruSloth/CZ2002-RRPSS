package com.CZ2002.project_exceptions.order;

public class InvalidSetMembership extends Exception{
    public InvalidSetMembership() {
        super();
    }

    public InvalidSetMembership(String message) {
        super(message);
    }
}