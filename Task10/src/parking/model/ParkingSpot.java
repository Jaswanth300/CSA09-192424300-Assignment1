package parking.model;

/**
 * Encapsulated ParkingSpot with defensive mutators.
 */
public class ParkingSpot {
    private final int floor;
    private final int slot;
    private Vehicle occupiedBy;

    public ParkingSpot(int floor, int slot) {
        if (floor < 0) throw new IllegalArgumentException("Floor index cannot be negative");
        if (slot < 0) throw new IllegalArgumentException("Slot index cannot be negative");
        this.floor = floor;
        this.slot = slot;
        this.occupiedBy = null;
    }

    public int getFloor() { return floor; }
    public int getSlot() { return slot; }
    public boolean isFree() { return occupiedBy == null; }
    public Vehicle getOccupiedBy() { return occupiedBy; }

    public boolean assignVehicle(Vehicle vehicle) {
        if (vehicle == null) throw new IllegalArgumentException("Cannot assign null vehicle");
        if (!isFree()) return false;
        this.occupiedBy = vehicle;
        return true;
    }

    public Vehicle vacate() {
        Vehicle previous = this.occupiedBy;
        this.occupiedBy = null;
        return previous;
    }

    @Override
    public String toString() {
        return "Spot(" + floor + "," + slot + ")=" + (isFree() ? "EMPTY" : occupiedBy.getRegNo());
    }
}
