package Exceptions;

public class InvalidReservationException extends Exception {
    public InvalidReservationException() {
        super();
    }

    public InvalidReservationException(String message) {
        super(message);
    }
}
