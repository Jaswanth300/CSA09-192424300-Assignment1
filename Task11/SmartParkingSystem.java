import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * SIMATS Java Programming Assignment 1 - Campus Smart-Parking System
 * TASK 11: Implement, Test and Defend the Design
 * Bloom's Level: K5 - Evaluate | Marks: 5
 *
 * Student: BALA NAGA JASWANTH (Reg No: 192424300C)
 */
public class SmartParkingSystem {

    // ==========================================
    // Core Domain Hierarchy
    // ==========================================

    interface Chargeable {
        double chargingFee(int kWh);
    }

    abstract static class Vehicle {
        private final String regNo;
        private final boolean hasMonthlyPass;

        protected Vehicle(String regNo, boolean hasMonthlyPass) {
            if (regNo == null || regNo.trim().isEmpty()) {
                throw new IllegalArgumentException("Registration plate cannot be blank");
            }
            this.regNo = regNo.trim().toUpperCase();
            this.hasMonthlyPass = hasMonthlyPass;
        }

        public final String getRegNo() { return regNo; }
        public final boolean hasMonthlyPass() { return hasMonthlyPass; }

        public abstract double ratePerHour();

        /** Ceiling integer billing: every started hour is charged in full */
        public double calculateParkingFee(int minutes) {
            if (hasMonthlyPass) return 0.0; // Pass holders park free
            int hours = (minutes + 59) / 60; // For minutes == 0, hours = 0 -> Fee = 0.0
            return hours * ratePerHour();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Vehicle other)) return false;
            return Objects.equals(regNo, other.regNo);
        }

        @Override
        public int hashCode() {
            return Objects.hash(regNo);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "[" + regNo + (hasMonthlyPass ? ", PASS" : ", VISITOR") + "]";
        }
    }

    static class TwoWheeler extends Vehicle {
        public TwoWheeler(String regNo, boolean pass) { super(regNo, pass); }
        @Override public double ratePerHour() { return 10.0; }
    }

    static class Car extends Vehicle {
        public Car(String regNo, boolean pass) { super(regNo, pass); }
        @Override public double ratePerHour() { return 30.0; }
    }

    static class ElectricCar extends Car implements Chargeable {
        public ElectricCar(String regNo, boolean pass) { super(regNo, pass); }
        @Override
        public double chargingFee(int kWh) {
            return kWh * 12.0;
        }
    }

    // ==========================================
    // Parking Slot & Ticket Entities
    // ==========================================

    static class ParkingSpot {
        private final int floor;
        private final int slot;
        private Vehicle currentVehicle;

        public ParkingSpot(int floor, int slot) {
            this.floor = floor;
            this.slot = slot;
            this.currentVehicle = null;
        }

        public int getFloor() { return floor; }
        public int getSlot() { return slot; }
        public boolean isAvailable() { return currentVehicle == null; }

        public boolean park(Vehicle v) {
            if (!isAvailable()) return false;
            this.currentVehicle = v;
            return true;
        }

        public Vehicle vacate() {
            Vehicle v = this.currentVehicle;
            this.currentVehicle = null;
            return v;
        }

        @Override
        public String toString() {
            return "Spot(" + floor + "," + slot + ")=" + (isAvailable() ? "FREE" : currentVehicle.getRegNo());
        }
    }

    static class ParkingTicket {
        private static int counter = 0;
        private final String ticketId;
        private final Vehicle vehicle;
        private final int floor;
        private final int slot;
        private final long entryTimeMillis;

        public ParkingTicket(Vehicle vehicle, int floor, int slot) {
            this.ticketId = String.format("TKT-%04d", ++counter);
            this.vehicle = vehicle;
            this.floor = floor;
            this.slot = slot;
            this.entryTimeMillis = System.currentTimeMillis();
        }

        public String getTicketId() { return ticketId; }
        public Vehicle getVehicle() { return vehicle; }
        public int getFloor() { return floor; }
        public int getSlot() { return slot; }
    }

    // ==========================================
    // Facility Controller
    // ==========================================

    static class ParkingFacility {
        private final ParkingSpot[][] slots;
        // Tracking active tickets by registration number to prevent duplicate entry
        private final Map<String, ParkingTicket> activeTickets = new HashMap<>();
        private double totalDailyRevenue = 0.0;

        public ParkingFacility(int[] slotsPerFloor) {
            slots = new ParkingSpot[slotsPerFloor.length][];
            for (int f = 0; f < slotsPerFloor.length; f++) {
                slots[f] = new ParkingSpot[slotsPerFloor[f]];
                for (int s = 0; s < slotsPerFloor[f]; s++) {
                    slots[f][s] = new ParkingSpot(f, s);
                }
            }
        }

        public ParkingTicket checkIn(Vehicle vehicle) {
            // Guard against duplicate active registrations
            if (activeTickets.containsKey(vehicle.getRegNo())) {
                throw new IllegalStateException("Duplicate entry rejected: Vehicle " + vehicle.getRegNo() + " is already inside!");
            }

            // Find first available spot
            for (ParkingSpot[] floor : slots) {
                for (ParkingSpot spot : floor) {
                    if (spot.isAvailable()) {
                        spot.park(vehicle);
                        ParkingTicket ticket = new ParkingTicket(vehicle, spot.getFloor(), spot.getSlot());
                        activeTickets.put(vehicle.getRegNo(), ticket);
                        return ticket;
                    }
                }
            }
            return null; // Facility is full
        }

        public double checkOut(String ticketId, int durationMinutes, int chargingKWh) {
            ParkingTicket foundTicket = null;
            for (ParkingTicket t : activeTickets.values()) {
                if (t.getTicketId().equals(ticketId)) {
                    foundTicket = t;
                    break;
                }
            }

            if (foundTicket == null) {
                throw new IllegalArgumentException("Invalid ticket ID: " + ticketId);
            }

            // Release spot
            ParkingSpot spot = slots[foundTicket.getFloor()][foundTicket.getSlot()];
            spot.vacate();
            activeTickets.remove(foundTicket.getVehicle().getRegNo());

            // Compute fees
            double parkingFee = foundTicket.getVehicle().calculateParkingFee(durationMinutes);
            double chargingFee = 0.0;
            if (foundTicket.getVehicle() instanceof Chargeable c && chargingKWh > 0) {
                chargingFee = c.chargingFee(chargingKWh);
            }
            double totalFee = parkingFee + chargingFee;
            totalDailyRevenue += totalFee;
            return totalFee;
        }

        public double getTotalDailyRevenue() { return totalDailyRevenue; }
        public int getActiveCount() { return activeTickets.size(); }
    }

    // ==========================================
    // Main Verification Test Driver
    // ==========================================

    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("TASK 11: Implement, Test and Defend the Design");
        System.out.println("Integrated Campus Smart-Parking System Verification Suite");
        System.out.println("======================================================================");

        // Facility with 2 floors (Floor 0: 2 slots, Floor 1: 1 slot -> Total capacity = 3)
        ParkingFacility facility = new ParkingFacility(new int[]{2, 1});

        // ----------------------------------------------------
        // TEST CATEGORY 1: Normal Operation
        // ----------------------------------------------------
        System.out.println("\n--- [TEST 1: Normal Operation] ---");
        Vehicle car1 = new Car("TN01AA1111", false); // Visitor
        Vehicle bike1 = new TwoWheeler("TN02BB2222", false); // Visitor
        Vehicle ev1 = new ElectricCar("TN03CC3333", false); // Visitor

        ParkingTicket t1 = facility.checkIn(car1);
        ParkingTicket t2 = facility.checkIn(bike1);
        System.out.println("Checked in car1 : " + car1.getRegNo() + " -> " + t1.getTicketId() + " at Spot(" + t1.getFloor() + "," + t1.getSlot() + ")");
        System.out.println("Checked in bike1: " + bike1.getRegNo() + " -> " + t2.getTicketId() + " at Spot(" + t2.getFloor() + "," + t2.getSlot() + ")");

        // ----------------------------------------------------
        // TEST CATEGORY 2: Boundary Cases
        // ----------------------------------------------------
        System.out.println("\n--- [TEST 2: Boundary Cases] ---");
        // 2a: Last free slot allocation
        ParkingTicket t3 = facility.checkIn(ev1);
        System.out.println("Allocated LAST FREE slot to ev1: " + t3.getTicketId() + " at Spot(" + t3.getFloor() + "," + t3.getSlot() + ")");
        
        // Facility is now full (3/3)
        Vehicle overflowCar = new Car("TN99ZZ9999", false);
        ParkingTicket tOverflow = facility.checkIn(overflowCar);
        System.out.println("Capacity Exhaustion Check (Attempt check-in when full): " + (tOverflow == null ? "PASS (Returned null)" : "FAIL"));

        // Release t1 so we can test zero-minute parking
        double feeT1 = facility.checkOut(t1.getTicketId(), 120, 0); // 120 mins = 2 hrs * 30 = Rs 60
        System.out.println("Checked out car1 (" + t1.getTicketId() + ", 120 mins): Rs " + feeT1);

        // 2b: Zero-minute parking check (immediate turnaround)
        Vehicle zeroMinCar = new Car("TN05EE5555", false);
        ParkingTicket tZero = facility.checkIn(zeroMinCar);
        double feeZero = facility.checkOut(tZero.getTicketId(), 0, 0);
        System.out.println("Zero-Minute Parking Fee: Rs " + feeZero + " (Expected Rs 0.0 -> " + (feeZero == 0.0 ? "PASS" : "FAIL") + ")");

        // 2c: Stay exactly at slab boundary: 60 mins vs 61 mins
        Vehicle slabCar1 = new Car("TN06FF6666", false);
        ParkingTicket tSlab1 = facility.checkIn(slabCar1);
        double fee60 = facility.checkOut(tSlab1.getTicketId(), 60, 0);
        System.out.println("Slab Boundary 60 mins (Exact 1 hour): Rs " + fee60 + " (Expected Rs 30.0 -> " + (fee60 == 30.0 ? "PASS" : "FAIL") + ")");

        Vehicle slabCar2 = new Car("TN07GG7777", false);
        ParkingTicket tSlab2 = facility.checkIn(slabCar2);
        double fee61 = facility.checkOut(tSlab2.getTicketId(), 61, 0);
        System.out.println("Slab Boundary 61 mins (Starts 2nd hour): Rs " + fee61 + " (Expected Rs 60.0 -> " + (fee61 == 60.0 ? "PASS" : "FAIL") + ")");

        // ----------------------------------------------------
        // TEST CATEGORY 3: Invalid Cases
        // ----------------------------------------------------
        System.out.println("\n--- [TEST 3: Invalid Cases & Error Handling] ---");
        // 3a: Duplicate registration check
        try {
            System.out.println("Attempting to re-enter vehicle TN02BB2222 (currently parked inside)...");
            facility.checkIn(new TwoWheeler("TN02BB2222", false));
        } catch (IllegalStateException e) {
            System.out.println("  -> Successfully rejected duplicate: " + e.getMessage());
        }

        // 3b: Invalid / Non-existent ticket release
        try {
            System.out.println("Attempting to check out non-existent ticket 'TKT-9999'...");
            facility.checkOut("TKT-9999", 30, 0);
        } catch (IllegalArgumentException e) {
            System.out.println("  -> Successfully intercepted invalid ticket: " + e.getMessage());
        }

        // ----------------------------------------------------
        // TEST CATEGORY 4: Hand-Calculated Revenue Scenario
        // ----------------------------------------------------
        System.out.println("\n--- [TEST 4: Hand-Calculated Revenue Scenario Comparison] ---");
        System.out.println("Resetting facility for a clean benchmark day scenario:");
        ParkingFacility benchFacility = new ParkingFacility(new int[]{5, 5});

        // Scenario:
        // V1: Car (Visitor), 120 mins (2 hrs) -> 2 * 30 = Rs 60.0
        // V2: TwoWheeler (Visitor), 90 mins (ceil 2 hrs) -> 2 * 10 = Rs 20.0
        // V3: ElectricCar (Visitor), 60 mins (1 hr * 30 = 30) + 10 kWh (10 * 12 = 120) -> Rs 150.0
        // V4: Car (Student Monthly Pass), 180 mins -> Rs 0.0 (Free)
        // V5: Car (Visitor), 0 mins (immediate exit) -> Rs 0.0
        // Hand-Calculated Expected Total = 60 + 20 + 150 + 0 + 0 = Rs 230.0

        ParkingTicket bt1 = benchFacility.checkIn(new Car("KA01AA1111", false));
        ParkingTicket bt2 = benchFacility.checkIn(new TwoWheeler("KA02BB2222", false));
        ParkingTicket bt3 = benchFacility.checkIn(new ElectricCar("KA03CC3333", false));
        ParkingTicket bt4 = benchFacility.checkIn(new Car("KA04DD4444", true)); // Pass holder
        ParkingTicket bt5 = benchFacility.checkIn(new Car("KA05EE5555", false));

        double f1 = benchFacility.checkOut(bt1.getTicketId(), 120, 0);
        double f2 = benchFacility.checkOut(bt2.getTicketId(), 90, 0);
        double f3 = benchFacility.checkOut(bt3.getTicketId(), 60, 10);
        double f4 = benchFacility.checkOut(bt4.getTicketId(), 180, 0);
        double f5 = benchFacility.checkOut(bt5.getTicketId(), 0, 0);

        double actualTotal = benchFacility.getTotalDailyRevenue();
        double expectedTotal = 230.0;

        System.out.println("Itemized Settlement:");
        System.out.println("  1. Car (120 mins)        : Rs " + f1 + " (Expected: Rs 60.0)");
        System.out.println("  2. TwoWheeler (90 mins)  : Rs " + f2 + " (Expected: Rs 20.0)");
        System.out.println("  3. ElectricCar (60m+10k) : Rs " + f3 + " (Expected: Rs 150.0)");
        System.out.println("  4. Monthly Pass (180m)   : Rs " + f4 + " (Expected: Rs 0.0)");
        System.out.println("  5. Zero-Minute Stay (0m) : Rs " + f5 + " (Expected: Rs 0.0)");
        System.out.println("------------------------------------------------------");
        System.out.println("Hand-Calculated Revenue : Rs " + expectedTotal);
        System.out.println("Actual Program Output   : Rs " + actualTotal);
        System.out.println("Revenue Match Status    : " + (actualTotal == expectedTotal ? "100% MATCH (VERIFIED)" : "MISMATCH"));

        // ----------------------------------------------------
        // TEST CATEGORY 5: OOP Principles & Limitation Evaluation
        // ----------------------------------------------------
        System.out.println("\n--- [TEST 5: OOP Evaluation & Design Limitation] ---");
        System.out.println("1. Encapsulation: ParkingSpot and Vehicle fields are private with guarded access.");
        System.out.println("2. Inheritance: Hierarchical vehicle tree avoids code duplication for license plates.");
        System.out.println("3. Polymorphism: Fee calculation dynamically dispatched via ratePerHour().");
        System.out.println("4. Abstraction: Chargeable interface cleanly decouples EV power billing from vehicle types.");
        System.out.println("IDENTIFIED LIMITATION: Pricing rules are tightly coupled inside vehicle subclasses.");
        System.out.println("  If surge pricing, peak hours, or discounts are introduced, subclasses must be modified.");
        System.out.println("  This directly motivates the Day-4 Design Challenge in Task 12.");
        System.out.println("======================================================================");
    }
}
