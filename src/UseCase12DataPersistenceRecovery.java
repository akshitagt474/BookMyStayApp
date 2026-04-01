import java.io.*;
import java.util.*;

class Reservation implements Serializable {
    private String reservationId;
    private String roomType;

    public Reservation(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return reservationId + " | " + roomType;
    }
}

class SystemState implements Serializable {
    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

class PersistenceService {
    private static final String FILE_NAME = "system_state.ser";

    public void save(SystemState state) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(state);
            System.out.println("State saved successfully");
        } catch (IOException e) {
            System.out.println("Error saving state");
        }
    }

    public SystemState load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            System.out.println("State loaded successfully");
            return (SystemState) ois.readObject();
        } catch (Exception e) {
            System.out.println("No valid saved state found");
            return null;
        }
    }
}

class BookingSystem {
    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public BookingSystem() {
        inventory = new HashMap<>();
        bookings = new ArrayList<>();

        inventory.put("STANDARD", 2);
        inventory.put("DELUXE", 2);
        inventory.put("SUITE", 1);
    }

    public void addReservation(Reservation r) {
        String type = r.getRoomType();
        if (inventory.getOrDefault(type, 0) > 0) {
            bookings.add(r);
            inventory.put(type, inventory.get(type) - 1);
            System.out.println("Booked: " + r);
        } else {
            System.out.println("No rooms available for " + type);
        }
    }

    public void display() {
        System.out.println("Bookings:");
        for (Reservation r : bookings) {
            System.out.println(r);
        }

        System.out.println("Inventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + " -> " + inventory.get(key));
        }
    }

    public SystemState getState() {
        return new SystemState(inventory, bookings);
    }

    public void restore(SystemState state) {
        if (state != null) {
            this.inventory = state.inventory;
            this.bookings = state.bookings;
        }
    }
}

public class UseCase12DataPersistenceRecovery {
    public static void main(String[] args) {

        PersistenceService persistence = new PersistenceService();
        BookingSystem system = new BookingSystem();

        SystemState loadedState = persistence.load();
        if (loadedState != null) {
            system.restore(loadedState);
        }

        system.addReservation(new Reservation("RES401", "DELUXE"));
        system.addReservation(new Reservation("RES402", "SUITE"));

        System.out.println();
        system.display();

        persistence.save(system.getState());
    }
}