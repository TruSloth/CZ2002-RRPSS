package com.CZ2002.exceptions;
/**
 * The {@code InvalidSetMembershipException} is thrown to indicate that the input is invalid
 */
public class InvalidSetMembership extends Exception{
    public InvalidSetMembership() {
        super();
    }

    public InvalidSetMembership(String message) {
        super(message);
    }
}