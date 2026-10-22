package parking.model;

/**
 * ParkingSpot model representing a single physical space.
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
public class ParkingSpot {
    private int floor;
    private int slot;
    private Vehicle vehicle;

    public ParkingSpot(int floor, int slot) {
        this.floor = floor;
        this.slot = slot;
        this.vehicle = null;
    }

    public int getFloor() {
        return floor;
    }

    public int getSlot() {
        return slot;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public boolean isFree() {
        return vehicle == null;
    }

    public void park(Vehicle v) {
        this.vehicle = v;
    }

    public void vacate() {
        this.vehicle = null;
    }
}
