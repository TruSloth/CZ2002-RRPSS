package Unused;

public class ReservationOptionsDisplay extends ConsoleDisplay {
    public ReservationOptionsDisplay() {
        super.options = new String[]{
            "Create Reservation Booking",
            "Check Reservation Booking",
            "Update Reservation Booking",
            "Remove Reservation Booking",
            "Back"
        };

        super.title = "Reservations";
    }
}