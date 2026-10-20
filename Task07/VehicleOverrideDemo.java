import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * SIMATS Java Programming Assignment 1 - Campus Smart-Parking System
 * TASK 7: Override and Specialise Behaviour
 * Bloom's Level: K4 - Analyse | Marks: 4
 *
 * Student: BALA NAGA JASWANTH (Reg No: 192424300C)
 */
public class VehicleOverrideDemo {

    static class Vehicle {
        // final field: Registration number is immutable once assigned
        private final String regNo;

        public Vehicle(String regNo) {
            this.regNo = Objects.requireNonNull(regNo, "Registration number cannot be null");
        }

        // final getter prevents subclasses from breaking encapsulation
        public final String getRegNo() { return regNo; }

        public double ratePerHour() {
            return 20.0;
        }

        public double calculateFee(int minutes) {
            int billableHours = (minutes + 59) / 60;
            return billableHours * ratePerHour();
        }

        // Overriding java.lang.Object methods based on domain identity (regNo)
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Vehicle other)) return false;
            return Objects.equals(this.regNo, other.regNo);
        }

        @Override
        public int hashCode() {
            return Objects.hash(regNo);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + " [Plate=" + regNo + "]";
        }
    }

    static class TwoWheeler extends Vehicle {
        public TwoWheeler(String regNo) { super(regNo); }

        @Override
        public double ratePerHour() { return 10.0; }
    }

    static class Car extends Vehicle {
        public Car(String regNo) { super(regNo); }

        @Override
        public double ratePerHour() { return 30.0; }
    }

    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("TASK 7: Override and Specialise Behaviour");
        System.out.println("Method Overriding, Object Contracts, and OCP Evaluation");
        System.out.println("======================================================================");

        // 1. Polymorphic Fee Calculation Overriding
        Vehicle vBike = new TwoWheeler("TN01A1000");
        Vehicle vCar = new Car("TN02B2000");

        System.out.println("\n[1] Subclass Method Overriding:");
        System.out.println(vBike + " (130 mins) Fee: Rs " + vBike.calculateFee(130));
        System.out.println(vCar  + " (130 mins) Fee: Rs " + vCar.calculateFee(130));

        // 2. Overridden equals() and hashCode() Contract Demonstration
        System.out.println("\n[2] Object Identity & Equality Contract:");
        Vehicle car1 = new Car("TN09Z9999");
        Vehicle car2 = new Car("TN09Z9999");
        Vehicle bikeSamePlate = new TwoWheeler("TN09Z9999");

        System.out.println("car1 equals car2 (same plate, same class)       : " + car1.equals(car2));
        System.out.println("car1.hashCode() == car2.hashCode()             : " + (car1.hashCode() == car2.hashCode()));
        System.out.println("car1 equals bikeSamePlate (same plate via base): " + car1.equals(bikeSamePlate));

        // Verification in Hash-based collection
        Set<Vehicle> parkedSet = new HashSet<>();
        parkedSet.add(car1);
        boolean duplicateAdded = parkedSet.add(car2);
        System.out.println("HashSet deduplication test (prevent duplicate registration): " + (!duplicateAdded ? "SUCCESS (Deduplicated)" : "FAIL"));
        System.out.println("Active Vehicles in Set: " + parkedSet.size());

        // 3. Alternative Concept Analysis: Polymorphism vs Type Switch
        System.out.println("\n[3] Alternative Concept & Complexity Analysis:");
        System.out.println("Approach A (Polymorphic Method Overriding):");
        System.out.println("  - Dynamic dispatch cost: O(1) constant vtable jump.");
        System.out.println("  - Adding a new vehicle type: Open-Closed Principle (OCP) compliant; requires 0 edits to existing classes.");

        System.out.println("\nApproach B (Centralized calculateFee with switch on type enum/string):");
        System.out.println("  - Dispatch cost: O(k) sequential/table checks for k vehicle categories.");
        System.out.println("  - Adding a new type: Violates OCP; requires modifying the centralized switch in every class that handles types.");
        System.out.println("======================================================================");
    }
}
