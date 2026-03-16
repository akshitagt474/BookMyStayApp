abstract class Room{
    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Room Details:");
        System.out.println("Number of Beds: " + numberOfBeds);
        System.out.println("Square Feet: " + squareFeet);
        System.out.println("Price per Night: $" + pricePerNight);
    }

}

class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 200, 1500.00);
    }
}

class DoubleRoom extends Room {

    /**
     * Initializes a DoubleRoom with
     * predefined attributes.
     */
    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
}

class SuiteRoom extends Room {

    /**
     * Initializes a SuiteRoom with
     * predefined attributes.
     */
    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
}

public class UseCase2BookMyStayApp {

    /**
     * Application entry point.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {

        SingleRoom singleRoom = new SingleRoom();
        DoubleRoom doubleRoom = new DoubleRoom();
        SuiteRoom suiteRoom = new SuiteRoom();

        System.out.println("Single Room Details:");
        singleRoom.displayRoomDetails();

        System.out.println("Double Room Details:");
        doubleRoom.displayRoomDetails();

        System.out.println("Suite Room Details:");
        suiteRoom.displayRoomDetails();
    }
}