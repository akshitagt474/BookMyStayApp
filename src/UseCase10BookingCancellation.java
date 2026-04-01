import java.util.*;

class Reservation {
    private String reservationId;
    private String roomType;
    private String roomId;
    private boolean isCancelled;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isCancelled = false;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void cancel() {
        isCancelled = true;
    }

    @Override
    public String toString() {
        return reservationId + " | " + roomType + " | Room: " + roomId + " | Cancelled: " + isCancelled;
    }
}

class BookingSystem {
    private Map<String, Reservation> reservations;
    private Map<String, Integer> inventory;
    private Map<String, Stack<String>> allocatedRooms;

    public BookingSystem() {
        reservations = new HashMap<>();
        inventory = new HashMap<>();
        allocatedRooms = new HashMap<>();

        inventory.put("STANDARD", 2);
        inventory.put("DELUXE", 2);
        inventory.put("SUITE", 1);

        allocatedRooms.put("STANDARD", new Stack<>());
        allocatedRooms.put("DELUXE", new Stack<>());
        allocatedRooms.put("SUITE", new Stack<>());
    }

    public void addReservation(Reservation reservation) {
        reservations.put(reservation.getReservationId(), reservation);
        inventory.put(reservation.getRoomType(), inventory.get(reservation.getRoomType()) - 1);
        allocatedRooms.get(reservation.getRoomType()).push(reservation.getRoomId());
    }

    public void cancelReservation(String reservationId) {
        if (!reservations.containsKey(reservationId)) {
            System.out.println("Cancellation failed: Reservation not found");
            return;
        }

        Reservation r = reservations.get(reservationId);

        if (r.isCancelled()) {
            System.out.println("Cancellation failed: Already cancelled");
            return;
        }

        String roomType = r.getRoomType();

        Stack<String> stack = allocatedRooms.get(roomType);

        if (!stack.isEmpty()) {
            stack.pop();
        }

        inventory.put(roomType, inventory.get(roomType) + 1);

        r.cancel();

        System.out.println("Cancellation successful: " + reservationId);
    }

    public void displayInventory() {
        System.out.println("Inventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + " -> " + inventory.get(key));
        }
    }

    public void displayReservations() {
        for (Reservation r : reservations.values()) {
            System.out.println(r);
        }
    }
}

public class UseCase10BookingCancellation {
    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();

        Reservation r1 = new Reservation("RES301", "DELUXE", "D1");
        Reservation r2 = new Reservation("RES302", "SUITE", "S1");

        system.addReservation(r1);
        system.addReservation(r2);

        system.displayInventory();
        System.out.println();

        system.cancelReservation("RES302");
        system.cancelReservation("RES999");
        system.cancelReservation("RES302");

        System.out.println();
        system.displayInventory();

        System.out.println();
        system.displayReservations();
    }
}