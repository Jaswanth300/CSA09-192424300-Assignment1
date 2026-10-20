/**
 * SIMATS Java Programming Assignment 1 - Campus Smart-Parking System
 * TASK 4: Design Classes, Objects and Constructors
 * Bloom's Level: K4 - Analyse | Marks: 4
 *
 * Student: BALA NAGA JASWANTH (Reg No: 192424300C)
 */
public class ConstructorDemo {

    // Domain Class: ParkingTicket demonstrating default, parameterized, and copy constructors with chaining
    static class ParkingTicket {
        private final String ticketId;
        private final String vehicleRegNo;
        private final int floor;
        private final int slot;
        private final long entryTimeMillis;

        // 1. Full Parameterized Constructor (Primary constructor)
        public ParkingTicket(String ticketId, String vehicleRegNo, int floor, int slot, long entryTimeMillis) {
            this.ticketId = ticketId;
            this.vehicleRegNo = vehicleRegNo;
            this.floor = floor;
            this.slot = slot;
            this.entryTimeMillis = entryTimeMillis;
        }

        // 2. Overloaded Constructor (Convenience chaining using 'this'): Auto-populates current time
        public ParkingTicket(String ticketId, String vehicleRegNo, int floor, int slot) {
            this(ticketId, vehicleRegNo, floor, slot, System.currentTimeMillis());
        }

        // 3. Default Constructor: Fallback / placeholder ticket chaining
        public ParkingTicket() {
            this("TKT-DEFAULT", "UNKNOWN", -1, -1, 0L);
        }

        // 4. Copy Constructor: For re-issuing a duplicate ticket without modifying original entry state
        public ParkingTicket(ParkingTicket original, String duplicateTicketId) {
            this(duplicateTicketId, original.vehicleRegNo, original.floor, original.slot, original.entryTimeMillis);
        }

        public String getTicketId() { return ticketId; }
        public String getVehicleRegNo() { return vehicleRegNo; }
        public int getFloor() { return floor; }
        public int getSlot() { return slot; }
        public long getEntryTimeMillis() { return entryTimeMillis; }

        @Override
        public String toString() {
            return "Ticket[" + ticketId + " | Vehicle=" + vehicleRegNo + " | Location=(" + floor + "," + slot + ") | EntryTime=" + entryTimeMillis + "]";
        }
    }

    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("TASK 4: Design Classes, Objects and Constructors");
        System.out.println("Constructors, 'this' Chaining, Copy Semantics, and Memory Model");
        System.out.println("======================================================================");

        // 1. Default constructor demonstration
        ParkingTicket tktDefault = new ParkingTicket();
        System.out.println("\n[1] Default Constructor Object: " + tktDefault);

        // 2. Parameterized and chained constructor
        ParkingTicket tkt1 = new ParkingTicket("TKT-1001", "TN01AB1111", 0, 1);
        System.out.println("\n[2] Chained Overloaded Constructor Object: " + tkt1);

        // 3. Copy constructor demonstration (Duplicate ticket issuance)
        ParkingTicket tktDuplicate = new ParkingTicket(tkt1, "TKT-1001-DUP");
        System.out.println("\n[3] Copy Constructor (Re-issued Duplicate Ticket):");
        System.out.println("  Original : " + tkt1);
        System.out.println("  Duplicate: " + tktDuplicate);
        System.out.println("  Verification: Same vehicle & entry time preserved? " + 
            (tkt1.getVehicleRegNo().equals(tktDuplicate.getVehicleRegNo()) && tkt1.getEntryTimeMillis() == tktDuplicate.getEntryTimeMillis()));
        System.out.println("  Verification: Distinct object reference on heap? " + (tkt1 != tktDuplicate));

        // 4. Alternative Concept Comparison: Constructor Chaining vs Setters vs Factory Methods
        System.out.println("\n[4] Alternative Concept & Complexity Comparison:");
        System.out.println("Approach A (Constructor Chaining): Invariant validity guaranteed at instant of creation.");
        System.out.println("Approach B (Default + Setters): Vulnerable to partially initialized / inconsistent state before setters complete.");
        System.out.println("Approach C (Static Factory Methods): Enables descriptive method names (e.g., issueVisitorTicket()), caching, and subtype return.");
        System.out.println("Memory Model: Stack holds reference variables (tkt1, tktDuplicate), while heap holds actual ParkingTicket object payloads.");
        System.out.println("======================================================================");
    }
}
