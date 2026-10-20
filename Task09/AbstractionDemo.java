/**
 * SIMATS Java Programming Assignment 1 - Campus Smart-Parking System
 * TASK 9: Introduce Abstraction
 * Bloom's Level: K4 - Analyse | Marks: 4
 *
 * Student: BALA NAGA JASWANTH (Reg No: 192424300C)
 */
public class AbstractionDemo {

    // Capability Interface 1: Chargeable
    interface Chargeable {
        double chargingFee(int kWh);

        // Default interface method (Java 8+) for standardized power tariff computation
        default double getEmergencySurcharge() {
            return 25.0; // Flat emergency grid connection fee
        }

        // Static interface method
        static boolean isValidChargingDuration(int minutes) {
            return minutes > 0 && minutes <= 480; // Up to 8 hours
        }
    }

    // Capability Interface 2: Payable (Demonstrating multiple interface implementation)
    interface Payable {
        double computeFinalBill(double baseParkingFee);
    }

    // Abstract Superclass: Holds state (regNo) and contract (ratePerHour)
    abstract static class Vehicle {
        private final String regNo;

        protected Vehicle(String regNo) {
            this.regNo = regNo;
        }

        public final String getRegNo() { return regNo; }

        // Abstract method: Enforces concrete rate definition in subclasses
        public abstract double ratePerHour();

        // Template method: Encapsulates standard billing logic across all vehicles
        public double calculateFee(int minutes) {
            int hours = (minutes + 59) / 60;
            return hours * ratePerHour();
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + " [Plate=" + regNo + "]";
        }
    }

    // Multi-behavior concrete class: inherits Vehicle state, implements Chargeable and Payable
    static class ElectricSedan extends Vehicle implements Chargeable, Payable {
        public ElectricSedan(String regNo) {
            super(regNo);
        }

        @Override
        public double ratePerHour() {
            return 30.0;
        }

        @Override
        public double chargingFee(int kWh) {
            return kWh * 12.0;
        }

        @Override
        public double computeFinalBill(double baseParkingFee) {
            // Apply standard campus GST / service cess
            return baseParkingFee * 1.05;
        }
    }

    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("TASK 9: Introduce Abstraction");
        System.out.println("Abstract Classes vs Interfaces, Multiple Inheritance of Type, & Defaults");
        System.out.println("======================================================================");

        // 1. Multiple Behaviors via Interfaces
        ElectricSedan ev = new ElectricSedan("TN05EV5555");
        double baseFee = ev.calculateFee(120); // 2 hours = Rs 60
        double charge = ev.chargingFee(15);    // 15 kWh = Rs 180
        double subtotal = baseFee + charge;
        double finalBill = ev.computeFinalBill(subtotal);

        System.out.println("\n[1] Multi-Capability Object Execution:");
        System.out.println("Vehicle                : " + ev);
        System.out.println("Base Parking (120 mins): Rs " + baseFee);
        System.out.println("EV Charging (15 kWh)   : Rs " + charge);
        System.out.println("Emergency Surcharge    : Rs " + ev.getEmergencySurcharge() + " (via default interface method)");
        System.out.println("Final Bill (incl Tax)  : Rs " + String.format("%.2f", finalBill));
        System.out.println("Valid Charging Window  : " + Chargeable.isValidChargingDuration(120) + " (via static interface method)");

        // 2. Alternative Concept Analysis: Vehicle as Interface vs Abstract Class
        System.out.println("\n[2] Alternative Concept & Architectural Trade-offs:");
        System.out.println("Approach A (Abstract Class Vehicle + Interface Chargeable):");
        System.out.println("  - Vehicle holds private final state (regNo) and enforces constructor invocation.");
        System.out.println("  - Chargeable is a pure behavioral contract implemented only by vehicles requiring power.");

        System.out.println("\nApproach B (Vehicle as Interface with default methods + Chargeable as Abstract Class):");
        System.out.println("  - Critical Limitation: Interfaces CANNOT declare non-static instance fields (no regNo storage).");
        System.out.println("  - Code Duplication: Every single subclass would have to re-declare regNo and its constructor.");
        System.out.println("  - Single Inheritance Lockout: If Chargeable were an abstract class, a vehicle could inherit only one base.");
        System.out.println("======================================================================");
    }
}
