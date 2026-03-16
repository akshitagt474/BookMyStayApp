import java.util.HashMap;
import java.util.Map;

class RoomInventory {

    private final Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    private void initializeInventory() {
        roomAvailability.put("SingleRoom", 10);
        roomAvailability.put("DoubleRoom", 5);
        roomAvailability.put("SuiteRoom", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}

public class UseCase3BookMyStayApp {
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        System.out.println("Initial Room Availability:");

        for (Map.Entry<String, Integer> entry : inventory.getRoomAvailability().entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        inventory.updateAvailability("SingleRoom", 8);

        System.out.println("\nUpdated Room Availability:");

        for (Map.Entry<String, Integer> entry : inventory.getRoomAvailability().entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}
