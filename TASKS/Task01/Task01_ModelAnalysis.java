/**
 * SIMATS Java Programming Assignment 1 - Task 01
 * Problem Analysis & Object Identification
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
public class Task01_ModelAnalysis {

    static class Vehicle {
        String regNo;
        String type;

        Vehicle(String regNo, String type) {
            this.regNo = regNo;
            this.type = type;
        }

        public String getRegNo() {
            return regNo;
        }

        public String getType() {
            return type;
        }
    }

    static class ParkingSpot {
        int floor;
        int slot;
        Vehicle vehicle;

        ParkingSpot(int floor, int slot) {
            this.floor = floor;
            this.slot = slot;
            this.vehicle = null;
        }

        boolean isAvailable() {
            return vehicle == null;
        }

        boolean park(Vehicle v) {
            if (!isAvailable()) {
                return false; // Cannot park in an occupied slot
            }
            this.vehicle = v;
            return true;
        }

        Vehicle vacate() {
            Vehicle removed = this.vehicle;
            this.vehicle = null;
            return removed;
        }
    }

    public static void main(String[] args) {
        ParkingSpot spot = new ParkingSpot(0, 0);
        Vehicle v1 = new Vehicle("TN01AB1111", "Car");
        Vehicle v2 = new Vehicle("TN02CD2222", "TwoWheeler");

        System.out.println("Spot (0,0) initially free: " + spot.isAvailable());

        boolean parked = spot.park(v1);
        System.out.println("Parked v1 (TN01AB1111): " + parked);
        System.out.println("Spot (0,0) free after parking: " + spot.isAvailable());

        // Reject double-booking
        boolean doublePark = spot.park(v2);
        System.out.println("Attempt to park v2 in occupied spot: " + doublePark);

        Vehicle vacated = spot.vacate();
        System.out.println("Vacated spot, removed vehicle: " + vacated.getRegNo());
        System.out.println("Spot (0,0) free after vacate: " + spot.isAvailable());
    }
}
