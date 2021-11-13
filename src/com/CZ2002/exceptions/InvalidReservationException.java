package com.CZ2002.exceptions;
/**
 * The {@code InvalidReservationException} is thrown to indicate that a reservation does not exist or cannot be added
 */
public class InvalidReservationException extends Exception {
    public InvalidReservationException() {
        super();
    }

    public InvalidReservationException(String message) {
        super(message);
    }
}