/**
 * SIMATS Java Programming Assignment 1 - Campus Smart-Parking System
 * TASK 6: Design the Inheritance Hierarchy
 * Bloom's Level: K4 - Analyse | Marks: 4
 *
 * Student: BALA NAGA JASWANTH (Reg No: 192424300C)
 */
public class VehicleHierarchyDemo {

    // Base Superclass
    static class Vehicle {
        private final String regNo;

        public Vehicle(String regNo) {
            System.out.println("    [Constructor Trace 1] Vehicle constructor called for regNo: " + regNo);
            this.regNo = regNo;
        }

        public String getRegNo() { return regNo; }

        public double ratePerHour() {
            return 20.0; // Base default rate
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "[regNo=" + regNo + "]";
        }
    }

    // Subclass 1: TwoWheeler (Single inheritance from Vehicle)
    static class TwoWheeler extends Vehicle {
        public TwoWheeler(String regNo) {
            super(regNo);
            System.out.println("    [Constructor Trace 2] TwoWheeler constructor executed.");
        }

        @Override
        public double ratePerHour() { return 10.0; }
    }

    // Subclass 2: Car (Hierarchical inheritance branch from Vehicle)
    static class Car extends Vehicle {
        public Car(String regNo) {
            super(regNo);
            System.out.println("    [Constructor Trace 2] Car constructor executed.");
        }

        @Override
        public double ratePerHour() { return 30.0; }
    }

    // Subclass 3: ElectricCar (Multilevel inheritance: Vehicle -> Car -> ElectricCar)
    static class ElectricCar extends Car {
        private final double batteryCapacityKWh;

        public ElectricCar(String regNo, double batteryCapacityKWh) {
            super(regNo);
            System.out.println("    [Constructor Trace 3] ElectricCar constructor executed with battery: " + batteryCapacityKWh + " kWh.");
            this.batteryCapacityKWh = batteryCapacityKWh;
        }

        public double getBatteryCapacityKWh() { return batteryCapacityKWh; }
    }

    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("TASK 6: Design the Inheritance Hierarchy");
        System.out.println("Hierarchical & Multilevel Inheritance and Constructor Tracing");
        System.out.println("======================================================================");

        // 1. Traced constructor execution order
        System.out.println("\n[1] Constructor Execution Order Trace for new ElectricCar(\"TN03EV9999\", 40.0):");
        ElectricCar ev = new ElectricCar("TN03EV9999", 40.0);
        System.out.println("Resulting Instance: " + ev);
        System.out.println("Hourly Rate       : Rs " + ev.ratePerHour());

        // 2. Demonstration of Hierarchical Subclasses
        System.out.println("\n[2] Hierarchical Subclass Instances:");
        TwoWheeler bike = new TwoWheeler("TN01BK1234");
        Car standardCar = new Car("TN02CR5678");
        System.out.println("Bike Rate: Rs " + bike.ratePerHour() + " | Car Rate: Rs " + standardCar.ratePerHour());

        // 3. Alternative Concept Analysis: Inheritance vs Composition
        System.out.println("\n[3] Alternative Concept & Complexity Analysis:");
        System.out.println("Approach A (Inheritance - 'Car is-a Vehicle'):");
        System.out.println("  - New vehicle type added by creating new subclass.");
        System.out.println("  - Method dispatch is direct via JVM vtable (O(1)).");
        System.out.println("  - Space per object: Lean; only subclass-specific fields added to object layout.");

        System.out.println("\nApproach B (Composition - 'Vehicle has-a VehicleType'):");
        System.out.println("  - A single generic Vehicle class holds a reference to a VehicleType strategy/enum.");
        System.out.println("  - Adding new type requires only configuring a new VehicleType instance (no class explosion).");
        System.out.println("  - Space per object: Extra pointer indirection for the composite component.");
        System.out.println("======================================================================");
    }
}
