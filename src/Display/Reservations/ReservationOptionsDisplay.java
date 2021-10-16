package Display.Reservations;

import Display.ConsoleDisplay;

public class ReservationOptionsDisplay extends ConsoleDisplay {
    public ReservationOptionsDisplay() {
        super.options = new String[]{
            "Create Reservation Booking",
            "Check Reservation Booking",
            "Remove Reservation Booking",
            "Back"
        };

        super.title = "Reservations";
    }
}
