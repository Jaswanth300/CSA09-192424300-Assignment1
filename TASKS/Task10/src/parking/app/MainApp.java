package parking.app;

import parking.model.Car;
import parking.model.ParkingSpot;
import parking.model.TwoWheeler;
import parking.model.Vehicle;
import parking.service.ParkingService;

/**
 * SIMATS Java Programming Assignment 1 - Task 10
 * Packages, Access Modifiers, and Encapsulation
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
public class MainApp {

    public static void main(String[] args) {
        ParkingService service = new ParkingService(2, 2);
        Vehicle car = new Car("TN01AA1111");
        Vehicle bike = new TwoWheeler("TN02BB2222");

        ParkingSpot spot1 = service.allocateSpot(car);
        ParkingSpot spot2 = service.allocateSpot(bike);

        System.out.println("Car allocated to Floor " + spot1.getFloor() + ", Slot " + spot1.getSlot());
        System.out.println("Bike allocated to Floor " + spot2.getFloor() + ", Slot " + spot2.getSlot());

        // Encapsulation validation
        try {
            service.calculateFee(car, -10);
        } catch (IllegalArgumentException e) {
            System.out.println("Validation caught negative duration: " + e.getMessage());
        }

        try {
            new Car("");
        } catch (IllegalArgumentException e) {
            System.out.println("Validation caught blank regNo: " + e.getMessage());
        }

        // Wrapper class autoboxing & Integer cache comparison
        Integer a = 100, b = 100;
        Integer c = 200, d = 200;
        System.out.println("Cached Integers (100 == 100): " + (a == b));
        System.out.println("Non-cached Integers (200 == 200): " + (c == d) + " (Reference comparison)");
        System.out.println("Non-cached Integers (200.equals(200)): " + c.equals(d) + " (Value comparison)");
    }
}
