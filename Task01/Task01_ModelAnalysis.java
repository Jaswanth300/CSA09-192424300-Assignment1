/**
 * SIMATS Java Programming Assignment 1 - Campus Smart-Parking System
 * TASK 1: Understand the Problem and Identify the Objects
 * Bloom's Level: K4 - Analyse | Marks: 4
 *
 * Student: BALA NAGA JASWANTH (Reg No: 192424300C)
 */
public class Task01_ModelAnalysis {

    // Domain candidate representation: Vehicle object
    static class Vehicle {
        private final String regNo;
        private final String type;
        private final long entryTimeMillis;

        public Vehicle(String regNo, String type, long entryTimeMillis) {
            this.regNo = regNo;
            this.type = type;
            this.entryTimeMillis = entryTimeMillis;
        }

        public String getRegNo() { return regNo; }
        public String getType() { return type; }
        public long getEntryTimeMillis() { return entryTimeMillis; }

        @Override
        public String toString() {
            return "Vehicle[regNo=" + regNo + ", type=" + type + ", entryTime=" + entryTimeMillis + "]";
        }
    }

    // Candidate class: ParkingSpot
    static class ParkingSpot {
        private final int floor;
        private final int slot;
        private Vehicle vehicle;

        public ParkingSpot(int floor, int slot) {
            this.floor = floor;
            this.slot = slot;
            this.vehicle = null;
        }

        public boolean isAvailable() { return vehicle == null; }

        public boolean park(Vehicle v) {
            if (!isAvailable()) return false;
            this.vehicle = v;
            return true;
        }

        public Vehicle vacate() {
            Vehicle temp = this.vehicle;
            this.vehicle = null;
            return temp;
        }

        @Override
        public String toString() {
            return "Floor " + floor + " Slot " + slot + " -> " + (isAvailable() ? "FREE" : vehicle.getRegNo());
        }
    }

    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("TASK 1: Understand the Problem and Identify the Objects");
        System.out.println("Domain Model & Business Rules Validation");
        System.out.println("======================================================================");

        // 1. Demonstrate Candidate Classes & Single Responsibility
        System.out.println("\n[1] Object-Oriented Modeling Demonstration:");
        ParkingSpot spot0 = new ParkingSpot(0, 0);
        Vehicle v1 = new Vehicle("TN01AB1111", "Car", System.currentTimeMillis());
        System.out.println("Initial State: " + spot0);
        
        boolean parkSuccess = spot0.park(v1);
        System.out.println("Allocating TN01AB1111 to Spot (0,0): " + parkSuccess);
        System.out.println("State After Parking: " + spot0);

        // Business Rule 1: A slot cannot hold two vehicles
        Vehicle v2 = new Vehicle("TN02CD2222", "TwoWheeler", System.currentTimeMillis());
        boolean doubleParkAttempt = spot0.park(v2);
        System.out.println("Business Rule Test (Reject double-booking): " + (!doubleParkAttempt ? "PASS (Denied)" : "FAIL"));

        // Vacating spot
        Vehicle vacated = spot0.vacate();
        System.out.println("Vacated: " + vacated.getRegNo() + " | State: " + spot0);

        // 2. Alternative Concept Analysis: Object Model vs. Parallel Arrays
        System.out.println("\n[2] Alternative Concept & Complexity Comparison:");
        System.out.println("Scenario: Managing N vehicles");
        System.out.println("Approach A (Object-Oriented Design): Vehicle[] vehicles");
        System.out.println("  - Linear Search by regNo: O(N) comparisons");
        System.out.println("  - Adding attribute (e.g., color): Modify Vehicle class only (O(1) structural change)");
        System.out.println("  - Memory overhead: Single object header per vehicle with contiguous reference array");

        System.out.println("\nApproach B (Parallel Arrays): String[] regNo, String[] type, long[] entryTime");
        System.out.println("  - Linear Search by regNo: O(N) comparisons");
        System.out.println("  - Adding attribute: Requires creating and maintaining a separate parallel array across all functions");
        System.out.println("  - Integrity risk: High (de-synchronization risk on sorting/deletion)");
        System.out.println("======================================================================");
    }
}
