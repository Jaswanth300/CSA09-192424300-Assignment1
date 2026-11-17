/**
 * SIMATS Java Programming Assignment 1 - Task 04
 * Classes, Objects and Constructors
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
public class ConstructorDemo {

    static class ParkingTicket {
        String ticketId;
        String vehicleNo;
        int floor;
        int slot;

        public ParkingTicket(String ticketId, String vehicleNo, int floor, int slot) {
            this.ticketId = ticketId;
            this.vehicleNo = vehicleNo;
            this.floor = floor;
            this.slot = slot;
        }

        public ParkingTicket(String ticketId, String vehicleNo) {
            this(ticketId, vehicleNo, 0, 0);
        }

        public ParkingTicket() {
            this("TKT-000", "UNKNOWN", 0, 0);
        }

        public ParkingTicket(ParkingTicket other, String newTicketId) {
            this(newTicketId, other.vehicleNo, other.floor, other.slot);
        }

        public void display() {
            System.out.println("Ticket ID: " + ticketId + ", Vehicle: " + vehicleNo + ", Location: Floor " + floor + " Slot " + slot);
        }
    }

    public static void main(String[] args) {
        ParkingTicket t1 = new ParkingTicket();
        System.out.print("Default ticket: ");
        t1.display();

        ParkingTicket t2 = new ParkingTicket("TKT-101", "TN01AB1111", 1, 2);
        System.out.print("Parameterized ticket: ");
        t2.display();

        ParkingTicket t3 = new ParkingTicket("TKT-102", "TN02CD2222");
        System.out.print("Chained ticket: ");
        t3.display();

        ParkingTicket t4 = new ParkingTicket(t2, "TKT-101-DUP");
        System.out.print("Duplicate ticket: ");
        t4.display();
    }
}
