package com.CZ2002.exceptions;
/**
 * The {@code InvalidStaffException} is thrown to indicate that a staff does not exist
 */
public class InvalidStaffException extends Exception {
    public InvalidStaffException() {
        super();
    }

    public InvalidStaffException(String message) {
        super(message);
    }
}
