/**
 * SIMATS Java Programming Assignment 1 - Campus Smart-Parking System
 * TASK 5: Apply static Members and the Object Life Cycle
 * Bloom's Level: K4 - Analyse | Marks: 3
 *
 * Student: BALA NAGA JASWANTH (Reg No: 192424300C)
 */
public class TicketLifecycleDemo {

    static class ParkingTicket {
        // Static member: shared across all ticket instances to ensure monotonically increasing, globally unique IDs
        private static int ticketCounter = 0;
        private static int activeOccupancy = 0;

        private final String ticketId;
        private final String regNo;
        private final long entryTime;

        // Static factory method
        public static ParkingTicket issue(String regNo) {
            ticketCounter++;
            activeOccupancy++;
            String id = String.format("TKT-%04d", ticketCounter);
            return new ParkingTicket(id, regNo, System.currentTimeMillis());
        }

        private ParkingTicket(String ticketId, String regNo, long entryTime) {
            this.ticketId = ticketId;
            this.regNo = regNo;
            this.entryTime = entryTime;
        }

        public static int getGlobalTicketCount() { return ticketCounter; }
        public static int getActiveOccupancy() { return activeOccupancy; }

        public static void recordExit() {
            if (activeOccupancy > 0) activeOccupancy--;
        }

        public String getTicketId() { return ticketId; }
        public String getRegNo() { return regNo; }

        @Override
        public String toString() {
            return "[" + ticketId + " : " + regNo + "]";
        }
    }

    // Method demonstrating pass-by-value of object references
    public static void processTicketInspection(ParkingTicket ticketRef) {
        System.out.println("  Inside processTicketInspection: Inspecting " + ticketRef);
        // Attempting to rebind the formal parameter
        ticketRef = null;
        System.out.println("  Inside processTicketInspection: Local parameter set to null. Caller reference unaffected.");
    }

    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("TASK 5: Apply static Members and the Object Life Cycle");
        System.out.println("Static State, Lifecycle Tracking, Reference Semantics, and GC");
        System.out.println("======================================================================");

        // 1. Static Counters & Globally Unique Ticket Generation
        System.out.println("\n[1] Static Members & Global Sequence Validation:");
        ParkingTicket t1 = ParkingTicket.issue("TN01A1111");
        ParkingTicket t2 = ParkingTicket.issue("TN02B2222");
        ParkingTicket t3 = ParkingTicket.issue("TN03C3333");

        System.out.println("Issued: " + t1);
        System.out.println("Issued: " + t2);
        System.out.println("Issued: " + t3);
        System.out.println("Static Global Total Issued: " + ParkingTicket.getGlobalTicketCount());
        System.out.println("Static Live Occupancy     : " + ParkingTicket.getActiveOccupancy());

        // 2. Object Lifecycle and Garbage Collection Eligibility
        System.out.println("\n[2] Object Lifecycle & Garbage Collection Trace:");
        System.out.println("Vehicle TN01A1 exits facility.");
        ParkingTicket.recordExit();
        t1 = null; // Dereferencing active ticket reference
        System.out.println("Active reference t1 set to null. Object TKT-0001 is now UNREACHABLE on the Heap.");
        System.out.println("Status: Eligible for Garbage Collection (GC) sweep.");
        System.out.println("Updated Static Live Occupancy: " + ParkingTicket.getActiveOccupancy());

        // 3. Object Reference Passing Semantics (Pass-by-Value)
        System.out.println("\n[3] Pass-by-Value of Object References:");
        System.out.println("Before method call, t2 is: " + t2);
        processTicketInspection(t2);
        System.out.println("After method call, t2 is still: " + t2 + " (Reference in caller remains intact)");

        // 4. Alternative Concept Analysis: Static Counter vs Multi-Facility Instance Counters
        System.out.println("\n[4] Alternative Concept & Multi-Facility Analysis:");
        System.out.println("Problem: University runs North Gate and South Gate facilities.");
        System.out.println("  - Case A (Global Static Counter): Unified numbering across all k facilities (TKT-0001, TKT-0002...).");
        System.out.println("    Guarantee: Zero collision risk across the campus. Extra space = O(1) integer.");
        System.out.println("  - Case B (Facility Instance Counter): Each facility has an instance counter.");
        System.out.println("    Result: Both gates would issue 'TKT-0001' unless prefixed with Facility ID (e.g., 'NG-0001' vs 'SG-0001').");
        System.out.println("    Space: O(k) integers for k facilities.");
        System.out.println("======================================================================");
    }
}
