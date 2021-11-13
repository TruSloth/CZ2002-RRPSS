package com.CZ2002.project_exceptions;

public class InvalidReservationException extends Exception {
    public InvalidReservationException() {
        super();
    }

    public InvalidReservationException(String message) {
        super(message);
    }
}