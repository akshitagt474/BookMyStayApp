import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private int nights;

    public Reservation(String reservationId, String guestName, String roomType, int nights) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNights() {
        return nights;
    }

    @Override
    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType + " | Nights: " + nights;
    }
}

class BookingValidator {

    private Set<String> validRoomTypes;
    private Map<String, Integer> inventory;

    public BookingValidator() {
        validRoomTypes = new HashSet<>(Arrays.asList("STANDARD", "DELUXE", "SUITE"));

        inventory = new HashMap<>();
        inventory.put("STANDARD", 2);
        inventory.put("DELUXE", 2);
        inventory.put("SUITE", 1);
    }

    public void validate(Reservation reservation) throws InvalidBookingException {
        if (!validRoomTypes.contains(reservation.getRoomType())) {
            throw new InvalidBookingException("Invalid room type selected");
        }

        if (reservation.getNights() <= 0) {
            throw new InvalidBookingException("Number of nights must be greater than 0");
        }

        int available = inventory.getOrDefault(reservation.getRoomType(), 0);

        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for selected type");
        }
    }

    public void updateInventory(Reservation reservation) {
        String type = reservation.getRoomType();
        inventory.put(type, inventory.get(type) - 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + " -> " + inventory.get(key));
        }
    }
}

public class UseCase9ErrorHandlingValidation{
    public static void main(String[] args) {

        BookingValidator validator = new BookingValidator();

        List<Reservation> requests = Arrays.asList(
                new Reservation("RES201", "Akshita", "DELUXE", 2),
                new Reservation("RES202", "Rahul", "SUITE", 1),
                new Reservation("RES203", "Sneha", "SUITE", 1),
                new Reservation("RES204", "John", "INVALID", 3),
                new Reservation("RES205", "Meera", "STANDARD", 0)
        );

        for (Reservation r : requests) {
            try {
                validator.validate(r);
                validator.updateInventory(r);
                System.out.println("Booking successful: " + r);
            } catch (InvalidBookingException e) {
                System.out.println("Booking failed for " + r.getReservationId() + ": " + e.getMessage());
            }
        }

        System.out.println();
        validator.displayInventory();
    }
}