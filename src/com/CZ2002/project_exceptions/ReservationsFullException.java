package Exceptions;

public class ReservationsFullException extends Exception {
    public ReservationsFullException() {
        super("Reservations are full for this time.");
    }

    public ReservationsFullException(String message) {
        super(message);
    }
}