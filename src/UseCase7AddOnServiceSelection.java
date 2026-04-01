import java.util.*;

class Service {
    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return name + " - ₹" + cost;
    }
}

class AddOnServiceManager {
    private Map<String, List<Service>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    public void addService(String reservationId, Service service) {
        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    public List<Service> getServices(String reservationId) {
        return reservationServices.getOrDefault(reservationId, new ArrayList<>());
    }

    public double calculateTotalCost(String reservationId) {
        double total = 0;
        List<Service> services = reservationServices.get(reservationId);

        if (services != null) {
            for (Service s : services) {
                total += s.getCost();
            }
        }

        return total;
    }

    public void displayServices(String reservationId) {
        List<Service> services = getServices(reservationId);

        if (services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("Add-on services for Reservation " + reservationId + ":");
        for (Service s : services) {
            System.out.println("- " + s);
        }
    }
}

public class UseCase7AddOnServiceSelection {
    public static void main(String[] args) {
        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId = "RES101";

        Service breakfast = new Service("Breakfast", 500);
        Service spa = new Service("Spa", 1500);
        Service airportPickup = new Service("Airport Pickup", 800);
        Service extraBed = new Service("Extra Bed", 700);

        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, spa);
        manager.addService(reservationId, airportPickup);
        manager.addService(reservationId, extraBed);

        manager.displayServices(reservationId);

        double totalCost = manager.calculateTotalCost(reservationId);
        System.out.println("\nTotal Add-on Cost: ₹" + totalCost);
    }
}

