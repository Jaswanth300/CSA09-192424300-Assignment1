/**
 * SIMATS Java Programming Assignment 1 - Task 05
 * Static Members and Object Lifecycle
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
public class TicketLifecycleDemo {

    static class ParkingTicket {
        private static int counter = 0;
        private static int activeCount = 0;

        private int ticketId;
        private String vehicleNo;

        public ParkingTicket(String vehicleNo) {
            this.ticketId = ++counter;
            activeCount++;
            this.vehicleNo = vehicleNo;
        }

        public void markExit() {
            if (activeCount > 0) {
                activeCount--;
            }
        }

        public static int getTotalIssued() {
            return counter;
        }

        public static int getActiveCount() {
            return activeCount;
        }

        public void display() {
            System.out.println("Ticket #" + ticketId + " for " + vehicleNo);
        }
    }

    // Demonstrates pass-by-value of object references
    public static void testReference(ParkingTicket t) {
        t = null; // Modifies local parameter only; caller reference is unaffected
    }

    public static void main(String[] args) {
        ParkingTicket t1 = new ParkingTicket("TN01A1111");
        ParkingTicket t2 = new ParkingTicket("TN02B2222");
        t1.display();
        t2.display();
        System.out.println("Total tickets issued: " + ParkingTicket.getTotalIssued());
        System.out.println("Active tickets inside: " + ParkingTicket.getActiveCount());

        t1.markExit();
        t1 = null; // Eligible for garbage collection
        System.out.println("After vehicle 1 exits, active count: " + ParkingTicket.getActiveCount());
        System.out.println("t1 set to null (eligible for GC)");

        testReference(t2);
        System.out.println("Caller t2 is still valid: " + (t2 != null));
    }
}
