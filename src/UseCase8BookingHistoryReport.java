import java.util.*;

class Reservation {
    private String reservationId;
    private String guestName;
    private double amount;

    public Reservation(String reservationId, String guestName, double amount) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.amount = amount;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return reservationId + " | " + guestName + " | ₹" + amount;
    }
}

class BookingHistory {
    private List<Reservation> reservations;

    public BookingHistory() {
        reservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }
}

class BookingReportService {

    public void showAllBookings(List<Reservation> reservations) {
        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    public double getTotalRevenue(List<Reservation> reservations) {
        double total = 0;

        for (Reservation r : reservations) {
            total += r.getAmount();
        }

        return total;
    }

    public void showSummary(List<Reservation> reservations) {
        System.out.println("Total Bookings: " + reservations.size());
        System.out.println("Total Revenue: ₹" + getTotalRevenue(reservations));
    }
}

public class UseCase8BookingHistoryReport{
    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        history.addReservation(new Reservation("RES101", "Akshita", 5000));
        history.addReservation(new Reservation("RES102", "Rahul", 7000));
        history.addReservation(new Reservation("RES103", "Sneha", 6500));

        System.out.println("Booking History:");
        reportService.showAllBookings(history.getAllReservations());

        System.out.println("\nSummary Report:");
        reportService.showSummary(history.getAllReservations());
    }
}