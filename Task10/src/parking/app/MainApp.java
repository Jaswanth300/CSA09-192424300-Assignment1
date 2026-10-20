package parking.app;

import parking.model.Car;
import parking.model.ParkingSpot;
import parking.model.TwoWheeler;
import parking.model.Vehicle;
import parking.service.ParkingService;

/**
 * Application entry point demonstrating encapsulation, access control, and autoboxing pitfalls.
 */
public class MainApp {

    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("TASK 10: Enforce Encapsulation through Packages and Access Modifiers");
        System.out.println("Modular Architecture, Validated Setters, and Autoboxing Pitfalls");
        System.out.println("======================================================================");

        // 1. Modular Package Interaction
        System.out.println("\n[1] Modular Package Interaction (parking.model & parking.service):");
        ParkingService service = new ParkingService(new int[]{2, 2});
        Vehicle car1 = new Car("TN01AA1111");
        Vehicle bike1 = new TwoWheeler("TN02BB2222");

        ParkingSpot spot1 = service.allocateSpot(car1);
        ParkingSpot spot2 = service.allocateSpot(bike1);

        System.out.println("Allocated car1  : " + spot1);
        System.out.println("Allocated bike1 : " + spot2);

        // 2. Encapsulation & Defensive Input Validation
        System.out.println("\n[2] Defensive Input Validation:");
        try {
            System.out.println("Attempting fee calculation with negative duration (-15 mins)...");
            service.calculateFee(car1, -15);
        } catch (IllegalArgumentException e) {
            System.out.println("  -> Successfully intercepted and rejected: " + e.getMessage());
        }

        try {
            System.out.println("Attempting to create vehicle with blank license plate...");
            new Car("   ");
        } catch (IllegalArgumentException e) {
            System.out.println("  -> Successfully intercepted and rejected: " + e.getMessage());
        }

        // 3. Wrapper Classes and Autoboxing Pitfalls (Integer Cache Trap)
        System.out.println("\n[3] Autoboxing & Wrapper Class Comparison Trap (== vs equals):");
        Integer cachedA = 100;
        Integer cachedB = 100;
        System.out.println("Cached Integers (100 == 100)      : " + (cachedA == cachedB) + " (True due to IntegerCache [-128, 127])");

        Integer nonCachedA = 200;
        Integer nonCachedB = 200;
        System.out.println("Non-Cached Integers (200 == 200)  : " + (nonCachedA == nonCachedB) + " (FALSE! Identity comparison on distinct Heap objects)");
        System.out.println("Correct Comparison (200.equals(200)): " + (nonCachedA.equals(nonCachedB)) + " (TRUE via value equality)");

        // 4. Alternative Concept: Private Validating Fields vs Public Mutable Fields
        System.out.println("\n[4] Alternative Concept & Complexity Analysis:");
        System.out.println("Approach A (Encapsulated fields + validated mutators):");
        System.out.println("  - Validation cost: O(1) checks executed once per mutation inside class boundary.");
        System.out.println("  - Maintainability: Centralized; impossible for external code to corrupt internal state.");

        System.out.println("\nApproach B (Public fields with client-side validation):");
        System.out.println("  - Fragility: Validation must be duplicated at every client invocation site.");
        System.out.println("  - Risk: O(M) duplicate checks across M calling sites; missing even one creates corrupted data.");
        System.out.println("======================================================================");
    }
}
