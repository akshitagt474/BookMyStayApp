import java.util.*;

class BookingRequest {
    private String requestId;
    private String roomType;

    public BookingRequest(String requestId, String roomType) {
        this.requestId = requestId;
        this.roomType = roomType;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getRoomType() {
        return roomType;
    }
}

class BookingSystem {
    private Map<String, Integer> inventory;
    private Queue<BookingRequest> queue;

    public BookingSystem() {
        inventory = new HashMap<>();
        queue = new LinkedList<>();

        inventory.put("STANDARD", 2);
        inventory.put("DELUXE", 2);
        inventory.put("SUITE", 1);
    }

    public synchronized void addRequest(BookingRequest request) {
        queue.add(request);
    }

    public synchronized BookingRequest getRequest() {
        return queue.poll();
    }

    public synchronized boolean allocateRoom(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public void displayInventory() {
        System.out.println("Final Inventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + " -> " + inventory.get(key));
        }
    }
}

class BookingProcessor implements Runnable {
    private BookingSystem system;

    public BookingProcessor(BookingSystem system) {
        this.system = system;
    }

    @Override
    public void run() {
        while (true) {
            BookingRequest request;

            synchronized (system) {
                request = system.getRequest();
            }

            if (request == null) {
                break;
            }

            boolean success;

            synchronized (system) {
                success = system.allocateRoom(request.getRoomType());
            }

            if (success) {
                System.out.println(Thread.currentThread().getName() + " booked " + request.getRoomType() + " for " + request.getRequestId());
            } else {
                System.out.println(Thread.currentThread().getName() + " failed for " + request.getRequestId());
            }
        }
    }
}

public class UseCase11ConcurrentBookingSimulation {
    public static void main(String[] args) throws InterruptedException {

        BookingSystem system = new BookingSystem();

        system.addRequest(new BookingRequest("REQ1", "DELUXE"));
        system.addRequest(new BookingRequest("REQ2", "DELUXE"));
        system.addRequest(new BookingRequest("REQ3", "DELUXE"));
        system.addRequest(new BookingRequest("REQ4", "SUITE"));
        system.addRequest(new BookingRequest("REQ5", "SUITE"));
        system.addRequest(new BookingRequest("REQ6", "STANDARD"));
        system.addRequest(new BookingRequest("REQ7", "STANDARD"));
        system.addRequest(new BookingRequest("REQ8", "STANDARD"));

        Thread t1 = new Thread(new BookingProcessor(system), "Thread-1");
        Thread t2 = new Thread(new BookingProcessor(system), "Thread-2");
        Thread t3 = new Thread(new BookingProcessor(system), "Thread-3");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println();
        system.displayInventory();
    }
}