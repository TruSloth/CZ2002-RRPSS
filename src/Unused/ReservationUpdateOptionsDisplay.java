package Unused;

public class ReservationUpdateOptionsDisplay extends ConsoleDisplay {
    public ReservationUpdateOptionsDisplay() {
        super.options = new String[]{
            "Name",
            "Contact",
            "Pax",
            "Table No.",
            "Reservation Period",
            "Back"
        };

        super.title = "Reservations";
    }
}
