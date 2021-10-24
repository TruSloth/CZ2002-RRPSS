package Exceptions;

public class InvalidReservationException extends IllegalArgumentException {
    public InvalidReservationException() {
        super();
    }

    public InvalidReservationException(String message) {
        super(message);
    }
}
